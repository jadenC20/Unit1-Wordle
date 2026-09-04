package com.example.wordle

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity : AppCompatActivity() {

    private lateinit var wordToGuess: String
    private var guessCount = 0
    private var gameOver = false
    private var currentList = FourLetterWordList.WordList.COMMON
    private var streak = 0

    private lateinit var tvStreak: TextView
    private lateinit var ivStar: ImageView
    private lateinit var tvGuess1Label: TextView
    private lateinit var tvGuess1Word: TextView
    private lateinit var tvGuess1CheckLabel: TextView
    private lateinit var tvGuess1Result: TextView
    private lateinit var tvGuess2Label: TextView
    private lateinit var tvGuess2Word: TextView
    private lateinit var tvGuess2CheckLabel: TextView
    private lateinit var tvGuess2Result: TextView
    private lateinit var tvGuess3Label: TextView
    private lateinit var tvGuess3Word: TextView
    private lateinit var tvGuess3CheckLabel: TextView
    private lateinit var tvGuess3Result: TextView
    private lateinit var tvAnswer: TextView
    private lateinit var etGuess: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button
    private lateinit var listToggle: MaterialButtonToggleGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindViews()
        streak = getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(KEY_STREAK, 0)
        updateStreakLabel()

        tvGuess1Label.text = getString(R.string.guess_number, 1)
        tvGuess1CheckLabel.text = getString(R.string.guess_check, 1)
        tvGuess2Label.text = getString(R.string.guess_number, 2)
        tvGuess2CheckLabel.text = getString(R.string.guess_check, 2)
        tvGuess3Label.text = getString(R.string.guess_number, 3)
        tvGuess3CheckLabel.text = getString(R.string.guess_check, 3)

        startNewRound()

        btnSubmit.setOnClickListener { submitGuess() }
        btnReset.setOnClickListener { startNewRound() }
        etGuess.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submitGuess()
                true
            } else {
                false
            }
        }

        listToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            currentList = when (checkedId) {
                R.id.btnListSports -> FourLetterWordList.WordList.SPORTS
                R.id.btnListNature -> FourLetterWordList.WordList.NATURE
                else -> FourLetterWordList.WordList.COMMON
            }
            startNewRound()
        }
    }

    private fun bindViews() {
        tvStreak = findViewById(R.id.tvStreak)
        ivStar = findViewById(R.id.ivStar)
        tvGuess1Label = findViewById(R.id.tvGuess1Label)
        tvGuess1Word = findViewById(R.id.tvGuess1Word)
        tvGuess1CheckLabel = findViewById(R.id.tvGuess1CheckLabel)
        tvGuess1Result = findViewById(R.id.tvGuess1Result)
        tvGuess2Label = findViewById(R.id.tvGuess2Label)
        tvGuess2Word = findViewById(R.id.tvGuess2Word)
        tvGuess2CheckLabel = findViewById(R.id.tvGuess2CheckLabel)
        tvGuess2Result = findViewById(R.id.tvGuess2Result)
        tvGuess3Label = findViewById(R.id.tvGuess3Label)
        tvGuess3Word = findViewById(R.id.tvGuess3Word)
        tvGuess3CheckLabel = findViewById(R.id.tvGuess3CheckLabel)
        tvGuess3Result = findViewById(R.id.tvGuess3Result)
        tvAnswer = findViewById(R.id.tvAnswer)
        etGuess = findViewById(R.id.etGuess)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)
        listToggle = findViewById(R.id.listToggle)
    }

    private fun startNewRound() {
        wordToGuess = if (currentList == FourLetterWordList.WordList.COMMON) {
            FourLetterWordList.getRandomFourLetterWord()
        } else {
            FourLetterWordList.getRandomFourLetterWord(currentList)
        }
        guessCount = 0
        gameOver = false

        tvGuess1Word.text = ""
        tvGuess1Result.text = ""
        tvGuess2Word.text = ""
        tvGuess2Result.text = ""
        tvGuess3Word.text = ""
        tvGuess3Result.text = ""
        tvAnswer.visibility = View.GONE
        ivStar.visibility = View.GONE
        etGuess.text.clear()
        etGuess.isEnabled = true
        btnSubmit.isEnabled = true
        btnSubmit.alpha = 1f
        btnSubmit.visibility = View.VISIBLE
        hideKeyboard()
    }

    private fun submitGuess() {
        if (gameOver || guessCount >= MAX_GUESSES) {
            Toast.makeText(this, R.string.error_max_guesses, Toast.LENGTH_SHORT).show()
            return
        }

        val guess = etGuess.text.toString().uppercase()
        if (guess.length != 4) {
            Toast.makeText(this, R.string.error_length, Toast.LENGTH_SHORT).show()
            return
        }
        if (!guess.all { it.isLetter() }) {
            Toast.makeText(this, R.string.error_letters, Toast.LENGTH_SHORT).show()
            return
        }

        val result = checkGuess(guess)
        guessCount += 1
        showGuess(guessCount, guess, result)

        etGuess.text.clear()
        hideKeyboard()

        val won = guess == wordToGuess
        if (won || guessCount >= MAX_GUESSES) {
            endRound(won)
        }
    }

    private fun showGuess(number: Int, guess: String, result: String) {
        val wordView: TextView
        val resultView: TextView
        when (number) {
            1 -> {
                wordView = tvGuess1Word
                resultView = tvGuess1Result
            }
            2 -> {
                wordView = tvGuess2Word
                resultView = tvGuess2Result
            }
            else -> {
                wordView = tvGuess3Word
                resultView = tvGuess3Result
            }
        }
        wordView.text = colorize(guess, result)
        resultView.text = colorize(result, result)
    }

    private fun colorize(text: String, result: String): SpannableString {
        val spannable = SpannableString(text)
        for (i in text.indices) {
            val color = when (result.getOrNull(i)) {
                'O' -> Color.parseColor("#6AAA64")
                '+' -> Color.parseColor("#C9B458")
                else -> Color.parseColor("#787C7E")
            }
            spannable.setSpan(
                ForegroundColorSpan(color),
                i,
                i + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }

    private fun endRound(won: Boolean) {
        gameOver = true
        etGuess.isEnabled = false
        btnSubmit.isEnabled = false
        btnSubmit.alpha = 0.4f
        tvAnswer.text = wordToGuess
        tvAnswer.visibility = View.VISIBLE

        if (won) {
            ivStar.visibility = View.VISIBLE
            streak += 1
            saveStreak()
            updateStreakLabel()
            Toast.makeText(this, R.string.you_won, Toast.LENGTH_SHORT).show()
        } else {
            streak = 0
            saveStreak()
            updateStreakLabel()
            Toast.makeText(this, R.string.error_max_guesses, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateStreakLabel() {
        tvStreak.text = getString(R.string.streak, streak)
    }

    private fun saveStreak() {
        getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_STREAK, streak)
            .apply()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etGuess.windowToken, 0)
    }

    /**
     * Assignment helper: O = correct place, + = right letter wrong place, X = not in word.
     */
    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }

    companion object {
        private const val MAX_GUESSES = 3
        private const val PREFS = "wordle_prefs"
        private const val KEY_STREAK = "streak"
    }
}

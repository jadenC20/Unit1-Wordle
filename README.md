# Android Project 1 - Wordle

Wordle is an android app that recreates a simple version of the popular word game [Wordle](https://www.nytimes.com/games/wordle/index.html).

Time spent: ~3 hours spent in total

## Required Features

The following **required** functionality is completed:

- [x] **User has 3 chances to guess a random 4 letter word**
- [x] **After 3 guesses, user should no longer be able to submit another guess**
- [x] **After each guess, user sees the "correctness" of the guess**
- [x] **After all guesses are taken, user can see the target word displayed**

The following **optional** features are implemented:

- [x] User can toggle between different word lists
- [x] User can see the 'correctness' of their guess through colors on the word
- [x] User sees a visual change after guessing the correct word
- [x] User can tap a 'Reset' button to get a new word and clear previous guesses
- [x] User will get an error message if they input an invalid guess
- [x] User can see a 'streak' record of how many words they've guessed correctly.

The following **additional** features are implemented:

- [x] Common / Sports / Nature word lists
- [x] Keyboard hides and the guess field clears after each submit

## Video Walkthrough

GIF created using [LiceCap](http://www.cockos.com/licecap/).

Add your walkthrough GIF here after recording:

```
<img width="959" height="1251" alt="Wordle Demo" src="https://github.com/user-attachments/assets/d77ed882-6c27-4e62-9fc1-f3cd51d01cf0" />


```

## Notes

`FourLetterWordList.getRandomFourLetterWord()` supplies the secret word. After each guess, `checkGuess` returns a string using **O** (right letter, right place), **+** (right letter, wrong place), and **X** (not in the word). Submit is disabled after 3 guesses or a win; Reset starts a new round.

## License

    Copyright [2026] [Jaden]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

package com.example.wordle

/**
 * Helper from the Unit 1 Wordle assignment.
 * Common 4-letter words plus extra themed lists for stretch features.
 */
object FourLetterWordList {
    // Common 4 letter words (assignment helper style: split + shuffled + uppercase)
    private const val FOUR_LETTER_WORDS =
        "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Stay,Step,Stop,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Come,Cook,Cope,Dare,Deny,Draw,Drop,Earn,Fail,Fall,Feel,Fill,Find,Gain,Give,Grow,Hang,Hate,Have,Hear,Hide,Hold,Hurt,Join,Jump,Keep,Kill,Know,Last,Lead,Lend,Lift,Like,Link,Live,Lose,Make,Meet,Must,Open,Pass,Pick,Pray,Pull,Push,Read,Rely,Ride,Ring,Roll,Save,Seek,Seem,Sell,Send,Shed,Shut,Sing,Sink,Slip,Suit,Take,Talk,Tell,Tend,Vary,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Wish,Able,Bare,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Easy,Evil,Fair,Fast,Fine,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Hard,High,Holy,Huge,Just,Keen,Late,Lazy,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Near,Neat,Next,Nice,Okay,Only,Oral,Pale,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Thin,Tidy,Tiny,True,Ugly,Vain,Vast,Very,Warm,Wary,Weak,Wide,Wild,Wise,Zero"

    private const val SPORTS_WORDS =
        "Ball,Goal,Team,Golf,Swim,Race,Kick,Jump,Surf,Dunk,Pass,Punt,Play,Game,Skip,Trot,Walk,Hike,Bowl,Pong,Hoop,Club,Yard,Mile,Lane,Heat,Meet,Lap,Wait,Shot,Putt,Serve,Ace,Net,Rim,Foul,Dive,Ride,Sail,Ski,Snow,Track,Fast,Slow,Win,Loss,Tied"

    private const val NATURE_WORDS =
        "Tree,Leaf,Fern,Moss,Lake,Hill,Wind,Rain,Snow,Bird,Fish,Deer,Wolf,Bear,Rose,Lily,Pond,Sand,Rock,Wave,Moon,Star,Soil,Seed,Bush,Vine,Peak,Cave,Reef,Tide,Mist,Dawn,Dusk,Oak,Pine,Hawk,Frog,Toad,Lark,Iris,Sage,Clay,Lava,Cove,Glen,Dale"

    enum class WordList {
        COMMON, SPORTS, NATURE
    }

    fun getRandomFourLetterWord(): String {
        val words = FOUR_LETTER_WORDS.split(",").shuffled()
        return words[0].uppercase()
    }

    fun getRandomFourLetterWord(list: WordList): String {
        val source = when (list) {
            WordList.COMMON -> FOUR_LETTER_WORDS
            WordList.SPORTS -> SPORTS_WORDS
            WordList.NATURE -> NATURE_WORDS
        }
        val words = source.split(",")
            .map { it.trim() }
            .filter { it.length == 4 }
            .shuffled()
        return words[0].uppercase()
    }
}

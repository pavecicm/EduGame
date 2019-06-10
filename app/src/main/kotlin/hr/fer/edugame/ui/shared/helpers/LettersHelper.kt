package hr.fer.edugame.ui.shared.helpers

import hr.fer.edugame.extensions.random
import hr.fer.edugame.ui.letters.EMPTY_WORD

fun getLetters(vowels: Int, consonants: Int): MutableList<String> {
    val numbers: MutableList<Int> = mutableListOf()
    val letters: MutableList<String> = mutableListOf()
    for (i in 1..vowels) {
        var number: Int
        do {
            number = ((1..145).random())
        } while (numbers.contains(number))
        numbers.add(number)
    }
    for (i in 1..consonants) {
        var number: Int
        do {
            number = ((146..368).random())
        } while (numbers.contains(number))
        numbers.add(number)
    }
    numbers.forEach {
        letters.add(turnInGameLetter(it).toString())
    }
    return letters
}

fun getVowel(): Char =
    turnInGameLetter(((1..145).random()))

fun getConsonant() = turnInGameLetter(((146..368).random()))

fun turnInGameLetter(random: Int): Char =
    when (random) {
        in 1..39 -> 'A'
        in 40..65 -> 'E'
        in 66..102 -> 'I'
        in 103..130 -> 'O'
        in 131..145 -> 'U'
        in 146..150 -> 'B'
        in 151..155 -> 'C'
        in 156..160 -> 'Ć'
        in 161..164 -> 'Č'
        in 165..172 -> 'D'
        in 173..175 -> 'Đ'
        in 176..177 -> 'F'
        in 178..182 -> 'G'
        in 183..185 -> 'H'
        in 186..203 -> 'J'
        in 204..216 -> 'K'
        in 217..231 -> 'L'
        in 232..247 -> 'M'
        in 248..272 -> 'N'
        in 273..285 -> 'P'
        in 286..308 -> 'R'
        in 309..323 -> 'S'
        in 324..328 -> 'Š'
        in 329..345 -> 'T'
        in 346..357 -> 'V'
        in 358..364 -> 'Z'
        in 364..368 -> 'Ž'
//        32 -> '&' // Dž
//        33 -> '%' // Lj
//        34 -> '?' // Nj
        else -> 'a'
    }

fun calculatePoints(ownResult: String, opponentResult: String): Int {
    val own = ownResult.length
    val opponent = opponentResult.length
    return if ((opponentResult == EMPTY_WORD || own == 9) && own != opponent) {
        12
    } else if (own == 0 || own == opponent || own < opponent) {
        0
    } else if (opponentResult == EMPTY_WORD || own == 9) {
        12
    } else {
        6
    }
}

fun calculatePointSinglePlayer(result: String) =
    if (result.length == 9)
        12
    else
        result.length

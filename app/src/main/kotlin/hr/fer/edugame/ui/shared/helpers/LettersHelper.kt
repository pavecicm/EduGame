package hr.fer.edugame.ui.shared.helpers

import hr.fer.edugame.extensions.random

fun getLetters(vowels: Int, consonants: Int): MutableList<String> {
    val numbers: MutableList<Int> = mutableListOf()
    val letters: MutableList<String> = mutableListOf()
    for (i in 1..vowels) {
        var number: Int
        do {
            number = ((1..5).random())
        } while (numbers.contains(number))
        numbers.add(number)
    }
    for (i in 1..consonants) {
        var number: Int
        do {
            number = ((6..31).random())
        } while (numbers.contains(number))
        numbers.add(number)
    }
    numbers.forEach {
        letters.add(turnInGameLetter(it).toString())
    }
    return letters
}

fun getVowel(): Char =
    turnInGameLetter(((1..5).random()))

fun getConsonant() = turnInGameLetter(((6..31).random()))

fun turnInGameLetter(random: Int): Char =
    when (random) {
        1 -> 'A'
        2 -> 'E'
        3 -> 'I'
        4 -> 'O'
        5 -> 'U'
        6 -> 'B'
        7 -> 'C'
        8 -> 'Ć'
        9 -> 'Č'
        10 -> 'D'
        11 -> 'Đ'
        12 -> 'E'
        13 -> 'F'
        14 -> 'G'
        15 -> 'H'
        16 -> 'I'
        17 -> 'J'
        18 -> 'K'
        19 -> 'L'
        20 -> 'M'
        21 -> 'N'
        22 -> 'O'
        23 -> 'P'
        24 -> 'R'
        25 -> 'S'
        26 -> 'Š'
        27 -> 'T'
        28 -> 'U'
        29 -> 'V'
        30 -> 'Z'
        31 -> 'Ž'
//        32 -> '&' // Dž
//        33 -> '%' // Lj
//        34 -> '?' // Nj
        else -> 'a'
    }

fun calculatePoints(ownResult: String, opponentResult: String): Int {
    val own = ownResult.length
    val opponent = opponentResult.length
    return if (own == opponent) {
        0
    } else if (own < opponent) {
        0
    } else if (own == 9) {
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

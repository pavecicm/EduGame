package hr.fer.edugame.ui.shared.helpers

import hr.fer.edugame.extensions.random


fun getNumbers(count: Int): List<Int> {
    val numbers: MutableList<Int> = mutableListOf()
    val temp = getRandomNumbers(count)
    temp.forEach {
        numbers.add(turnInGameNumber(it))
    }
    return numbers
}

fun getWantedNumber() = ((0..999).random())

fun getRandomNumbers(count: Int): List<Int> {
    val numbers: MutableList<Int> = mutableListOf()
    for (i in 1..count) {
        var number: Int
        do {
            number = ((1..21).random())
        } while (numbers.contains(number))
        numbers.add(number)
    }
    return numbers
}

fun turnInGameNumber(number: Int): Int =
    when (number) {
        1, 2 -> 1
        3, 4 -> 2
        5, 6 -> 3
        8, 7 -> 4
        9 -> 5
        11, 12 -> 6
        13, 14 -> 7
        15, 16 -> 8
        17, 18 -> 9
        19 -> 10
        20 -> 25
        10 -> 50
        21 -> 100
        else -> 0
    }

fun calculatePoints(wantedNumber: Int, ownResult: Int, opponentResult: Int): Int {
    val own: Int = Math.abs(wantedNumber - ownResult)
    val opponent: Int = Math.abs(wantedNumber - opponentResult)
    return if (own == opponent) {
        0
    } else if (opponent == 0 || opponent < own) {
        0
    } else if (own == 0) {
        12
    } else {
        6
    }
}
package hr.fer.edugame.extensions

import hr.fer.edugame.ui.letters.Letter


fun String.toOperationUI(first: Int, second: Int, result: Int) =
    String.format("%d %s %d = %d", first, this, second, result)

fun String.splitEmail(): String {
    var split = split("@")
    return split[0]
}

fun String.toLetter(index: Int) = Letter(index, this)
package hr.fer.edugame.extensions


fun String.toOperationUI(first: Int, second: Int, result: Int) =
    String.format("%d %s %d = %d", first, this, second, result)

fun String.splitEmail(): String {
    var split = split("@")
    return split[0]
}
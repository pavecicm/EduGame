package hr.fer.edugame.ui.letters

data class Letter(
    val index: Int,
    val letter: String,
    var isClickable: Boolean = true
)
package hr.fer.edugame.ui.letters

import hr.fer.edugame.ui.shared.base.BaseView

interface LettersView : BaseView {
    fun displayLetters(letters: List<Char>)
    fun showNextLevel(letters: List<Char>)
}
package hr.fer.edugame.ui.letters

import hr.fer.edugame.extensions.random
import hr.fer.edugame.ui.shared.base.BasePresenter
import java.util.Random
import javax.inject.Inject

class LettersPresenter @Inject constructor(
    override val view: LettersView
): BasePresenter(view) {

    private val words: List<String> = listOf()

    fun init() {
        view.displayLetters(getLetters(5))
    }

    fun onNextLevel(){
        view.showNextLevel(getLetters(7))
    }

    private fun getLetters(count: Int): List<Char> {
        val letters: MutableList<Char> = mutableListOf()
        for(i in 0..count) {
            letters.add(getRandomLetter())
        }
        return letters
    }

    private fun getRandomLetter() = ((65..90).random()).toChar()

}
package hr.fer.edugame.ui.letters.dialog

import hr.fer.edugame.data.firebase.interactors.LettersGameInteractor
import hr.fer.edugame.ui.shared.WordsUtil
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.base.BaseView
import javax.inject.Inject

class ChooseLetterTypePresenter @Inject constructor(
    override val view: BaseView,
    private val lettersGameInteractor: LettersGameInteractor,
    private val wordsUtil: WordsUtil

    ): BasePresenter(view) {

    fun generateVowel() {
//        lettersGameInteractor.setRandomLetters()
    }

    fun generateConsoant() {

    }
}
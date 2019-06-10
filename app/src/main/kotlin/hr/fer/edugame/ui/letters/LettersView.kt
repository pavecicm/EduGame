package hr.fer.edugame.ui.letters

import hr.fer.edugame.ui.shared.base.BaseView

interface LettersView : BaseView {
    fun displayLetters(points: Int, letters: List<String>)
    fun displayLettersAndChooseNext(letters: List<String>)
    fun navigateToNextLevel(ownResult: String, opponentResult: String, points: Int)
    fun navigateToNextLevel(result: String, points: Int)
    fun updateProgress(seccondsRemaining: Long)
    fun getLongestWord(): String
    fun saveWord(word: String)
    fun showNoSuchWord()
    fun showChooseType()
    fun showOpponentTurnToChoose(letters: List<String>)
    fun setGoBack(goBack: Boolean)
}
package hr.fer.edugame.ui.numbers

import hr.fer.edugame.ui.shared.base.BaseView

interface NumbersView : BaseView {
    fun startLevel(points: Int, wantedNumber: Int, givenNumbers: List<Int>)
    fun resetLevel(wantedNumber: Int, givenNumbers: List<Int>)
    fun updateGivenNumber(number: Int)
    fun updateOperationsList(operation: String)
    fun navigateToNextLevel(
        points: Int,
        ownResult: Int,
        opponentResult: Int
    )
    fun navigateToNextLevel(
        points: Int,
        result: Int
    )
    fun updateProgress(seccondsRemaining: Long)
}
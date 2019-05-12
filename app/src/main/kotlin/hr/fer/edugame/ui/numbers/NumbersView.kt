package hr.fer.edugame.ui.numbers

import hr.fer.edugame.ui.shared.base.BaseView

interface NumbersView : BaseView {
    fun startLevel(totalPoints: Int, wanted: Int, givenNumbers: List<Int>)
    fun resetLevel(wanted: Int, givenNumbers: List<Int>)
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
    fun updateProgress(secondsRemaining: Long)
}
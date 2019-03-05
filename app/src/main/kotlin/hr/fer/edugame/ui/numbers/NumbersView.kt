package hr.fer.edugame.ui.numbers

import hr.fer.edugame.ui.shared.base.BaseView

interface NumbersView : BaseView {
    fun startLevel(wantedNumber: Int, givenNumbers: List<Int>)
    fun resetLevel(wantedNumber: Int, givenNumbers: List<Int>)
    fun updateGivenNumber(number: Int)
    fun updateOperationsList(operation: String)
    fun navigateToNextLevel(wanted: Int, numbers: List<Int>)
}
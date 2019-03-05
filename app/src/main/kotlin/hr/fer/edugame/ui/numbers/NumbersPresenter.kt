package hr.fer.edugame.ui.numbers

import hr.fer.edugame.extensions.random
import hr.fer.edugame.extensions.toOperationUI
import hr.fer.edugame.ui.shared.base.BasePresenter
import javax.inject.Inject

private const val PLUS = "+"
private const val MINUS = "-"
private const val TIMES = "*"
private const val DIVIDE = "/"

class NumbersPresenter @Inject constructor(
    override val view: NumbersView
) : BasePresenter(view) {

    private val givenNumbers: MutableList<Int> = mutableListOf()
    private var wantedNumber = 0
    private var result: Int = 0

    fun init() {
        wantedNumber = ((65..999).random())
        givenNumbers.addAll(getNumbers(4))
        view.startLevel(wantedNumber = wantedNumber, givenNumbers = givenNumbers)
    }

    private fun getNumbers(count: Int): List<Int> {
        val numbers: MutableList<Int> = mutableListOf()
        for (i in 0..count) {
            numbers.add(((1..100).random()))
        }
        return numbers
    }

    fun reset() {
        view.resetLevel(wantedNumber = wantedNumber, givenNumbers = givenNumbers)
    }

    fun handleOnCalculateClicked(first: Int, second: Int, operation: String) {
        result = calculate(first, second, operation)
        view.updateOperationsList(operation.toOperationUI(first, second, result))
        view.updateGivenNumber(result)
    }

    fun calculate(first: Int, second: Int, operation: String) =
        when (operation) {
            PLUS -> first + second
            MINUS -> first - second
            TIMES -> first * second
            DIVIDE -> first / second
            else -> 0
        }

    fun handleOoNextLevelClick() {
        givenNumbers.clear()
        givenNumbers.addAll(getNumbers(6))
        wantedNumber = getNumbers(1).first()
        view.navigateToNextLevel(wantedNumber, givenNumbers)
    }
}
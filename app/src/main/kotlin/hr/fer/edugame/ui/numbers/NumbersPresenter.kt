package hr.fer.edugame.ui.numbers

import hr.fer.edugame.constants.NO_CALCULATED_NUMBER
import hr.fer.edugame.data.firebase.interactors.NumbersGameInteractor
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.extensions.toOperationUI
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.helpers.getNumbers
import hr.fer.edugame.ui.shared.helpers.getWantedNumber
import javax.inject.Inject

private const val PLUS = "+"
private const val MINUS = "-"
private const val TIMES = "*"
private const val DIVIDE = "/"

class NumbersPresenter @Inject constructor(
    override val view: NumbersView,
    private val preferenceStore: PreferenceStore,
    private val numbersGameInteractor: NumbersGameInteractor
) : BasePresenter(view) {

    private val givenNumbers: MutableList<Int> = mutableListOf()
    private var wantedNumber = -1
    private var result: Int = 0
    private var opponentResult = NO_CALCULATED_NUMBER
    private var points = 0
    private var totalPoints = 0
    private var isFinishClicked: Boolean = false

    fun init() {
        numbersGameInteractor.resetCalculatedNumbers()
        resetCache()
        if (preferenceStore.opponentId.isNotEmpty() && preferenceStore.isInitiator) {
            wantedNumber = getWantedNumber()
            givenNumbers.clear()
            givenNumbers.addAll(getNumbers(6))
            numbersGameInteractor.setRandomNumbers(this, givenNumbers, wantedNumber)
            displayNumbers()
        } else {
            numbersGameInteractor.listenForNumbers(this)
        }
        numbersGameInteractor.listenForOpponentResult(this)
    }

    fun setWantedNumber(wantedNumber: Int) {
        resetCache()
        this.wantedNumber = wantedNumber
        if (givenNumbers.isNotEmpty()) {
            displayNumbers()
        }
    }


    fun setGivenNumbers(givenNumbers: List<Int>) {
        resetCache()
        this.givenNumbers.clear()
        this.givenNumbers.addAll(givenNumbers)
        if (wantedNumber != -1) {
            displayNumbers()
        }
    }

    fun displayNumbers() {
        view.startLevel(wantedNumber = wantedNumber, givenNumbers = givenNumbers)
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
        numbersGameInteractor.finishRound(result)
        isFinishClicked = true
        calculatePoints()
    }

    fun onDestroy() {
        numbersGameInteractor.removeGameRoom()
    }

    fun saveOpponentResult(opponentResult: Int) {
        if(this.opponentResult != opponentResult) {
            this.opponentResult = opponentResult
            calculatePoints()
        }
    }

    fun calculatePoints() {
        if(opponentResult != -1 && isFinishClicked) {
            points = hr.fer.edugame.ui.shared.helpers.calculatePoints(wantedNumber, result, opponentResult)
            totalPoints += points
            view.navigateToNextLevel(
                totalPoints = totalPoints,
                points = points,
                ownResult = result,
                opponentResult = this.opponentResult
            )
        }
    }

    fun resetCache() {
        result = 0
        opponentResult = NO_CALCULATED_NUMBER
        isFinishClicked = false
    }
}
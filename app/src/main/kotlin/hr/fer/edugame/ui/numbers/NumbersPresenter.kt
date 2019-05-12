package hr.fer.edugame.ui.numbers

import hr.fer.edugame.constants.GAME_TURN_DURATION
import hr.fer.edugame.constants.NO_CALCULATED_NUMBER
import hr.fer.edugame.data.firebase.interactors.NumbersGameInteractor
import hr.fer.edugame.data.firebase.interactors.RankInteractor
import hr.fer.edugame.data.rx.RxSchedulers
import hr.fer.edugame.data.rx.applySchedulers
import hr.fer.edugame.data.rx.subscribe
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.extensions.toOperationUI
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.helpers.calculatePointsSinglePlayer
import hr.fer.edugame.ui.shared.helpers.getNumbers
import hr.fer.edugame.ui.shared.helpers.getUser
import hr.fer.edugame.ui.shared.helpers.getWantedNumber
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val PLUS = "+"
private const val MINUS = "-"
private const val TIMES = "*"
private const val DIVIDE = "/"
const val POINTS_TO_WIN = 50
const val START = 0

class NumbersPresenter @Inject constructor(
    override val view: NumbersView,
    private val preferenceStore: PreferenceStore,
    private val numbersGameInteractor: NumbersGameInteractor,
    private val rankInteractor: RankInteractor,
    private val rxSchedulers: RxSchedulers
) : BasePresenter(view) {

    private val givenNumbers: MutableList<Int> = mutableListOf()
    private var wantedNumber = -1
    private var result: Int = START
    private var opponentResult = NO_CALCULATED_NUMBER
    private var points = START
    private var totalPoints = START
    private var isFinishClicked: Boolean = false

    private val countdownObservable: Observable<Long> = Observable
        .interval(0, 1, TimeUnit.SECONDS, rxSchedulers.backgroundThreadScheduler)
        .map { GAME_TURN_DURATION - resumeCorrection - it }
        .takeUntil { it <= 0 }

    private var resumeCorrection = 0L

    fun init() {
        if (preferenceStore.isSinglePlayerEnabled) {
             totalPoints = preferenceStore.singlePlayerPoints
            startSinglePlayer()
        } else {
            this.totalPoints = preferenceStore.gamePoints
            numbersGameInteractor.resetCalculatedNumbers()
            resetCache()
            if (preferenceStore.opponentId.isNotEmpty() && preferenceStore.isInitiator) {
                startMultiplayer()
            } else {
                numbersGameInteractor.listenForNumbers(this)
            }
            numbersGameInteractor.listenForOpponentResult(this)
        }
    }

    fun startSinglePlayer() {
        wantedNumber = getWantedNumber()
        givenNumbers.clear()
        givenNumbers.addAll(getNumbers(6))
        displayNumbers()
    }

    fun startMultiplayer() {
        startSinglePlayer()
        numbersGameInteractor.setRandomNumbers(this, givenNumbers, wantedNumber)
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
        if (wantedNumber != null && wantedNumber != -1) {
            displayNumbers()
        }
    }

    private fun displayNumbers() {
        view.startLevel(totalPoints = totalPoints, wanted = wantedNumber, givenNumbers = givenNumbers)
    }

    fun reset() {
        view.resetLevel(wanted = wantedNumber, givenNumbers = givenNumbers)
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
        view.showProgress()
        if (!preferenceStore.isSinglePlayerEnabled) {
            numbersGameInteractor.finishRound(result)
            isFinishClicked = true
        }
        calculatePoints()
    }

    fun onDestroy() {
        numbersGameInteractor.destroyListeners()
    }

    fun saveOpponentResult(opponentResult: Int) {
        if (this.opponentResult != opponentResult) {
            this.opponentResult = opponentResult
            calculatePoints()
        }
    }

    fun calculatePoints() {
        if (preferenceStore.isSinglePlayerEnabled) {
            points = calculatePointsSinglePlayer(wantedNumber, result)
            totalPoints += points
            preferenceStore.singlePlayerPoints = totalPoints
            view.hideProgress()
            view.navigateToNextLevel(
                points = points,
                result = result
            )
        } else {
            if (opponentResult != -1 && isFinishClicked) {
                points = hr.fer.edugame.ui.shared.helpers.calculatePoints(wantedNumber, result, opponentResult)
                totalPoints += points
                if (totalPoints > POINTS_TO_WIN) {
                    preferenceStore.multiplayerPoints++
                    rankInteractor.savePoints(preferenceStore.getUser())
                    preferenceStore.gamePoints = 0
                    numbersGameInteractor.declareWin()
                    view.hideProgress()
                    view.showGameWon()
                } else {
                    preferenceStore.gamePoints = totalPoints
                    view.hideProgress()
                    view.navigateToNextLevel(
                        points = points,
                        ownResult = result,
                        opponentResult = this.opponentResult
                    )
                }
            }
        }
    }

    fun resetCache() {
        result = START
        opponentResult = NO_CALCULATED_NUMBER
        isFinishClicked = false
    }

    fun handleOpponentWin() {
        preferenceStore.gamePoints = START
        numbersGameInteractor.removeGameRoom()
        view.showGameLost()
    }

    fun startCountdown() {
        countdownObservable
            .applySchedulers(rxSchedulers)
            .subscribe(this,
                onNext = { view.updateProgress(it) },
                onComplete = { handleOoNextLevelClick() }
            )
    }
}
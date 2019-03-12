package hr.fer.edugame.ui.letters

import hr.fer.edugame.data.firebase.interactors.LettersGameInteractor
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.numbers.POINTS_TO_WIN
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.helpers.calculatePointSinglePlayer
import hr.fer.edugame.ui.shared.helpers.calculatePoints
import hr.fer.edugame.ui.shared.helpers.getLetters
import javax.inject.Inject

const val START = 0
const val NO_FINAL_WORD = ""

class LettersPresenter @Inject constructor(
    override val view: LettersView,
    private val preferenceStore: PreferenceStore,
    private val lettersGameInteractor: LettersGameInteractor
) : BasePresenter(view) {

    private var totalPoints: Int = START
    private val givenLetters: MutableList<String> = mutableListOf()
    private var result = NO_FINAL_WORD
    private var opponentResult = NO_FINAL_WORD
    private var points = START
    private var isFinishClicked: Boolean = false

    fun init() {
        resetCache()
        if(preferenceStore.isSinglePlayerEnabled) {
            totalPoints = preferenceStore.singlePlayerPoints
            startSinglePlayer()
        } else {
            if (preferenceStore.isInitiator && preferenceStore.opponentId.isNotEmpty()) {
                totalPoints = preferenceStore.gamePoints
                startMultiplayer()
            } else {
                lettersGameInteractor.listenForLetters(this)
            }
            lettersGameInteractor.listenForOpponentResult(this)
        }
    }

    fun startSinglePlayer() {
        givenLetters.addAll(getLetters(3, 6))
        view.displayLetters(totalPoints, givenLetters)
    }

    fun startMultiplayer() {
        startSinglePlayer()
        lettersGameInteractor.setRandomLetters(this, givenLetters)
    }

    fun onNextLevel(word: String) {
        result = word
        if(!preferenceStore.isSinglePlayerEnabled) {
            lettersGameInteractor.finishRound(result)
            isFinishClicked = true
        }
        calculatePoints()
    }

    fun setLetters(letters: List<String>) {
        resetCache()
        view.displayLetters(totalPoints, letters)
    }

    fun saveOpponentResult(opponentResult: String) {
        if (this.opponentResult != opponentResult) {
            this.opponentResult = opponentResult
            calculatePoints()
        }
    }

    fun calculatePoints() {
        if(preferenceStore.isSinglePlayerEnabled) {
            points = calculatePointSinglePlayer(result)
            totalPoints += points
            preferenceStore.singlePlayerPoints = totalPoints
            view.navigateToNextLevel(result, points)
        }
        if (opponentResult != "" && isFinishClicked) {
            points = calculatePoints(result, opponentResult)
            totalPoints += points
            if (totalPoints > POINTS_TO_WIN) {
                preferenceStore.gamePoints = START
                lettersGameInteractor.declareWin()
                view.showGameWon()
            } else {
                preferenceStore.gamePoints = totalPoints
                view.navigateToNextLevel(
                    points = points,
                    ownResult = result,
                    opponentResult = this.opponentResult
                )
            }
        }
    }

    fun resetCache() {
        result = NO_FINAL_WORD
        opponentResult = NO_FINAL_WORD
        isFinishClicked = false
    }

    fun handleOpponentWin() {
        preferenceStore.gamePoints = START
        lettersGameInteractor.removeGameRoom()
        view.showGameLost()
    }

    fun onDestroy() {
        lettersGameInteractor.destroyListeners()
    }
}
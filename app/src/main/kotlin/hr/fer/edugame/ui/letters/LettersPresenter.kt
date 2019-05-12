package hr.fer.edugame.ui.letters

import hr.fer.edugame.constants.GAME_TURN_DURATION
import hr.fer.edugame.data.firebase.interactors.LettersGameInteractor
import hr.fer.edugame.data.firebase.interactors.RankInteractor
import hr.fer.edugame.data.rx.RxSchedulers
import hr.fer.edugame.data.rx.applySchedulers
import hr.fer.edugame.data.rx.subscribe
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.letters.dialog.LetterType
import hr.fer.edugame.ui.numbers.POINTS_TO_WIN
import hr.fer.edugame.ui.shared.WordsUtil
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.helpers.calculatePointSinglePlayer
import hr.fer.edugame.ui.shared.helpers.calculatePoints
import hr.fer.edugame.ui.shared.helpers.getConsonant
import hr.fer.edugame.ui.shared.helpers.getLetters
import hr.fer.edugame.ui.shared.helpers.getVowel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val START = 0
const val NO_FINAL_WORD = ""
const val EMPTY_WORD = "EMPTY_WORD"

class LettersPresenter @Inject constructor(
    override val view: LettersView,
    private val preferenceStore: PreferenceStore,
    private val lettersGameInteractor: LettersGameInteractor,
    private val rxSchedulers: RxSchedulers,
    private val wordsUtil: WordsUtil
) : BasePresenter(view) {

    private var totalPoints: Int = START
    private val givenLetters: MutableList<String> = mutableListOf()
    private var result = NO_FINAL_WORD
    private var opponentResult = NO_FINAL_WORD
    private var points = START
    private var isFinishClicked: Boolean = false

    private val countdownObservable: Observable<Long> = Observable
        .interval(0, 1, TimeUnit.SECONDS, rxSchedulers.backgroundThreadScheduler)
        .map { GAME_TURN_DURATION - resumeCorrection - it }
        .takeUntil { it <= 0 }

    private var resumeCorrection = 0L

    fun init() {
        resetCache()
        if (preferenceStore.isSinglePlayerEnabled) {
            totalPoints = preferenceStore.singlePlayerPoints
            startSinglePlayer()
        } else {
            if (preferenceStore.isInitiator && preferenceStore.opponentId.isNotEmpty()) {
                totalPoints = preferenceStore.gamePoints
                view.showChooseType()
            } else {
                view.showOpponentTurnToChoose(givenLetters)
            }
            lettersGameInteractor.listenForLetters(this)
            lettersGameInteractor.listenForOpponentResult(this)
        }
    }

    fun startSinglePlayer() {
        givenLetters.addAll(getLetters(3, 6))
        view.displayLetters(totalPoints, givenLetters)
    }

    fun setNewLetter(type: LetterType) {
        when(type) {
            LetterType.VOWEL -> {
                givenLetters.add(getVowel().toString())
            }
            LetterType.CONSOANT -> givenLetters.add(getConsonant().toString())
        }
        lettersGameInteractor.setRandomLetters(this, givenLetters)
        if(givenLetters.size == 9) {
            view.displayLetters(totalPoints, givenLetters)
        } else {
            view.showOpponentTurnToChoose(givenLetters)
        }
    }

    fun setLetters(letters: List<String>) {
        resetCache()
        givenLetters.clear()
        givenLetters.addAll(letters)
        if(letters.size == 9) {
            view.displayLetters(totalPoints, givenLetters)
        } else {
            view.displayLettersAndChooseNext(givenLetters)
        }
    }

    fun startMultiplayer() {
        startSinglePlayer()
        lettersGameInteractor.setRandomLetters(this, givenLetters)
    }

    fun onNextLevel(word: String) {
        view.showProgress()
        result = word
        if (!preferenceStore.isSinglePlayerEnabled) {
            lettersGameInteractor.finishRound(result)
            isFinishClicked = true
        }
        if (opponentResult.isNotEmpty())
            calculatePoints()
    }

    fun onSaveClicked(word: String) {
        view.showProgress()
        if (wordsUtil.checkIfWordExists(word)) {
            view.saveWord(word)
        } else {
            view.showNoSuchWord()
        }
        view.hideProgress()
    }

    fun saveOpponentResult(opponentResult: String) {
        if (this.opponentResult != opponentResult && opponentResult.isNotEmpty()) {
            this.opponentResult = opponentResult
            calculatePoints()
        }
    }

    fun calculatePoints() {
        if (preferenceStore.isSinglePlayerEnabled) {
            points = calculatePointSinglePlayer(result)
            totalPoints += points
            preferenceStore.singlePlayerPoints = totalPoints
            view.hideProgress()
            view.navigateToNextLevel(result, points)
        }
        if (isFinishClicked && this.opponentResult.isNotEmpty()) {
            points = calculatePoints(result, opponentResult)
            totalPoints += points
            if (totalPoints > POINTS_TO_WIN) {
                preferenceStore.gamePoints = START
                lettersGameInteractor.declareWin()
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

    fun startCountdown() {
        countdownObservable
            .applySchedulers(rxSchedulers)
            .subscribe(this,
                onNext = { view.updateProgress(it) },
                onComplete = { onNextLevel(view.getLongestWord()) }
            )
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
        preferenceStore.isInitiator = !preferenceStore.isInitiator
        lettersGameInteractor.destroyListeners()
    }
}
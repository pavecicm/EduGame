package hr.fer.edugame.ui.letters

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import hr.fer.edugame.R
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.shared.adapters.WordsListAdapter
import hr.fer.edugame.ui.shared.base.BaseFragment
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.listeners.HomeListener
import kotlinx.android.synthetic.main.fragment_letters.newWordView
import kotlinx.android.synthetic.main.fragment_letters.wordsList
import kotlinx.android.synthetic.main.view_navigation.backBtn
import kotlinx.android.synthetic.main.view_navigation.navigationTitle
import kotlinx.android.synthetic.main.view_navigation.nextBtn
import javax.inject.Inject

class LettersFragment : BaseFragment(), LettersView {

    companion object {
        fun newInstance() = LettersFragment()
    }

    override val layoutRes: Int = R.layout.fragment_letters
    override fun providePresenter(): BasePresenter? = presenter

    @Inject
    lateinit var presenter: LettersPresenter
    private lateinit var homeListener: HomeListener
    private lateinit var wordsAdapter: WordsListAdapter
    private var dialog: Dialog? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeListener) {
            homeListener = context
        } else {
            throw RuntimeException(activity?.localClassName + " must implement " + HomeListener::class.java.name)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        presenter.init()
    }

    fun initUI() {
        backBtn.setThrottlingClickListener {
            activity?.onBackPressed()
        }
        navigationTitle.setText(R.string.letters)
        nextBtn.setThrottlingClickListener {
            presenter.onNextLevel(wordsAdapter.getLongestWord())
        }
    }

    override fun displayLetters(points: Int, letters: List<String>) {
        navigationTitle.text = String.format(getString(R.string.points), points)
        wordsAdapter = WordsListAdapter()
        wordsList.adapter = wordsAdapter
        newWordView.initLettersList(
            letters = letters,
            onSaveClickListener = {
                wordsAdapter.updateItem(it)
            })
    }

    override fun navigateToNextLevel(ownResult: String, opponentResult: String, points: Int) {
        dialog = AlertDialog.Builder(context)
            .setMessage(String.format(getString(R.string.result_letters), ownResult, opponentResult, points))
            .setPositiveButton(R.string.ok)
            { _, _ ->
                homeListener.onNavigateToNumbers()
            }
            .create()
        dialog?.let {
            it.show()
        }
    }

    override fun navigateToNextLevel(result: String, points: Int) {
        dialog = AlertDialog.Builder(context)
            .setMessage(String.format(getString(R.string.result_letters_single_player), result, points))
            .setPositiveButton(R.string.ok)
            { _, _ ->
                homeListener.onNavigateToNumbers()
            }
            .create()
        dialog?.let {
            it.show()
        }
    }

    override fun onStop() {
        dialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
        super.onStop()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}
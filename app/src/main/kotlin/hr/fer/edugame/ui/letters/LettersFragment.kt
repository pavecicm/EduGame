package hr.fer.edugame.ui.letters

import android.os.Bundle
import android.view.View
import hr.fer.edugame.R
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.shared.adapters.WordsListAdapter
import hr.fer.edugame.ui.shared.base.BaseFragment
import hr.fer.edugame.ui.shared.base.BasePresenter
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
    private lateinit var wordsAdapter: WordsListAdapter

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
            presenter.onNextLevel()
        }
    }

    override fun displayLetters(letters: List<Char>) {
        wordsAdapter = WordsListAdapter()
        wordsList.adapter = wordsAdapter
        newWordView.initLettersList(
            letters = letters,
            onSaveClickListener = {
                wordsAdapter.updateItem(it)
            })
    }

    override fun showNextLevel(letters: List<Char>) {
        wordsAdapter.resetAdapter()
        newWordView.resetLettersList(letters)
    }
}
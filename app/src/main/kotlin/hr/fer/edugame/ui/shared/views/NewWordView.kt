package hr.fer.edugame.ui.shared.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.util.AttributeSet
import android.widget.GridLayout
import android.widget.LinearLayout
import hr.fer.edugame.R
import hr.fer.edugame.extensions.inflate
import hr.fer.edugame.extensions.setPaddingHorizontal
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.extensions.toLetter
import hr.fer.edugame.ui.letters.Letter
import hr.fer.edugame.ui.shared.adapters.LettersListAdapter
import kotlinx.android.synthetic.main.view_new_word_input.view.deleteBtn
import kotlinx.android.synthetic.main.view_new_word_input.view.destroyBtn
import kotlinx.android.synthetic.main.view_new_word_input.view.lettersList
import kotlinx.android.synthetic.main.view_new_word_input.view.newWordContainer
import kotlinx.android.synthetic.main.view_new_word_input.view.saveBtn

class NewWordView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {

    private var letters: MutableList<Letter> = mutableListOf()
    private lateinit var lettersAdapter: LettersListAdapter
    private lateinit var onSaveClickListener: (word: String) -> Unit

    private val currentWordList: MutableList<Letter> = mutableListOf()

    init {
        inflate(R.layout.view_new_word_input, true)
    }

    fun initLettersList(letters: List<String> = listOf(), onSaveClickListener: (word: String) -> Unit) {
        resetLetters(letters)
        lettersAdapter = LettersListAdapter(this.letters) {
            onLetterClicked(letter = it)
        }

        with(lettersList) {
            val gridLayoutManager = GridLayoutManager(context, 2)
            gridLayoutManager.orientation = GridLayout.HORIZONTAL
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            addItemDecoration(HorizontalSpaceItemDecorator(R.dimen.spacing_1x))
            adapter = lettersAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        this.onSaveClickListener = onSaveClickListener

        deleteBtn.setOnClickListener {
            if (currentWordList.isNotEmpty()) {
                lettersAdapter.setItemClickable(currentWordList.last())
                currentWordList.dropLast(1)
                newWordContainer.removeViewAt(currentWordList.size)
            }
        }
        destroyBtn.setThrottlingClickListener {
            resetView()
        }
        saveBtn.setThrottlingClickListener {
            if (currentWordList.isNotEmpty()) {
                onSaveClickListener(getCurrentWord())
                resetView()
            }
        }
    }

    fun getCurrentWord(): String {
        var word = ""
        currentWordList.forEach { word += it.letter }
        return word
    }


    fun resetLetters(letters: List<String>) {
        this.letters.clear()
        var index = 0
        letters.forEach {
            this.letters.add(it.toLetter(index++))
        }
    }

    fun onLetterClicked(letter: Letter) {
        val newLetter = letter.copy(isClickable = false)
        lettersAdapter.updateItem(newLetter)
        currentWordList.add(letter)
        addViews(newWordContainer, 1, newLetter.letter)
    }

    fun resetLettersList(letters: List<String>) {
        resetLetters(letters)
        lettersAdapter.initNewLetters(this.letters)
        resetView()
    }

    private fun addViews(viewContainer: LinearLayout, numberOfViews: Int, letter: String) {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//        params.setMargins(R.dimen.spacing_0_5x)
        IntRange(1, numberOfViews).forEach { _ ->
            viewContainer.addView(
                AppCompatTextView(context).apply {
                    background = resources.getDrawable(R.drawable.bg_new_letter_btn)
                    textSize = 32f
                    setTextColor(resources.getColor(R.color.light_gray))
                    text = letter
                    setPaddingHorizontal(R.dimen.spacing_1_5x)
                    setThrottlingClickListener {
                        val position = viewContainer.indexOfChild(this)
                        lettersAdapter.setItemClickable(currentWordList[position])
                        currentWordList.removeAt(position)
                        viewContainer.removeViewAt(viewContainer.indexOfChild(this))
                    }
                },
                params
            )
        }
    }

    private fun resetView() {
        lettersAdapter.updateItems(letters)
        currentWordList.clear()
        newWordContainer.removeAllViews()
    }
}
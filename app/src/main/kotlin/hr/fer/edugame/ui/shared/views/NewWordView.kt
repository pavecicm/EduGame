package hr.fer.edugame.ui.shared.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import hr.fer.edugame.R
import hr.fer.edugame.extensions.inflate
import hr.fer.edugame.extensions.setPaddingHorizontal
import hr.fer.edugame.extensions.setThrottlingClickListener
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
) : ConstraintLayout(context, attr, defStyleAttr), View.OnClickListener {

    private var letters: MutableList<String> = mutableListOf()
    private lateinit var lettersAdapter: LettersListAdapter
    private lateinit var onSaveClickListener: (word: String) -> Unit

    private var currentWord: String = ""

    init {
        inflate(R.layout.view_new_word_input, true)
    }

    fun initLettersList(letters: List<String>, onSaveClickListener: (word: String) -> Unit) {
        this.letters.clear()
        this.letters.addAll(letters)
        lettersAdapter = LettersListAdapter(this.letters, this)

        with(lettersList) {
            val gridLayoutManager = GridLayoutManager(context, 2)
            gridLayoutManager.orientation = GridLayout.HORIZONTAL
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            addItemDecoration(HorizontalSpaceItemDecorator(R.dimen.spacing_1x))
            adapter = lettersAdapter
        }

        this.onSaveClickListener = onSaveClickListener

        deleteBtn.setOnClickListener {
            if (currentWord.isNotEmpty()) {
                lettersAdapter.updateItem(currentWord.last().toString())
                currentWord = currentWord.dropLast(1)
                newWordContainer.removeViewAt(currentWord.length)
            }
        }
        destroyBtn.setThrottlingClickListener {
            resetView()
        }
        saveBtn.setThrottlingClickListener {
            if (currentWord.isNotEmpty()) {
                onSaveClickListener(currentWord)
                resetView()
            }
        }

    }

    fun resetLettersList(letters: List<String>) {
        this.letters.clear()
        this.letters = letters.toMutableList()
        lettersAdapter.initNewLetters(this.letters)
        resetView()
    }

    override fun onClick(v: View?) {
        val newLetter = (v as TextView).text
        lettersAdapter.destroyItem(newLetter.toString())
        currentWord += newLetter
        addViews(newWordContainer, 1, newLetter.toString())
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
                        lettersAdapter.updateItem(letter = letter.toString())
                        currentWord = currentWord.removeRange(position, position + 1)
                        viewContainer.removeViewAt(viewContainer.indexOfChild(this))
                    }
                },
                params
            )
        }
    }

    private fun resetView() {
        val mutableList = mutableListOf<String>()
        currentWord.toList().forEach {
            mutableList.add(it.toString())
        }
        lettersAdapter.updateItems(mutableList)
        newWordContainer.removeAllViews()
        currentWord = ""
    }
}
package hr.fer.edugame.ui.letters.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import hr.fer.edugame.R
import hr.fer.edugame.extensions.setThrottlingClickListener
import hr.fer.edugame.ui.shared.base.BaseDialogFragment
import hr.fer.edugame.ui.shared.base.BasePresenter
import hr.fer.edugame.ui.shared.base.BaseView
import kotlinx.android.synthetic.main.dialog_fragment_choose_letter.consoant
import kotlinx.android.synthetic.main.dialog_fragment_choose_letter.vowel
import javax.inject.Inject

class ChooseLetterTypeDialog : BaseDialogFragment(), BaseView {

    override val layoutRes: Int = R.layout.dialog_fragment_choose_letter
    override fun providePresenter(): BasePresenter? = presenter

    @Inject
    lateinit var presenter: ChooseLetterTypePresenter

    companion object {
        private const val EXTRA_ACTIONS_LIST = "EXTRA_ACTIONS_LIST"
        fun newInstance() = ChooseLetterTypeDialog()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.let {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setCanceledOnTouchOutside(true)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vowel.setThrottlingClickListener {

        }
        consoant.setThrottlingClickListener {

        }
    }
}
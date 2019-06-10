package hr.fer.edugame.ui.shared.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.CircularProgressDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import hr.fer.edugame.R
import kotlinx.android.synthetic.main.dialog_loader.progressBar
import timber.log.Timber

private const val DIM = 0f

class LoadingDialog : DialogFragment() {

    companion object {
        fun newInstance() = LoadingDialog()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            with(it) {
                setDimAmount(DIM)
                addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawable = CircularProgressDrawable(requireContext())
        progressBar.progressDrawable = drawable
        progressBar.indeterminateDrawable = drawable
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.dialog_loader, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LoadingDialog)
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        try {
            val ft = manager?.beginTransaction()
            ft?.add(this, tag)
            ft?.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Timber.e(e)
        }
    }
}
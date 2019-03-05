package hr.fer.edugame.ui.shared.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(), BaseView {

    @get:LayoutRes
    abstract val layoutRes: Int

    protected abstract fun providePresenter(): BasePresenter?

    private lateinit var baseActivity: BaseActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onAttach(context: Context?) {
        if (context is BaseActivity) {
            baseActivity = context
        } else {
            throw RuntimeException("Every activity MUST extend BaseActivity!")
        }
        super.onAttach(requireContext())
    }

    override fun showKeyboard() = baseActivity.showKeyboard()

    override fun hideKeyboard() = baseActivity.hideKeyboard()

    override fun showLevelCompleted() = baseActivity.showLevelCompleted()
}
package hr.fer.edugame.ui.shared.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter(
    open val view: BaseView
) {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun cancel() {
        compositeDisposable.clear()
    }
}
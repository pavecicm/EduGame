package hr.fer.edugame.data.rx.observers

import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ErrorHandlingCompletableObserver(
    private val compositeDisposable: CompositeDisposable
//    private val errorView: ErrorView
) : CompletableObserver {

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(t: Throwable) {
//        RetrofitUtils.handleException(t, errorView)
    }
}
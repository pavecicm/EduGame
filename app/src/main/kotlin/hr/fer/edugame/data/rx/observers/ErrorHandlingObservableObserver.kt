package hr.fer.edugame.data.rx.observers

import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ErrorHandlingObservableObserver<T>(
    private val compositeDisposable: CompositeDisposable
//    private val errorView: ErrorView
) : Observer<T> {

    override fun onComplete() {
        // override if needed
    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
//        RetrofitUtils.handleException(e, errorView)
    }
}
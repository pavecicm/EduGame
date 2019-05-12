package hr.fer.edugame.data.rx

import hr.fer.edugame.data.rx.observers.ErrorHandlingObservableObserver
import hr.fer.edugame.ui.shared.base.BasePresenter
import io.reactivex.Observable

fun <T> Observable<T>.applySchedulers(rxSchedulers: RxSchedulers): Observable<T> {
    return this.subscribeOn(rxSchedulers.backgroundThreadScheduler).observeOn(rxSchedulers.mainThreadScheduler)
}

fun <T> Observable<T>.subscribe(basePresenter: BasePresenter, onNext: (T) -> Unit, onComplete: (() -> Unit)) {
    this.subscribe(object : ErrorHandlingObservableObserver<T>(basePresenter.compositeDisposable) {
        override fun onNext(t: T) {
            onNext(t)
        }

        override fun onComplete() {
            onComplete()
        }
    })
}
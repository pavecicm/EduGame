package hr.fer.edugame.ui.shared.base

import hr.fer.edugame.data.firebase.interactors.RankInteractor
import hr.fer.edugame.data.models.User
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.ui.shared.helpers.getUser
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter(
    open val view: BaseView
) {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun cancel() {
        compositeDisposable.clear()
    }
}
package hr.fer.edugame.data.rx

import io.reactivex.Scheduler

data class RxSchedulers(
    val backgroundThreadScheduler: Scheduler,
    val mainThreadScheduler: Scheduler
)
package com.raiden.core.utils.rx.schedule

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun mainThread(): Scheduler
    fun io(): Scheduler
    fun computation(): Scheduler
}
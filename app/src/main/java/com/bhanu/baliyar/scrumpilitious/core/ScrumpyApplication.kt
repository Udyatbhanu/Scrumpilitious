package com.bhanu.baliyar.scrumpilitious.core

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bhanu.baliyar.scrumpilitious.core.notifications.NotifyWorker
import com.bhanu.baliyar.scrumpilitious.core.notifications.WorkerFactoryEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ScrumpyApplication : Application(), Configuration.Provider {


    override fun onCreate() {
        super.onCreate()
        scheduleNotifyWorker()
    }

    private fun scheduleNotifyWorker() {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<NotifyWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "notify_worker", // unique name
            ExistingPeriodicWorkPolicy.KEEP, // keep if already scheduled
            periodicWorkRequest
        )
    }

    override val workManagerConfiguration: Configuration
        get() {
            val workerFactory = EntryPointAccessors.fromApplication(
                this,
                WorkerFactoryEntryPoint::class.java
            ).workerFactory()
            return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        }
}
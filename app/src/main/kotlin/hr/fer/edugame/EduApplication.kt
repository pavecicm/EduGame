package hr.fer.edugame

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import hr.fer.edugame.di.components.AppComponent
import hr.fer.edugame.di.components.DaggerAppComponent
import timber.log.Timber
import com.jakewharton.threetenabp.AndroidThreeTen

class EduApplication : DaggerApplication() {

    companion object {
        lateinit var instance: EduApplication
            private set

        lateinit var appComponent: AppComponent
            private set
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.create()
        return appComponent
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        initLogging()
        initDateTimeLibrary()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initDateTimeLibrary() {
        AndroidThreeTen.init(this)
    }
}
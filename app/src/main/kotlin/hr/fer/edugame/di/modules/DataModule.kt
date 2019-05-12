package hr.fer.edugame.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import hr.fer.edugame.constants.PREF_USER
import javax.inject.Singleton

@Module
class DataModule {

   @Provides
    @Singleton
    fun provideSharedPreferences(app: Application) = app.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE)
}
package hr.fer.edugame.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import hr.fer.edugame.data.storage.prefs.PreferenceStore
import hr.fer.edugame.data.storage.prefs.SharedPreferenceStore
import hr.fer.edugame.di.qualifiers.AppContext
import javax.inject.Singleton

@Module
class MenagersModules {
    
    @Provides
    @Singleton
    fun providePreferenceStorage(@AppContext context: Context): PreferenceStore = SharedPreferenceStore(context)
}
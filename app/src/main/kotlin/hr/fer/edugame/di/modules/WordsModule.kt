package hr.fer.edugame.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import hr.fer.edugame.di.qualifiers.AppContext
import hr.fer.edugame.ui.shared.WordsUtil
import javax.inject.Singleton

@Module
class WordsModule {

    @Provides
    @Singleton
    fun provideWordsModule(@AppContext context: Context) = WordsUtil(context)
}
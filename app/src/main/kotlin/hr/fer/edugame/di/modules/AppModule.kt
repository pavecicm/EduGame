package hr.fer.edugame.di.modules

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import hr.fer.edugame.EduApplication
import hr.fer.edugame.di.qualifiers.AppContext
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    @AppContext
    fun provideAppContext(): Context = EduApplication.instance

    @Provides
    fun provideResources(@AppContext context: Context): Resources = context.resources
}
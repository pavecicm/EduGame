package hr.fer.edugame.di.modules

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import hr.fer.edugame.EduApplication
import hr.fer.edugame.data.rx.RxSchedulers
import hr.fer.edugame.di.qualifiers.AppContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    @AppContext
    fun provideAppContext(): Context = EduApplication.instance

    @Provides
    fun provideResources(@AppContext context: Context): Resources = context.resources

    @Provides
    @Singleton
    fun provideSchedulers() = RxSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())
}
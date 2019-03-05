package hr.fer.edugame.di.components

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import hr.fer.edugame.EduApplication
import hr.fer.edugame.di.DiBuilder
import hr.fer.edugame.di.modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    DiBuilder::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<EduApplication> {
}
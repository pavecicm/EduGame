package hr.fer.edugame.ui.login

import dagger.Binds
import dagger.Module
import hr.fer.edugame.di.modules.FirebaseModule

@Module(includes = [FirebaseModule::class])
abstract class LoginModule {

    @Binds
    abstract fun bindView(activity: LoginActivity): LoginView
}
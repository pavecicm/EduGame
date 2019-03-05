package hr.fer.edugame.ui.login

import dagger.Binds
import dagger.Module

@Module
abstract class LoginModule {

    @Binds
    abstract fun bindView(activity: LoginActivity): LoginView
}
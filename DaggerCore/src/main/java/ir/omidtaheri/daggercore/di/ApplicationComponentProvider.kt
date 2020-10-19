package ir.omidtaheri.daggercore.di

import ir.omidtaheri.daggercore.di.components.ApplicationComponent

interface ApplicationComponentProvider {

    fun provideApplicationComponent(): ApplicationComponent
}

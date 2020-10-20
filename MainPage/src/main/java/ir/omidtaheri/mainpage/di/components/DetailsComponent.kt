package ir.omidtaheri.mainpage.di.components

import dagger.Component
import ir.omidtaheri.mainpage.di.modules.DetailsModule
import ir.omidtaheri.daggercore.di.components.ApplicationComponent
import ir.omidtaheri.mainpage.ui.DetailFragment.DetailsFragment

@Component(dependencies = [ApplicationComponent::class], modules = [DetailsModule::class])
interface DetailsComponent {

     fun inject(fragment: DetailsFragment)
}

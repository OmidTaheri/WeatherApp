package ir.omidtaheri.mainpage.di.modules

import dagger.Binds
import dagger.Module
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import ir.omidtaheri.daggercore.di.scopes.FragmentScope
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailsViewModel
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailsViewModelFactory

@Module
interface DetailsModule {

    @FragmentScope
    @Binds
    fun provideDetailViewModel(viewmodel: DetailsViewModelFactory): ViewModelAssistedFactory<DetailsViewModel>
}

package ir.omidtaheri.mainpage.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel.DetailsViewModelFactory

@Module
class DetailsModule {

    @Provides
    fun provideDetailViewModel(viewmodel: DetailsViewModelFactory): ViewModelProvider.Factory {
        return viewmodel
    }
}

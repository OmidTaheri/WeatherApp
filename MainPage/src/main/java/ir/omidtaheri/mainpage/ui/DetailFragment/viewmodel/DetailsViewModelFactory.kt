package ir.omidtaheri.mainpage.ui.DetailFragment.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ir.omidtaheri.androidbase.viewmodelutils.ViewModelAssistedFactory
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(
    private val application: Application
) : ViewModelAssistedFactory<DetailsViewModel> {
    override fun create(handle: SavedStateHandle): DetailsViewModel {
        return DetailsViewModel(
            handle,
            application
        )
    }

}

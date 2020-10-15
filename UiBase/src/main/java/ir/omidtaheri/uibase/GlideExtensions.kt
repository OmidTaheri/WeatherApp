package ir.omidtaheri.uibase

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.LoadPoster(posterPath: String, myConetxt: Context) {

    val requestOptions = RequestOptions()
    requestOptions.apply {
        placeholder(R.drawable.ic_baseline_local_movies_24)
    }

    GlideApp.with(myConetxt)
        .load(BuildConfig.POSTER_URL + posterPath)
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .override(160, 160)
        .thumbnail(0.4f)
        .apply(requestOptions)
        .into(this)
}


fun ImageView.clear(myconetxt: Context) {
    GlideApp.with(myconetxt)
        .clear(this)
}


fun Fragment.onDestroyGlide() {
    GlideApp.get(requireContext()).clearMemory()
}
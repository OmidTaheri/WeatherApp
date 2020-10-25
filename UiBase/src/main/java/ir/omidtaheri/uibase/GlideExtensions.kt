package ir.omidtaheri.uibase

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun ImageView.LoadWeatherIcon(iconName: String, myConetxt: Context) {

    GlideApp.with(myConetxt)
        .load(getImage(iconName, myConetxt))
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}


fun View.LoadBackgroungImage(imageName: String, myConetxt: Context) {

    GlideApp.with(myConetxt)
        .load(getImage(imageName, myConetxt))
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .override(350, 540)
        .into<CustomTarget<Drawable>>(
            object : CustomTarget<Drawable>() {

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {

                    background = resource

                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
}


fun getImage(imageName: String, context: Context): Int {
    return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName())
}

fun View.clear() {
    GlideApp.with(this)
        .clear(this)
}


fun Fragment.onDestroyGlide() {
    GlideApp.get(requireContext()).clearMemory()
}

package ir.omidtaheri.uibase

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun ImageView.LoadWeatherIcon(iconName: String, myConetxt: Context) {

    GlideApp.with(myConetxt)
        .load(getImage(iconName, myConetxt))
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}


fun View.LoadBackgroungImage(imageName: String, myConetxt: Context) {

    GlideApp.with(myConetxt)
        .load(getImage(imageName, myConetxt))
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .override(700, 1080)
        .into<CustomTarget<Drawable>>(
            object : CustomTarget<Drawable>() {

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {

                    val a = animate().alpha(0F).setDuration(200)
                        .setListener(object : Animator.AnimatorListener{
                            override fun onAnimationStart(animation: Animator?) {
                            }

                            override fun onAnimationEnd(animation: Animator?) {
                                background = resource
                                animate().alpha(1F).setDuration(400).start()
                            }

                            override fun onAnimationCancel(animation: Animator?) {
                            }

                            override fun onAnimationRepeat(animation: Animator?) {
                            }

                        })

                    a.start()

                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
}

fun getImage(imageName: String, context: Context): Int {
    return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName())
}

fun ImageView.clear(myconetxt: Context) {
    GlideApp.with(myconetxt)
        .clear(this)
}


fun Fragment.onDestroyGlide() {
    GlideApp.get(requireContext()).clearMemory()
}

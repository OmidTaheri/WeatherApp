package ir.omidtaheri.uibase

import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette


// Generate palette synchronously and return it
fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

// Generate palette asynchronously and use it on a different
// thread using onGenerated()
fun createPaletteAsync(bitmap: Bitmap) {
    Palette.from(bitmap).generate { palette ->
        // Use generated instance
    }
}

@ColorInt
fun adjustAlpha(@ColorInt color: Int, alpha: Int): Int {
    val red: Int = Color.red(color)
    val green: Int = Color.green(color)
    val blue: Int = Color.blue(color)
    return Color.argb(alpha, red, green, blue)
}
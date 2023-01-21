package space.intbh.wtl.util

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun getImageFromAssets(imageName: String, assetManager: AssetManager): ImageBitmap {
    val imageStream = assetManager.open(imageName)
    val bitmap = BitmapFactory.decodeStream(imageStream)
    imageStream.close()
    return bitmap.asImageBitmap()
}
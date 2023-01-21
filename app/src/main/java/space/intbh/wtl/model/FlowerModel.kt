package space.intbh.wtl.model

import android.content.res.AssetManager
import androidx.compose.ui.graphics.ImageBitmap
import com.google.gson.JsonObject
import space.intbh.wtl.util.getImageFromAssets

data class FlowerModel(
    val name: String,
    val months: List<String>,
    val category: String,
    val picture: ImageBitmap
) {
    companion object {
        fun fromJson(json: JsonObject, assetManager: AssetManager): FlowerModel {
            val name = json.get("name").asString
            val months = json.get("months").asString.split(",")
            val category = json.get("category").asString
            val pictureName = json.get("picture").asString
            val picture  = getImageFromAssets(pictureName, assetManager)
            return FlowerModel(name, months, category, picture)
        }
    }
}

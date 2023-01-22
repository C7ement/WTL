package space.intbh.wtl.model

import android.content.res.AssetManager
import androidx.compose.ui.graphics.ImageBitmap
import com.google.gson.JsonObject
import space.intbh.wtl.util.getImageFromAssets

enum class Month {
    January, February, March, April, May, June, July, August, September, October, November, December;
    companion object {
        fun fromName(name: String): Month {
            when (name) {
                "Janvier" -> return January
                "Février" -> return February
                "Mars" -> return March
                "Avril" -> return April
                "Mai" -> return May
                "Juin" -> return June
                "Juillet" -> return July
                "Aout" -> return August
                "Septembre" -> return September
                "Octobre" -> return October
                "Novembre" -> return November
                "Décembre" -> return December
                else -> {
                    print(name)
                    assert(false)
                    return January
                }
            }
        }
    }
}

data class FlowerModel(
    val name: String,
    val months: List<Month>,
    val category: String,
    val picture: ImageBitmap
) {
    companion object {
        fun fromJson(json: JsonObject, assetManager: AssetManager): FlowerModel {
            val name = json.get("name").asString
            val months = json.get("months").asString.split(",")
            val category = json.get("category").asString
            val pictureName = json.get("picture").asString
            val picture = getImageFromAssets(pictureName, assetManager)
            return FlowerModel(name, months.map { Month.fromName(it) }, category, picture)
        }
    }
}

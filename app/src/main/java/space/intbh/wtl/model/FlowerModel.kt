package space.intbh.wtl.model

import android.content.res.AssetManager
import androidx.compose.ui.graphics.ImageBitmap
import com.google.gson.JsonObject
import space.intbh.wtl.util.getImageFromAssets

enum class Month {
    Janvier, Février, Mars, Avril, Mai, Juin, Juillet, Aout, Septembre, Octobre, Novembre, Décembre;
    companion object {
        fun fromName(name: String): Month {
            when (name) {
                "Janvier" -> return Janvier
                "Février" -> return Février
                "Mars" -> return Mars
                "Avril" -> return Avril
                "Mai" -> return Mai
                "Juin" -> return Juin
                "Juillet" -> return Juillet
                "Aout" -> return Aout
                "Septembre" -> return Septembre
                "Octobre" -> return Octobre
                "Novembre" -> return Novembre
                "Décembre" -> return Décembre
                else -> {
                    print(name)
                    assert(false)
                    return Janvier
                }
            }
        }
        fun getName(index: Int) : String{
            return values()[index].name
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

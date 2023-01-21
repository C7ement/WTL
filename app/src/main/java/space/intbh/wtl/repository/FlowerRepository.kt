package space.intbh.wtl.repository

import android.content.res.AssetManager
import android.content.res.Resources
import com.google.gson.JsonParser
import space.intbh.wtl.R
import space.intbh.wtl.model.FlowerModel

class FlowerRepository(
    private val resources: Resources,
    private val assetManager: AssetManager
) {
    fun getFlowerList(): List<FlowerModel> {
        val inputStream = resources.openRawResource(R.raw.flowers)
        val inputString = inputStream.bufferedReader().use { it.readText() }

        val json = JsonParser.parseString(inputString).asJsonArray
        return json.map { FlowerModel.fromJson(it.asJsonObject,  assetManager) }
    }
}
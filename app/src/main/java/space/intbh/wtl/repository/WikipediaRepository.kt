package space.intbh.wtl.repository

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.intbh.wtl.model.*

class WikipediaRepository {
    private val wikipediaApi = Retrofit.Builder()
        .baseUrl("https://en.wikipedia.org/w/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WikipediaApi::class.java)
    private val wikipediaSearchApi = Retrofit.Builder()
        .baseUrl("https://en.wikipedia.org/w/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WikipediaSearchApi::class.java)

    suspend fun search(searchText: String): List<WikipediaSearchResultItem> {
        val response = wikipediaSearchApi.search(searchText)
        val search = response.body()?.query?.search
        Log.d("getpage", response.toString())
        return search ?: listOf()
    }

    suspend fun getPageContent(pageId: String): WikipediaPageContent {
        val response = wikipediaApi.getPageContent(pageId)
        val pages = response.body()?.query?.pages
        if (pages != null) {
            for ((_, page) in pages) {
                return page
            }
        }
        Log.d(
            "getpage",
            response.toString()
        )
        return WikipediaPageContent(0, "", "")
    }

    suspend fun searchAndGEtPage(pageTitle: String): String {

        val res = search(pageTitle)
        if (res.isEmpty()) return "No Results"
        val res2 = getPageContent(res.first().pageid.toString())
        Log.d(
            "getpage",
            res2.toString()
        )
        return res2.extract
        // Use the page object here
    }

}

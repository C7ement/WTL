package space.intbh.wtl.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.intbh.wtl.model.*

class WikipediaRepository {
    private val wikipediaApi = Retrofit.Builder()
        .baseUrl("https://fr.wikipedia.org/w/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WikipediaApi::class.java)
    private val wikipediaSearchApi = Retrofit.Builder()
        .baseUrl("https://fr.wikipedia.org/w/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WikipediaSearchApi::class.java)

    suspend fun search(searchText: String): List<WikipediaSearchResultItem> {
        val response = wikipediaSearchApi.search(searchText)
        val search = response.body()?.query?.search
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
        return WikipediaPageContent(0, "", "")
    }

    suspend fun searchAndGEtPage(pageTitle: String): SearchResult {

        val res = search(pageTitle.split("-").first())

        if (res.isEmpty()) return SearchResult(res, WikipediaPageContent(0,"0","No Results"))
        val res2 = getPageContent(res.first().pageid.toString())
        return SearchResult(res, res2)
        // Use the page object here
    }



}

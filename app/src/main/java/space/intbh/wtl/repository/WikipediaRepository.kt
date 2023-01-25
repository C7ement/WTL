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
        return pages?.values?.firstOrNull()
            ?: WikipediaPageContent(0, "", "Auncun résultats")
    }

    suspend fun searchAndGEtPage(pageTitle: String): WikipediaPageContent {

        val searchResult = search(pageTitle.split("-").first())

        if (searchResult.isEmpty())
            return WikipediaPageContent(0, "", "Auncun résultats")
        return getPageContent(searchResult.first().pageid.toString())
    }


}

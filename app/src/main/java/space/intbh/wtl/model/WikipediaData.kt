package space.intbh.wtl.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

data class WikipediaPage(val title: String, val content: String)

interface WikipediaApi {
    @GET("api.php?action=query&format=json&prop=extracts&exintro=&explaintext=")
    suspend fun getPageContent(@Query("pageids") pageId: String): Response<WikipediaPageContentResponse>
}

data class WikipediaPageContentResponse(val query: WikipediaPageQuery)
data class WikipediaPageQuery(val pages: Map<String, WikipediaPageContent>)
data class WikipediaPageContent(val pageid: Int, val title: String, val extract: String)

interface WikipediaSearchApi {
    @GET("api.php?action=query&format=json&list=search&utf8=1&formatversion=2")
    suspend fun search(@Query("srsearch") searchText: String): Response<WikipediaSearchResponse>
}

data class WikipediaSearchResponse(val query: WikipediaSearchResult)
data class WikipediaSearchResult(val search: List<WikipediaSearchResultItem>)
data class WikipediaSearchResultItem(val pageid: Int, val title: String, val snippet: String)

package br.com.hearthisat.service.endpoint

import br.com.hearthisat.model.Artist
import br.com.hearthisat.model.Track
import br.com.hearthisat.service.config.APIConfiguration
import br.com.hearthisat.service.config.EndpointProtocol
import okhttp3.Headers
import retrofit2.Call
import retrofit2.http.*

data class GeneralEndpoint(private val route: String) : EndpointProtocol<GeneralEndpoint.Endpoint> {
    var headers = APIConfiguration.HttpHeaders.Headers
    var contentType = APIConfiguration.HttpHeaders.ContentType

    override fun headers() : Headers {
        return buildHeaders(headers = mapOf(headers.contentType to contentType.json))
    }

    override fun endpoints(): Class<Endpoint> {
        return Endpoint::class.java
    }

    interface Endpoint {
        @GET(APIConfiguration.General.feed)
        fun getFeed(@Query("page") page: Int,
                    @Query("type") type: String = "popular",
                    @Query("count") count: Int = 20): Call<List<Track>>

        @GET(APIConfiguration.General.artist)
        fun getArtist(@Path("artist") artist: String): Call<Artist>

        @GET(APIConfiguration.General.tracks)
        fun getTracks(@Path("artist") artist: String,
                      @Query("page") page: Int,
                      @Query("type") type: String = "tracks",
                      @Query("count") count: Int = 20): Call<List<Track>>
    }
}

package br.com.hearthisat.service.config

import br.com.hearthisat.service.config.adapter.BooleanTypeAdapter
import br.com.hearthisat.service.config.adapter.DateTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class Service { companion object  }

val Service.Companion.gson : Gson
    get() = GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, BooleanTypeAdapter())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanTypeAdapter())
            .registerTypeAdapter(Date::class.java, DateTypeAdapter(dateFormats = listOf("dd/MM/yyyy", "yyyy-MM-dd HH:mm:ss", "HH:mm:ss")))
            .create()

fun Service.Companion.buildClient(headers: Headers) : OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val builder = chain.request()
                        .newBuilder()
                        .headers(headers)

                chain.proceed(builder.build())
            }
            .build()
}

fun Service.Companion.buildRest(headers: Headers) : Retrofit {
    return Retrofit.Builder()
            .baseUrl(APIConfiguration.baseURL())
            .client(buildClient(headers))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}

fun <T> Service.Companion.build(endpoint: EndpointProtocol<T>) : T {
    val rest = buildRest(endpoint.headers())
    return rest.create(endpoint.endpoints())
}

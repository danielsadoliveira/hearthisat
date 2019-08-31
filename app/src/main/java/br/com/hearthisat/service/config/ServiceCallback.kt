package br.com.hearthisat.service.config

import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ServiceCallback<T>(private val onSuccess: (T) -> Unit, private val onError: (String) -> Unit) : Callback<T>, AnkoLogger {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            requestError(e = Error.Factory.from(call.request(), response), raw = response.errorBody())
            onError(response.message())
        }
    }

    override fun onFailure(call: Call<T>, throwable: Throwable) {
        error { "THROWABLE MESSAGE: ${throwable.message}" }
        error { "THROWABLE STACKTRACE: ${throwable.stackTrace}"}
        error { "THROWABLE CAUSE: ${throwable.cause}"}

        onError("Was not possible to complete the request.")
    }

    private fun requestError(e: Error?, raw: ResponseBody?) {
        if (e != null) {
            error { "ERROR STATUS CODE: ${e.statusCode.statusCode}"}
            error { "ERROR STATUS DESCRIPTION: ${e.statusCode.description}"}
            error { "ERROR BODY: ${e.errorBody}"}
        }

        if (raw != null) {
            error { "RAW: ${raw.string()}"}
        }
    }
}
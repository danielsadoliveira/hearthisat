package br.com.hearthisat.service.config

import br.com.hearthisat.extensions.caseNotNull
import br.com.hearthisat.extensions.caseNull
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by danielsdo on 15/08/17.
 */

data class Error(val errorType: ErrorType, val statusCode: HttpStatusCode = HttpStatusCode(0), val responseBody: ResponseBody? = null, val errorBody: String? = null, val request: Request? = null) {
    object Factory {
        /**
         * Make an Error instance from a Throwable instance.
         *
         * @return Error instance from request and throwable instances.
         */
        fun from(request: Request, throwable: Throwable): Error {
            when(throwable) {
                is java.net.SocketTimeoutException ->  {
                    return Error(ErrorType.ClientTimeOut(), request = request)
                }
                is java.net.ConnectException,
                is java.net.UnknownHostException,
                is java.io.EOFException -> {
                    return Error(ErrorType.CannotReachServer(), request = request)
                }
                is JsonParseException,
                is JsonSyntaxException,
                is IllegalAccessException -> {
                    return Error(ErrorType.MarshalingFailed(throwable), request = request)
                }
                else -> {
                    var error: Error? = null
                    throwable.cause.caseNotNull { cause ->
                        if (cause != throwable) {
                            error = from(request, throwable = cause)
                        }
                    }.caseNull {
                        error = Error(ErrorType.Unknown(throwable = throwable), request = request)
                    }

                    return error!!
                }
            }
        }

        /**
         * Make an Error instance from a retrofit2.Response instance.
         *
         * @return Error instance from request and response instances.
         */
        fun <T> from(request: Request, resp: Response<T>): Error {
            val raw = resp.raw()
            val code = HttpStatusCode(raw?.code() ?: 0)
            val errorType = when (code.description) {
                HttpStatusCode.Status.InternalServerError -> ErrorType.InternalServerError()
                HttpStatusCode.Status.NetworkConnectTimeoutError -> ErrorType.ServerTimeOut()
                HttpStatusCode.Status.Unauthorized -> ErrorType.Unauthorized()
                HttpStatusCode.Status.BadRequest -> ErrorType.BadRequest(request, resp)
                else -> ErrorType.Unknown()
            }
            return Error(errorType, code, raw?.body(), resp.errorBody()?.string(), request = request)
        }
    }


    sealed class ErrorType {
        class Unknown(val throwable: Throwable? = null) : ErrorType()

        class CannotReachServer : ErrorType()

        class MarshalingFailed(val throwable: Throwable? = null) : ErrorType()

        class InternalServerError : ErrorType()

        open class TimeOut : ErrorType()
        class ServerTimeOut : TimeOut()
        class ClientTimeOut : TimeOut()

        class Unauthorized : ErrorType()

        class BadRequest<T>(val request: Request, val response: Response<T>) : ErrorType()
    }
}
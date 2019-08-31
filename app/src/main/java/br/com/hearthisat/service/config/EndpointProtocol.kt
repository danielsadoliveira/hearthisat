package br.com.hearthisat.service.config

import br.com.hearthisat.extensions.caseNotNull
import okhttp3.Headers

interface EndpointProtocol<T> {
    fun headers(): Headers
    fun endpoints(): Class<T>

    fun buildHeaders(headers: Map<String, String>) : Headers {
        val builder = Headers.Builder()

        headers.caseNotNull {
            for (key in it.keys) {
                builder.add(key, it[key]!!)
            }
        }

        return builder.build()
    }
}

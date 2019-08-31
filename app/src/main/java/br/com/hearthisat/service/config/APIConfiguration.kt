package br.com.hearthisat.service.config

import br.com.hearthisat.app.App

object APIConfiguration {
    fun baseURL() : String {
        return when (App.instance.environment) {
            APIEnvironment.production -> "https://api-v2.hearthis.at"
            APIEnvironment.homologation -> ""
            APIEnvironment.dev -> ""
        }
    }

    object HttpHeaders {
        object Headers {
            const val contentType = "ContentType-Type"
            const val authorization = "Authorization"
        }

        object ContentType {
            const val json = "application/json"
        }
    }

    object User {
        const val authenticate = "api/v2/authenticate"
        const val register = "/api/register"
        const val account = "/api/account"
        const val reset_password = "/api/account/reset-password/init"
        const val change_password = "/api/account/change-password"
        const val delete = "/api/api/delete-account"
    }

    object General {
        const val feed = "/feed"
        const val artist = "/{artist}"
        const val tracks = "/{artist}" //?type=tracks
    }
}
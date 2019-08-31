package br.com.hearthisat.service

import br.com.hearthisat.model.Artist
import br.com.hearthisat.model.Track
import br.com.hearthisat.service.GeneralService.Companion
import br.com.hearthisat.service.config.APIConfiguration
import br.com.hearthisat.service.config.Service
import br.com.hearthisat.service.config.ServiceCallback
import br.com.hearthisat.service.config.build
import br.com.hearthisat.service.endpoint.GeneralEndpoint

class GeneralService { companion object }

val Companion.routes: APIConfiguration.General
    get() = APIConfiguration.General

fun Companion.getFeed(page: Int, onSuccess:(List<Track>) -> Unit, onError:(String) -> Unit) {
    Service.build(GeneralEndpoint(routes.feed))
        .getFeed(page = page)
        .enqueue(ServiceCallback<List<Track>>(onSuccess = onSuccess, onError = onError))
}

fun Companion.getArtist(permalink: String, onSuccess:(Artist) -> Unit, onError:(String) -> Unit) {
    Service.build(GeneralEndpoint(routes.artist))
        .getArtist(artist = permalink)
        .enqueue(ServiceCallback<Artist>(onSuccess = onSuccess, onError = onError))
}

fun Companion.getTracks(permalink: String, page: Int, onSuccess:(List<Track>) -> Unit, onError:(String) -> Unit) {
    Service.build(GeneralEndpoint(routes.tracks))
        .getTracks(artist = permalink, page = page)
        .enqueue(ServiceCallback<List<Track>>(onSuccess = onSuccess, onError = onError))
}
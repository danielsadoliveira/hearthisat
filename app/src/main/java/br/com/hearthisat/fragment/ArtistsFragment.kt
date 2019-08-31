package br.com.hearthisat.fragment

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.hearthisat.R
import br.com.hearthisat.adapter.ArtistAdapter
import br.com.hearthisat.dialog.ErrorDialog
import br.com.hearthisat.enums.ContentOption
import br.com.hearthisat.extensions.compile
import br.com.hearthisat.extensions.toJson
import br.com.hearthisat.model.Artist
import br.com.hearthisat.model.Track
import br.com.hearthisat.service.GeneralService
import br.com.hearthisat.service.getArtist
import br.com.hearthisat.service.getFeed
import br.com.hearthisat.utils.general.NotificationEvent
import br.com.hearthisat.utils.layout.BaseFragment
import br.com.hearthisat.utils.listeners.EndlessScrollListener
import kotlinx.android.synthetic.main.fragment_artists.*


class ArtistsFragment : BaseFragment() {
    private lateinit var adapter: ArtistAdapter
    private lateinit var layoutManager : LinearLayoutManager

    private var page : Int = 1

    //region - LIFE CYCLE METHODS
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_artists, container, false)
    }
    //endregion

    //region - SETUP METHODS
    override fun setup() {
        setupRecycler()
        requestAll(page = 1)
    }

    private fun setupRecycler() {
        adapter = ArtistAdapter { artist -> request(artist = artist) }
        layoutManager = LinearLayoutManager(activity!!)

        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.addOnScrollListener(object : EndlessScrollListener(layoutManager = layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                requestAll(page = page)
            }
        })
    }
    //endregion

    //region - HELPER METHODS
    private fun retriveArtists(tracks : List<Track>) : List<Artist> {
        val artists : MutableList<Artist> = mutableListOf()
        tracks.forEach { item ->
            if (item.artist != null) {
                artists.add(item.artist!!)
            }
        }

        return artists.filter { artist -> !adapter.artists.contains(artist)  }
    }
    //endregion

    //region - REQUEST METHODS
    private fun requestAll(page: Int) {
        if (page <= 1) loading_progress.visibility = View.VISIBLE
        else loading_more_progress.visibility = View.VISIBLE

        this.page = page

        GeneralService.getFeed(
            page = page,
            onSuccess = { tracks ->
                if (page <= 1) loading_progress.visibility = View.GONE
                else loading_more_progress.visibility = View.GONE

                adapter.add(list = retriveArtists(tracks = tracks), clear = page == 1)
            },
            onError = { message ->
                if (page <= 1) loading_progress.visibility = View.GONE
                else loading_more_progress.visibility = View.GONE

                ErrorDialog(context = activity!!, title = "Someting went wrong!", message = message).show()
            }
        )
    }

    private fun request(artist: Artist) {
        startLoading()

        GeneralService.getArtist(
            permalink = artist.permalink!!,
            onSuccess = { details ->
                stopLoading()
                NotificationEvent.post(identifier = "content", content = ContentOption.ARTIST_DETAILS.compile(mapOf("artist" to details.toJson())))
            },
            onError = { message ->
                stopLoading()
                ErrorDialog(context = activity!!, title = "Someting went wrong!", message = message).show()
            }
        )
    }
    //endregion
}
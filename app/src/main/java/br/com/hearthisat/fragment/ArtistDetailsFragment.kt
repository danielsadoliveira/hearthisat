package br.com.hearthisat.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.hearthisat.R
import br.com.hearthisat.adapter.ArtistAdapter
import br.com.hearthisat.adapter.TrackAdapter
import br.com.hearthisat.dialog.ErrorDialog
import br.com.hearthisat.enums.ContentOption
import br.com.hearthisat.extensions.*
import br.com.hearthisat.model.Artist
import br.com.hearthisat.service.GeneralService
import br.com.hearthisat.service.getTracks
import br.com.hearthisat.utils.general.NotificationEvent
import br.com.hearthisat.utils.layout.BaseFragment
import br.com.hearthisat.utils.listeners.EndlessScrollListener
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.fragment_artist_details.*
import org.jetbrains.anko.support.v4.toast

class ArtistDetailsFragment : BaseFragment() {
    private lateinit var adapter: TrackAdapter
    private lateinit var layoutManager : LinearLayoutManager

    private var page : Int = 1
    private lateinit var artist: Artist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_artist_details, container, false)
    }
    //endregion

    //region ## MENU METHODS ##
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                NotificationEvent.post(identifier = "content", content = ContentOption.ARTISTS.compile(mapOf("back" to true)))
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region - SETUP METHODS
    override fun setup() {
        artist = restoreObject(key = "artist", type = Artist::class.java)!!

        setupToolbar()
        setupData()
        setupRecycler()
        requestTracks(page = 1)
    }

    private fun setupToolbar() {
        (activity!! as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            toolbar.title = null

            supportActionBar?.setHomeAsUpIndicator(R.drawable.vector_chevron_outline_left)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    private fun setupData() {
        photo_view.load(url = artist.avatar_url ?: "", options = buildOptions(multiTransformation = MultiTransformation(CircleCrop())))
        artist_name_text.text = artist.username
        followers_text.text = formattedStrings!![R.string.followers_number_text](artist.followers_count.toString())
        tracks_text.text = formattedStrings!![R.string.tracks_number_text](artist.track_count.toString())
    }

    private fun setupRecycler() {
        adapter = TrackAdapter { track ->
            NotificationEvent.post(identifier = "playlist", content = adapter.tracks.toJson())
            NotificationEvent.post(identifier = "play", content = track.toJson())
        }
        layoutManager = LinearLayoutManager(activity!!)

        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        recycler.addOnScrollListener(object : EndlessScrollListener(layoutManager = layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                requestTracks(page = page)
            }
        })
    }
    //endregion

    //region - REQUEST METHODS
    private fun requestTracks(page: Int) {
        if (page <= 1) loading_progress.visibility = View.VISIBLE
        else loading_more_progress.visibility = View.VISIBLE

        this.page = page

        GeneralService.getTracks(
            permalink = artist.permalink!!,
            page = page,
            onSuccess = { tracks ->
                if(initialized) {
                    if (page <= 1) loading_progress.visibility = View.GONE
                    else loading_more_progress.visibility = View.GONE

                    adapter.add(list = tracks, clear = page == 1)
                }
            },
            onError = { message ->
                if(initialized) {
                    if (page <= 1) loading_progress.visibility = View.GONE
                    else loading_more_progress.visibility = View.GONE

                    ErrorDialog(context = activity!!, title = "Someting went wrong!", message = message).show()
                }
            }
        )
    }
    //endregion
}
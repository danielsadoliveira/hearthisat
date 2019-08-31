package br.com.hearthisat.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.hearthisat.R
import br.com.hearthisat.app.DataManager
import br.com.hearthisat.enums.ContentOption
import br.com.hearthisat.extensions.*
import br.com.hearthisat.model.Track
import br.com.hearthisat.utils.general.NotificationEvent
import br.com.hearthisat.utils.layout.BaseActivity
import br.com.hearthisat.utils.sensors.NetworkStateReceiver
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity(), NetworkStateReceiver.NetworkStateReceiverListener  {
    private lateinit var contentObserver: Disposable
    private lateinit var playObserver: Disposable
    private lateinit var playlistObserver: Disposable

    //region ## LIFE CYCLE METHODS ##
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataManager.networkStateReceiver?.addListener(this)

        setup()
    }

    override fun onDestroy() {
        super.onDestroy()
        DataManager.networkStateReceiver?.removeListener(this)
    }

    override fun onStart() {
        super.onStart()
        startObservables()
    }

    override fun onStop() {
        super.onStop()
        stopObservables()
    }

    //endregion

    //region ## SETUP METHODS ##
    private fun setup() {
        playing_now_frame.setup()
        changeFragment(fragment = ContentOption.ARTISTS.fragment)
    }

    //endregion

    //region ## HELPER METHODS ##
    fun changeFragment(fragment: Fragment, animation: FragmentAnimationType? = null) {
        commitFragment(
            layout = R.id.content_fragment,
            fragment = fragment,
            animationType = animation,
            commitType = FragmentCommitType.commitAllowingStateLoss)
    }


    //endregion

    //region ## Observables ##
    private fun startObservables() {
        contentObserver = NotificationEvent.listen(identifier = "content", contentType = String::class.java).subscribe { pair ->
            ContentOption::class.decompile(json = (pair.second as String?))?.let { content ->
                val option: ContentOption = content.first
                val extras: MutableMap<String, Any>? = content.second
                val back: Boolean = if (extras != null && extras.containsKey("back")) {
                    val item = (extras["back"] as Boolean?) ?: false
                    extras.remove("back")
                    item
                } else {
                    false
                }

                changeFragment(
                    fragment = option.fragment.newInstance(extras = extras),
                    animation = back then FragmentAnimationType.SLIDE_LEFT_TO_RIGHT ?: FragmentAnimationType.SLIDE_RIGHT_TO_LEFT)
            }
        }

        playObserver = NotificationEvent.listen(identifier = "play", contentType = String::class.java).subscribe { pair ->
            val track = (pair.second as String).objectFromJson(Track::class.java)
            playing_now_frame.play(track = track, full = true)
        }

        playlistObserver = NotificationEvent.listen(identifier = "playlist", contentType = String::class.java).subscribe { pair ->
            val tracks = (pair.second as String).listFromJson<Track>(object: TypeToken<List<Track>>(){}.type)!!
            playing_now_frame.playlist(list = tracks)
        }
    }

    private fun stopObservables() {
        contentObserver.dispose()
        playObserver.dispose()
        playlistObserver.dispose()
    }
    //endregion


    //region ## NetworkStateReceiverListener ##
    override fun networkAvailable() {
        //TODO: Ação quando conexão com internet voltar
    }

    override fun networkUnavailable() {
        //TODO: Ação quando conexão com internet for perdida
    }
    //endregion
}

package br.com.hearthisat.utils.layout.playingnow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import br.com.hearthisat.R
import br.com.hearthisat.app.App
import br.com.hearthisat.app.DataManager
import br.com.hearthisat.extensions.*
import br.com.hearthisat.model.Track
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.frame_playing_now.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onTouch

class PlayingNowFrame : FrameLayout {
    private var app = App.instance.getContext()
    lateinit var mainView : View

    private lateinit var player: MediaPlayer

    private var tracks : MutableList<Track> = mutableListOf()
    private var current : Track? = null

    lateinit var status : Status

    enum class Status {
        close,
        full,
        minimize
    }

    enum class Control {
        previous,
        next
    }

    //region - CONSTRUCTORS
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)
    //endregion

    //region - SETUP METHODS
    fun setup() {
        setupView()
        setupPlayer()
        setupButtons()
        setupBar()

        status = Status.close
        full_player.translationY = App.screenSize.y.toFloat()
    }

    private fun setupView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mainView = inflater.inflate(R.layout.frame_playing_now, parent as ViewGroup, false)
        mainView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(mainView)
    }

    private fun setupButtons() {
        close_button.onClick {
            change(status = Status.minimize)
        }

        minimized_layout.onClick {
            change(status = Status.full)
        }

        previous_button.onClick {
            change(control = Control.previous)
        }

        next_button.onClick {
            change(control = Control.next)
        }

        play_button.onClick {
            player.isPlaying then pause() ?: start()
        }

        play_minimized_button.onClick {
            player.isPlaying then pause() ?: start()
        }
    }

    private fun setupPlayer() {
        player = MediaPlayer()
        player.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

            setOnPreparedListener {
                preparing_progress.visibility = View.GONE
                preparing_minimized_progress.visibility  = View.GONE
                control_layout.visibility = View.VISIBLE
                play_minimized_button.visibility = View.VISIBLE

                track_seekbar.isClickable = true
                track_seekbar.progress = 0
                track_seekbar.max = 100

                track_minimized_seekbar.progress = 0
                track_minimized_seekbar.max = 100

                total_time_text.text = getTimer(duration.toLong())

                this@PlayingNowFrame.start()
            }

            setOnCompletionListener {
                play_button.image = app.drawables[R.drawable.vector_play]
                play_minimized_button.image = app.drawables[R.drawable.vector_play_outline]

                current_time_text.text = getTimer(duration.toLong())

                track_seekbar.progress = 100
                track_minimized_seekbar.progress = 100

                change(control = Control.next)
            }
        }
    }

    private fun setupBar() {
        track_seekbar.onTouch { _, _ ->
            if (player.isPlaying) {
                player.seekTo((player.duration / 100) * track_seekbar.progress)
            }
        }
    }
    //endregion

    //region - HELPER METHODS
    private fun start() {
        player.start()
        play_button.image = app.drawables[R.drawable.vector_pause]
        play_minimized_button.image = app.drawables[R.drawable.vector_pause_outline]

        updateTime()
    }

    private fun pause(){
        player.pause()
        play_button.image = app.drawables[R.drawable.vector_play]
        play_minimized_button.image = app.drawables[R.drawable.vector_play_outline]
    }

    private fun getTimer(millis : Long) : String {
        val hours = (millis / (1000 * 60 * 60))
        val minutes = (millis % (1000 * 60 * 60) / (1000 * 60))
        val seconds = (millis % (1000 * 60 * 60) % (1000 * 60) / 1000)

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun updateTime() {
        if (player.isPlaying) {
            current_time_text.text = getTimer(player.currentPosition.toLong())
            track_seekbar.progress = (player.currentPosition * 100) / player.duration
            track_minimized_seekbar.progress = (player.currentPosition * 100) / player.duration

            handler.postDelayed({
                kotlin.run {
                    updateTime()
                }
            },1000)
        }
    }

    private fun change(control: Control) {
        if (tracks.isNotEmpty()) {
            val currentIndex = tracks.indexOf(current)
            when (control) {
                Control.next -> {
                    if (currentIndex >= 0 && currentIndex < (tracks.size-1)){
                        play(tracks[currentIndex+1])
                    }
                }
                Control.previous -> {
                    if (currentIndex >= 1 && currentIndex < (tracks.size)){
                        play(tracks[currentIndex-1])
                    }
                }
            }

        }
    }

    private fun change(status: Status) {
        when (status) {
            Status.full -> {
                if (this.status != Status.full){
                    full_player.animate()
                        .translationY(0f)
                        .setDuration(300)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                this@PlayingNowFrame.status = status
                                minimized_layout.animate(alpha = 0f)
                            }
                        })
                }
            }

            Status.close -> {
                if (this.status != Status.close){
                    full_player.animate()
                        .translationY(App.screenSize.y.toFloat())
                        .setDuration(300)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                this@PlayingNowFrame.status = status
                                minimized_layout.animate(alpha = 0f, duration = 100)
                            }
                        })
                }
            }

            Status.minimize -> {
                if (this.status != Status.minimize){
                    full_player.animate()
                        .translationY(App.screenSize.y.toFloat())
                        .setDuration(300)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                this@PlayingNowFrame.status = status
                                minimized_layout.animate(alpha = 1f, duration = 100)
                            }
                        })
                }
            }
        }

    }
    //endregion

    //region - OTHER METHODS
    fun playlist(list: List<Track>) {
        tracks.apply {
            clear()
            addAll(list)
        }
    }

    fun play(track: Track, full: Boolean = false) {
        current = track

        full then change(status = Status.full)

        preparing_progress.visibility = View.VISIBLE
        preparing_minimized_progress.visibility  = View.VISIBLE
        control_layout.visibility = View.GONE
        play_minimized_button.visibility = View.GONE

        current_time_text.text = ""
        total_time_text.text = ""

        track_seekbar.isClickable = false
        track_seekbar.progress = 0
        track_minimized_seekbar.progress = 0

        current_track_image.load(track.artwork_url!!, options = buildOptions(multiTransformation = MultiTransformation(RoundedCorners(32))))
        artist_current_track_text.text = track.artist!!.username
        name_current_track_text.text = track.title

        current_track_image_minimized.load(track.artwork_url!!, options = buildOptions(multiTransformation = MultiTransformation(RoundedCorners(8))))
        artist_current_track_minimized_text.text = track.artist!!.username
        name_current_track_minimized_text.text = track.title

        player.stop()
        player.reset()
        player.setDataSource(DataManager.proxy.getProxyUrl(track.stream_url))
        player.prepareAsync()
    }
    //endregion
}
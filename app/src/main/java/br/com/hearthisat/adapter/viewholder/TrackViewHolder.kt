package br.com.hearthisat.adapter.viewholder

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.hearthisat.R
import br.com.hearthisat.extensions.buildOptions
import br.com.hearthisat.extensions.load
import br.com.hearthisat.extensions.then
import br.com.hearthisat.model.Artist
import br.com.hearthisat.model.Track
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.jetbrains.anko.find

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var photoView: ImageView = itemView.find(R.id.photo_view)
    var genreView: TextView = itemView.find(R.id.genre_view)
    var nameView: TextView = itemView.find(R.id.name_view)
    var durationView: TextView = itemView.find(R.id.duration_view)
    var lineView: View = itemView.find(R.id.line_view)

    //region - SETUP METHODS
    fun setup(track: Track, last: Boolean) {
        genreView.text = track.genre
        nameView.text = track.title
        durationView.text = getTimer(duration = (track.duration ?: "0").toLong())

        photoView.load(url = track.thumb ?: "", options = buildOptions(multiTransformation = MultiTransformation(RoundedCorners(16))))

        lineView.visibility = last then View.GONE ?: View.VISIBLE
    }
    //endregion

    //region - HELPER METHODS
    private fun getTimer(duration: Long) : String {
        val hours = duration / 3600
        val minutes = (duration % 3600) / 60
        val seconds = duration % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
    //endregion
}
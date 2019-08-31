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
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import org.jetbrains.anko.find

class ArtistViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var photoView: ImageView = itemView.find(R.id.photo_view)
    var nameView: TextView = itemView.find(R.id.name_view)
    var lineView: View = itemView.find(R.id.line_view)

    //region - SETUP METHODS
    fun setup(artist: Artist, last: Boolean) {
        nameView.text = artist.username
        photoView.load(url = artist.avatar_url ?: "", options = buildOptions(multiTransformation = MultiTransformation(CircleCrop())))

        lineView.visibility = last then View.GONE ?: View.VISIBLE
    }
    //endregion
}
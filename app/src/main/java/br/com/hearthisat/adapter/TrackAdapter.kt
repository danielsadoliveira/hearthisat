package br.com.hearthisat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.hearthisat.R
import br.com.hearthisat.adapter.viewholder.ArtistViewHolder
import br.com.hearthisat.adapter.viewholder.TrackViewHolder
import br.com.hearthisat.model.Artist
import br.com.hearthisat.model.Track
import org.jetbrains.anko.sdk27.coroutines.onClick

class TrackAdapter(private val action: (Track) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var tracks : MutableList<Track> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_track, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        require(holder is TrackViewHolder)
        holder.apply {
            setup(track = tracks[position], last = position == tracks.size-1)

            itemView.onClick {
                action.invoke(tracks[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    //region - HELPER METHODS
    fun add(list: List<Track>, clear: Boolean) {
        tracks.apply {
            if (clear) { clear() }
            addAll(list)
        }

        notifyDataSetChanged()
    }
    //endregion
}
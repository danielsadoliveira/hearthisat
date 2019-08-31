package br.com.hearthisat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.hearthisat.R
import br.com.hearthisat.adapter.viewholder.ArtistViewHolder
import br.com.hearthisat.model.Artist
import org.jetbrains.anko.sdk27.coroutines.onClick

class ArtistAdapter(private val action: (Artist) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var artists : MutableList<Artist> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArtistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_artists, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        require(holder is ArtistViewHolder)
        holder.apply {
            setup(artist = artists[position], last = position == artists.size-1)

            itemView.onClick {
                action.invoke(artists[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    //region - HELPER METHODS
    fun add(list: List<Artist>, clear: Boolean) {
        val currentSize = artists.size

        artists.apply {
            if (clear) { clear() }
            addAll(list)
        }

        notifyItemRangeInserted(currentSize, list.size)
    }
    //endregion
}
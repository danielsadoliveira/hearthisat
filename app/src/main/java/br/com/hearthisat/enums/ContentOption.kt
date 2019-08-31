package br.com.hearthisat.enums

import androidx.fragment.app.Fragment
import br.com.hearthisat.fragment.*

enum class ContentOption {
    ARTISTS {
        override val fragment: Fragment
            get() = ArtistsFragment()
    },
    ARTIST_DETAILS {
        override val fragment: Fragment
            get() = ArtistDetailsFragment()
    };


    abstract val fragment : Fragment
}
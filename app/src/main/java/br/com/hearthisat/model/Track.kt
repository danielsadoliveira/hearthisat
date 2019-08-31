package br.com.hearthisat.model

import com.google.gson.annotations.SerializedName

data class Track (
        @SerializedName("id")                   var id : String?,
        @SerializedName("release_date")         var release_date : String?,
        @SerializedName("user_id")              var user_id : String?,
        @SerializedName("duration")             var duration : String?,
        @SerializedName("permalink")            var permalink : String?,
        @SerializedName("description")          var description : String?,
        @SerializedName("genre")                var genre : String?,
        @SerializedName("title")                var title : String?,
        @SerializedName("thumb")                var thumb : String?,
        @SerializedName("artwork_url")          var artwork_url : String?,
        @SerializedName("artwork_url_retina")   var artwork_url_retina : String?,
        @SerializedName("background_url")       var background_url : String?,
        @SerializedName("waveform_data")        var waveform_data : String?,
        @SerializedName("waveform_url")         var waveform_url : String?,
        @SerializedName("stream_url")           var stream_url : String?,
        @SerializedName("preview_url")          var preview_url : String?,
        @SerializedName("playback_count")       var playback_count : String?,
        @SerializedName("download_count")       var download_count : String?,
        @SerializedName("favoritings_count")    var favoritings_count : String?,
        @SerializedName("user")                 var artist: Artist?
)






















package br.com.hearthisat.model

import com.google.gson.annotations.SerializedName

data class Artist (
        @SerializedName("id")                   var id : String?,
        @SerializedName("permalink")            var permalink : String?,
        @SerializedName("username")             var username : String?,
        @SerializedName("caption")              var caption : String?,
        @SerializedName("thumb_url")            var thumb_url : String?,
        @SerializedName("avatar_url")           var avatar_url : String?,
        @SerializedName("background_url")       var background_url : String?,
        @SerializedName("description")          var description : String?,
        @SerializedName("geo")                  var geo : String?,
        @SerializedName("track_count")          var track_count : Int?,
        @SerializedName("playlist_count")       var playlist_count : Int?,
        @SerializedName("likes_count")          var likes_count : Int?,
        @SerializedName("followers_count")      var followers_count : Int?,
        @SerializedName("following_count")      var following_count : Int?
)




















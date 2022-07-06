package cl.uandes.pichangapp.data.model

import com.google.gson.annotations.SerializedName


data class ObtainedRemoteMatch(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("home_team")
    var home_team: HomeTeam,
    @SerializedName("visitor_team")
    val visitor_team: Visitor,
    @SerializedName("location")
    val  location: Location,
    @SerializedName("instructions")
    val  instructions: String,
    @SerializedName("game_date")
    val  game_date: String,
    @SerializedName("results")
    val  results: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("updated_at")
    val  updated_at: String?
)

data class HomeTeam(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("email")
    var email: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val  updated_at: String,
    @SerializedName("name")
    val  name: String,
    @SerializedName("category")
    val  category: String,
    @SerializedName("authentication_token")
    val  authentication_token: String
)

data class Location(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("latitude")
    var latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("place_name")
    val  place_name: String,
    @SerializedName("created_at")
    val  created_at: String,
    @SerializedName("updated_at")
    val  updated_at: String
)

data class Visitor(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("email")
    var email: String,
    @SerializedName("created_at")
    val  created_at: String,
    @SerializedName("updated_at")
    val  updated_at: String,
    @SerializedName("name")
    val  name: String,
    @SerializedName("category")
    val  category: String,
    @SerializedName("authentication_token")
    val  authentication_token: String

)
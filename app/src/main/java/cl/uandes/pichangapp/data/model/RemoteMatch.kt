package cl.uandes.pichangapp.data.model

import com.google.gson.annotations.SerializedName


data class RemoteMatch(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("home_team_id")
    var home_team_id: Long,
    @SerializedName("visitor_team_id")
    val visitor_team_id: Long?,
    @SerializedName("location_id")
    val  location_id: Long,
    @SerializedName("instructions")
    val  instructions: String,
    @SerializedName("game_date")
    val  game_date: String,
    @SerializedName("results")
    val  results: String?
)

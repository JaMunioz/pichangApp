package cl.uandes.pichangapp.data.model

import com.google.gson.annotations.SerializedName


data class RemoteUser(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("category")
    val category: String?
)

//User(id: Long?, email: String,
//password: String, name: String?,
//category: String?)
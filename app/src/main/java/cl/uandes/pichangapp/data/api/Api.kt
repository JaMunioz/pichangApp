package cl.uandes.pichangapp.data.api

import cl.uandes.pichangapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("api/v1/users")
    suspend fun getUsers(): Response<HashMap<String, List<User>>>

    @GET("api/v1/users/{users_id}")
    suspend fun getUser(@Path("user_id") userId: String): Response<HashMap<String, User>>

    @POST("api/v1/signup")
    suspend fun addUserexternaly(@Body remoteUser: RemoteUser): Response<String>

    @POST("api/v1/login")
    suspend fun login(@Body remoteUser: RemoteUser): Response<Map<String, String>>

    @GET("api/v1/pichangas")
    suspend fun getMatches(
        @Header("x-user-email") userMail: String,
        @Header("x-user-token") userToken: String
    ): Response<HashMap<String, List<ObtainedRemoteMatch>>>

    @PATCH("api/v1/pichangas/{match_id}")
    suspend fun editMatch(
        @Body obtainedRemoteMatch: ObtainedRemoteMatch,
        @Path("match_id") match_id: String,
        @Header("x-user-email") userMail: String,
        @Header("x-user-token") userToken: String
    ): Response<ObtainedRemoteMatch>

    @GET("api/v1/pichangas/{match_id}")
    suspend fun getMatch(@Path("match_id") match_id: String): Response<HashMap<String, ObtainedRemoteMatch>>

    @DELETE("api/v1/pichangas/{match_id}")
    suspend fun delMatch(
        @Path("match_id") match_id: String,
        @Header("x-user-email") userMail: String,
        @Header("x-user-token") userToken: String
    ): Response<Boolean>

    @POST("api/v1/pichangas")
    suspend fun addMatchExternally(
        @Body match: RemoteMatch,
        @Header("x-user-email") userMail: String,
        @Header("x-user-token") userToken: String
    ): Response<Boolean>

}

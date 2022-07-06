package cl.uandes.pichangapp.data.api

import cl.uandes.pichangapp.data.model.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService : Api {
    private val URL = "https://pichangapp.sangut.cl"

    private val api = Retrofit.Builder()
        .baseUrl(URL) // indicates the base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    override suspend fun getUsers(): Response<HashMap<String, List<User>>> = api.getUsers()

    override suspend fun getUser(userId: String): Response<HashMap<String, User>> =
        api.getUser(userId)

    override suspend fun addUserexternaly(remoteUser: RemoteUser): Response<String> =
        api.addUserexternaly(remoteUser)

    override suspend fun login(remoteUser: RemoteUser): Response<Map<String, String>> =
        api.login(remoteUser)

    override suspend fun getMatches(userMail: String, userToken: String): Response<HashMap<String, List<ObtainedRemoteMatch>>> =
        api.getMatches(userMail, userToken)

    override suspend fun editMatch(obtainedRemoteMatch: ObtainedRemoteMatch,match_id: String, userMail: String, userToken: String): Response<ObtainedRemoteMatch> =
        api.editMatch(obtainedRemoteMatch, match_id, userMail, userToken)

    override suspend fun getMatch(match_id: String): Response<HashMap<String, ObtainedRemoteMatch>> =
        api.getMatch(match_id)

    override suspend fun addMatchExternally(match: RemoteMatch, userMail: String, userToken: String): Response<Boolean> =
        api.addMatchExternally(match, userMail, userToken)

    override suspend fun delMatch(match_id: String,userMail: String, userToken: String): Response<Boolean> =
        api.delMatch(match_id,userMail, userToken)

}

package cl.uandes.pichangapp.data.datasources

import cl.uandes.pichangapp.data.api.Api
import cl.uandes.pichangapp.data.api.ApiService
import cl.uandes.pichangapp.data.model.*
import cl.uandes.pichangapp.ui.view.mail
import cl.uandes.pichangapp.ui.view.token
import cl.uandes.pichangapp.ui.view.userId

class ApiDataSource(
    private var api: Api = ApiService(),
) : RemoteDataSource {

    override suspend fun getMatches(): List<ObtainedRemoteMatch> {
        api.getMatches(mail, token).also {
            if (it.isSuccessful == true) return it.body()?.get("pichangas") ?: emptyList()
        }

        return emptyList()
    }

    override suspend fun getMatch(matchId: String): ObtainedRemoteMatch? {
        api.getMatch(matchId).also {
            if (it.isSuccessful == true) return it.body()?.getValue("pichanga")
        }
        return null
    }

    override suspend fun editMatch(obtainedRemoteMatch: ObtainedRemoteMatch, matchId: String): ObtainedRemoteMatch? {
        api.editMatch(obtainedRemoteMatch, matchId , mail, token).also {
            return it.body()!!
        }
    }

    override suspend fun addMatchexternaly(match: RemoteMatch): Boolean {
        api.addMatchExternally(match, mail, token ).also {
            return it.body() == true
        }
    }

    override suspend fun delMatch(matchId: String): Boolean {
        api.delMatch(matchId, mail, token ).also {
            return it.isSuccessful
        }
    }


    override suspend fun getUsers(): List<User> {
        api.getUsers().also {
            if (it.isSuccessful == true) return it.body()?.get("users") ?: emptyList()
        }

        return emptyList()
    }

    override suspend fun getUser(userId: String): User? {
        api.getUser(userId).also {
            if (it.isSuccessful == true) return it.body()?.getValue("user")
        }

        return null
    }

    override suspend fun addUserexternaly(remoteUser: RemoteUser): Boolean {
        api.addUserexternaly(remoteUser).also {
            return it.isSuccessful
        }
    }

    override suspend fun login(remoteUser: RemoteUser): Boolean {
        api.login(remoteUser).also {
            userId = it.body()?.get("user_id")?.toLong() ?: 0L
            token = it.body()?.get("token").toString()
            mail = remoteUser.email
            return it.isSuccessful
        }
    }
}

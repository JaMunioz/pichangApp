package cl.uandes.pichangapp.data.datasources

import cl.uandes.pichangapp.data.model.*

interface RemoteDataSource {

    suspend fun getMatches(): List<ObtainedRemoteMatch>
    suspend fun getMatch(match_id: String): ObtainedRemoteMatch?
    suspend fun editMatch(obtainedRemoteMatch: ObtainedRemoteMatch,match_id: String): ObtainedRemoteMatch?
    suspend fun addMatchexternaly(match: RemoteMatch): Boolean
    suspend fun delMatch(match_id: String): Boolean
    suspend fun getUsers(): List<User>
    suspend fun getUser(user_id: String): User?
    suspend fun addUserexternaly(remoteUser: RemoteUser): Boolean
    suspend fun login(remoteUser: RemoteUser): Boolean
}
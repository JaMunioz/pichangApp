package cl.uandes.pichangapp.data.repository

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.datasources.PichangappLocalDataSource
import cl.uandes.pichangapp.data.datasources.RemoteDataSource
import cl.uandes.pichangapp.data.model.*
import cl.uandes.pichangapp.ui.view.mail
import cl.uandes.pichangapp.ui.view.token

class PichangappRepository(
    private val localDataSource: PichangappLocalDataSource,
    private val remoteDataSource: RemoteDataSource?,
) : UserRepository, MatchRepository {

    override suspend fun createUser(user: User) {
        localDataSource.createUser(user)
    }

    override suspend fun updateUser(pos: Int, user: User) {
        localDataSource.updateUser(pos, user)
    }

    override suspend fun deleteUser(user: User) {
        localDataSource.deleteUser(user)
    }

    override fun getAllUsers(): LiveData<List<User>> {
        return localDataSource.getAllUsers()
    }

    override suspend fun createMatch(match: Match) {
        localDataSource.createMatch(match)
    }

    override suspend fun updateMatch(pos: Int, match: Match) {
        localDataSource.updateMatch(pos, match)
    }

    override suspend fun deleteMatch(match: Match) {
        localDataSource.deleteMatch(match)
    }

    override suspend fun refreshMatches() {
        localDataSource.refreshMatches()
    }

    override fun getAllMatches(): LiveData<List<Match>> {
        return localDataSource.getAllMatches()
    }

    suspend fun addUserExternally(remoteUser: RemoteUser) =
        remoteDataSource?.addUserexternaly(remoteUser)

    suspend fun login(remoteUser: RemoteUser) = remoteDataSource?.login(remoteUser)

    suspend fun getUser(userId: String): User? = remoteDataSource?.getUser(userId)

    suspend fun getUsers(): List<User> = remoteDataSource?.getUsers()!!

    suspend fun addMatchExternally(match: RemoteMatch) = remoteDataSource?.addMatchexternaly(match)

    suspend fun delMatch(matchId: String): Boolean? = remoteDataSource?.delMatch(matchId)

    suspend fun getMatch(matchId: String): ObtainedRemoteMatch? = remoteDataSource?.getMatch(matchId)

    suspend fun getMatches(): List<ObtainedRemoteMatch> = remoteDataSource?.getMatches()!!

    suspend fun editMatch(
        obtainedRemoteMatch: ObtainedRemoteMatch,
        match_id: String
    ): ObtainedRemoteMatch = remoteDataSource?.editMatch(obtainedRemoteMatch, match_id)!!
}

package cl.uandes.pichangapp.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cl.uandes.pichangapp.data.model.Match
import cl.uandes.pichangapp.data.fakedata.Matches
import cl.uandes.pichangapp.data.model.User
import cl.uandes.pichangapp.data.fakedata.Users

class PichangappLocalDataSource : UserDataSource, MatchDataSource {
    private val users = Users.profileUser()

    override suspend fun createUser(user: User) {
        users.add(user)
    }

    override suspend fun updateUser(pos: Int, user: User) {
        users[pos] = user
    }

    override suspend fun deleteUser(user: User) {
        users.remove(user)
    }

    override fun getAllUsers(): LiveData<List<User>> {
        return MutableLiveData(this.users)
    }

    private val matches = Matches.MyNextMatches()

    override suspend fun createMatch(match: Match) {
        matches.add(match)
    }

    override suspend fun updateMatch(pos: Int, match: Match) {
        matches[pos] = match
    }

    override suspend fun deleteMatch(match: Match) {
        matches.remove(match)
        for (item: Int in matches.indices) {
            matches[item].id = item.toLong()
        }
    }

    override suspend fun refreshMatches() {
        matches.removeAll(matches)
    }


    override fun getAllMatches(): LiveData<List<Match>> {
        return MutableLiveData(this.matches)
    }

}

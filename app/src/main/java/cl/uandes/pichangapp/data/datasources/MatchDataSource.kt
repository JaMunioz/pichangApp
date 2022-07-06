package cl.uandes.pichangapp.data.datasources

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.model.Match

interface MatchDataSource {
    suspend fun createMatch(match: Match)
    suspend fun updateMatch(pos: Int,match: Match)
    suspend fun deleteMatch(match: Match)
    suspend fun refreshMatches()
    fun getAllMatches(): LiveData<List<Match>>
}
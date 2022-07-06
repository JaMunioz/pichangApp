package cl.uandes.pichangapp.data.repository

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.model.Match

interface MatchRepository {
  suspend fun createMatch(match: Match)
  suspend fun updateMatch(pos: Int,match: Match)
  suspend fun deleteMatch(match: Match)
  suspend fun refreshMatches()
  fun getAllMatches(): LiveData<List<Match>>
}

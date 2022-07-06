package cl.uandes.pichangapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.uandes.pichangapp.data.model.Match

@Dao
interface MatchDao {
    @Insert
    suspend fun insertMatch(match: Match)
    @Update
    suspend fun updateMatch(match: Match)
    @Query("SELECT * FROM match")
    fun getAllMatches(): LiveData<List<Match>>
    @Delete
    suspend fun deleteMatch(match: Match)
}

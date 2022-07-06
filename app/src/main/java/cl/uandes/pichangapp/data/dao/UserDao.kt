package cl.uandes.pichangapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cl.uandes.pichangapp.data.model.User

@Dao
interface UserDao {
  @Insert
  suspend fun insertUser(user: User)
  @Update
  suspend fun updateUser(user: User)
  @Query("SELECT * FROM user")
  fun getAllUsers(): LiveData<List<User>>
  @Delete
  suspend fun deleteUser(user: User)
}

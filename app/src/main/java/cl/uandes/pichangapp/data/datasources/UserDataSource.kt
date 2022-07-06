package cl.uandes.pichangapp.data.datasources

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.model.User

interface UserDataSource {
    suspend fun createUser(user: User)
    suspend fun updateUser(pos: Int, user: User)
    suspend fun deleteUser(user: User)
    fun getAllUsers(): LiveData<List<User>>
}
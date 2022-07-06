package cl.uandes.pichangapp.data.repository

import androidx.lifecycle.LiveData
import cl.uandes.pichangapp.data.model.User

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun updateUser(pos: Int,user: User)
    suspend fun deleteUser(user: User)
    fun getAllUsers(): LiveData<List<User>>
}

package cl.uandes.pichangapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//Creo que falta serialized aqui
//Hay que hacer la relacion muchos a uno
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name="email")
    val email: String,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="password")
    val password: String,
    @ColumnInfo(name="category")
    val category: String,
)
package cl.uandes.pichangapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//Creo que falta serialized aqui
//Hay que hacer la relacion muchos a uno
//Hay que hacer lod e location
@Entity(tableName = "match", foreignKeys = [ForeignKey(entity = User::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("user_id"),
    onDelete = ForeignKey.CASCADE)]
)
data class Match(
    @PrimaryKey(autoGenerate = true)
    var id: Long,//should be Long
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @ColumnInfo(name="teamOne")
    val teamOne: String,
    @ColumnInfo(name="teamTwo")
    val teamTwo: String,
    @ColumnInfo(name="date")
    val date: String,
    @ColumnInfo(name="hour")
    val hour: String,
    @ColumnInfo(name="place")
    val place: String,
    @ColumnInfo(name="category")
    val category: String,
    @ColumnInfo(name="rules")
    val rules: String?,
    @ColumnInfo(name="teamOnePoints")
    val teamOnePoints: Int,
    @ColumnInfo(name="teamTwoPoints")
    val teamTwoPoints: Int,
    @ColumnInfo(name="time")
    val time: Long,
    @ColumnInfo(name="apiId")
    val apiId: Long
)
package cl.uandes.pichangapp.data.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cl.uandes.pichangapp.data.dao.UserDao
import cl.uandes.pichangapp.data.model.User
import cl.uandes.pichangapp.data.dao.MatchDao
import cl.uandes.pichangapp.data.model.Match

@Database(
  entities = [Match::class, User::class],
  version = 2
)//Fala hacer el migration cosa para las bases de datos
abstract class LocalRoomDatabase: RoomDatabase() {

  abstract fun userDao(): UserDao
  companion object {
    private const val DATABASE_NAME = "Pichangapp.db"
    private var INSTANCE: LocalRoomDatabase? = null

    private fun create(context: Context): LocalRoomDatabase =
      Room.databaseBuilder(
        context,
        LocalRoomDatabase::class.java,
        DATABASE_NAME
      ).addMigrations(MIGRATION_1_2)
        .build()

    fun getInstance(context: Context): LocalRoomDatabase =
      (INSTANCE?: create(context).also { INSTANCE = it })

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
      override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `user` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `teamOne` TEXT NOT NULL, `teamTwo` TEXT NOT NULL, `date` TEXT NOT NULL, `hour` TEXT NOT NULL, `place` TEXT NOT NULL, `category` TEXT NOT NULL, `rules` TEXT, `teamOnePoints` INTEGER NOT NULL, `teamTwoPoints` INTEGER NOT NULL, `time` INTEGER NOT NULL)")
      }
    }
  }

  override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
    TODO("Not yet implemented")
  }

  override fun createInvalidationTracker(): InvalidationTracker {
    TODO("Not yet implemented")
  }

  override fun clearAllTables() {
    TODO("Not yet implemented")
  }
  abstract fun MatchDao(): MatchDao


}

package com.kk.paging3migrate.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kk.paging3migrate.entity.DataX
import com.kk.paging3migrate.entity.RemoteKeys

/**
 * @author kuky.
 * @description
 */

@Database(
    entities = [DataX::class, RemoteKeys::class],
    version = 1, exportSchema = false
)
abstract class MigrateDatabase : RoomDatabase() {
    abstract fun remoteQueryDao(): QueryDao

    companion object {
        @Volatile
        private var db: MigrateDatabase? = null

        fun getInstance(context: Context): MigrateDatabase =
            db ?: synchronized(this) { db ?: buildDatabase(context) }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, MigrateDatabase::class.java, "article.db"
        ).build()
    }
}
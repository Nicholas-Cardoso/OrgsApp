package br.com.linux.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.linux.orgs.database.converter.Converters
import br.com.linux.orgs.database.dao.ProductDAO
import br.com.linux.orgs.model.Products

@Database(entities = [Products::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "orgs.db")
                .allowMainThreadQueries()
                .build()
        }
    }
}
package br.com.linux.orgs.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.linux.orgs.database.converter.Converters
import br.com.linux.orgs.database.dao.ProductDAO
import br.com.linux.orgs.database.dao.UserDao
import br.com.linux.orgs.model.Products
import br.com.linux.orgs.model.User

@Database(
    version = 3,
    entities = [
        Products::class,
        User::class
    ],
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
    abstract fun userDao(): UserDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "orgs.db")
                .allowMainThreadQueries()
                .build()
        }
    }
}
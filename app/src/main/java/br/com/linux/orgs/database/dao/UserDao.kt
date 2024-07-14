package br.com.linux.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.linux.orgs.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id: Long): Flow<User>

    @Query("SELECT * FROM User WHERE user = :user AND password = :pass")
    suspend fun authentication(user: String, pass: String): User?

    @Query("SELECT * FROM User WHERE user = :user")
    fun findUserByName(user: String): User?

    @Insert
    suspend fun createUser(vararg user: User)
}

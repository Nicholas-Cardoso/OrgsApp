package br.com.linux.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.linux.orgs.database.AppDatabase
import br.com.linux.orgs.extensions.goingTo
import br.com.linux.orgs.model.User
import br.com.linux.orgs.preferences.dataStore
import br.com.linux.orgs.preferences.loginUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UserBaseActivity : AppCompatActivity() {
    private val database by lazy {
        AppDatabase.getInstance(this).userDao()
    }
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    protected val user: StateFlow<User?> = _user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            checkLoggedUser()
        }
    }

    private fun checkLoggedUser() {
        lifecycleScope.launch {
            dataStore.data.collect { pref ->
                pref[loginUser]?.also { userId ->
                    findUserLogged(userId.toLong())
                } ?: gonnaLogin()
            }
        }
    }

    private suspend fun findUserLogged(userId: Long): User? {
        return database
            .getUserById(userId)
            .firstOrNull().also {
                _user.value = it
            }
    }

    protected fun logout() {
        lifecycleScope.launch {
            dataStore.edit { pref ->
                pref.remove(loginUser)
            }
        }
    }

    private fun gonnaLogin() {
        goingTo(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }
}
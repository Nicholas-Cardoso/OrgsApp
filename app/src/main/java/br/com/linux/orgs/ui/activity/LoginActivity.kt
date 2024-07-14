package br.com.linux.orgs.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.linux.orgs.database.AppDatabase
import br.com.linux.orgs.databinding.ActivityLoginBinding
import br.com.linux.orgs.extensions.goingTo
import br.com.linux.orgs.extensions.toHash
import br.com.linux.orgs.preferences.dataStore
import br.com.linux.orgs.preferences.loginUser
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val database by lazy {
        AppDatabase.getInstance(this).userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureButtonLogin()
        configureButtonRegister()
    }

    private fun configureButtonLogin() {
        with (binding) {
            loginButton.setOnClickListener {
                if (checkLoginFields()) {
                    lifecycleScope.launch {
                        val user = user.text.toString()
                        val pass = password.text.toString().toHash()

                        database.authentication(user, pass)?.let { auth ->
                            dataStore.edit { pref ->
                                pref[loginUser] = auth.id.toString()
                            }

                            goingTo(ProductsListActivity::class.java)
                            finish()
                        } ?: Toast.makeText(
                            this@LoginActivity,
                            "Usuário ou senha inválidos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun checkLoginFields(): Boolean {
        var isValid = true
        with(binding) {
            listOf(user, password).forEach {
                if (it.text.trim().isEmpty()) {
                    it.error = "Esse campo é obrigatório."
                    isValid = false
                } else {
                    it.error = null
                }
            }
            return isValid
        }
    }

    private fun configureButtonRegister() {
        binding.registerButton.setOnClickListener {
            goingTo(RegisterActivity::class.java)
        }
    }
}
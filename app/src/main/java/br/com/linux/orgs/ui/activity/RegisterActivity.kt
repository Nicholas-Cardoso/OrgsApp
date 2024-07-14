package br.com.linux.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.linux.orgs.database.AppDatabase
import br.com.linux.orgs.databinding.ActivityRegisterBinding
import br.com.linux.orgs.extensions.showToast
import br.com.linux.orgs.extensions.toHash
import br.com.linux.orgs.model.User
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    private val database by lazy {
        AppDatabase.getInstance(this).userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Register"

        configureButtonRegister()
    }

    private fun configureButtonRegister() {
        binding.register.setOnClickListener {
            if (checkRegisterFields()) {
                lifecycleScope.launch {
                    try {
                        createUserAfterCheck()
                    } catch (ex: Exception) {
                        showToast("Ocorreu um erro. Tente novamente mais tarde!")
                    }
                }
            }
        }
    }

    private suspend fun createUserAfterCheck() {
        database.findUserByName(binding.user.text.toString())?.let {
            showToast("Usuário já cadastrado")
        } ?: run {
            database.createUser(createUser())
            finish()
        }
    }

    private fun checkRegisterFields(): Boolean {
        var isValid = true
        with(binding) {
            listOf(user, name, pass).forEach {
                if (it.text.isEmpty()) {
                    it.error = "Esse campo é obrigatório."
                    isValid = false
                } else {
                    it.error = null
                }
            }
            return isValid
        }
    }

    private fun createUser(): User {
        with(binding) {
            return User(
                user = user.text.toString(),
                name = name.text.toString(),
                password = pass.text.toString().toHash()
            )
        }
    }
}
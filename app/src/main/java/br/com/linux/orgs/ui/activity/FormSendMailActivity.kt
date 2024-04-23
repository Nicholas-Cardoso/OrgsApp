package br.com.linux.orgs.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import br.com.linux.orgs.Inject
import br.com.linux.orgs.data.remote.mail.SendMailService
import br.com.linux.orgs.databinding.ActivityFormSendMailBinding
import br.com.linux.orgs.model.BodyMail

class FormSendMailActivity(
    private val mailService: SendMailService = Inject.mailService
) : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormSendMailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureButton()
    }

    private fun configureButton() {
        onChangeFields()

        val sendBtn = binding.btnSend
        sendBtn.setOnClickListener {
            val body = createBodyMail()
            hideKeyboard()
            senderMail(body)
        }

        val homeBtn = binding.btnReturn
        homeBtn.setOnClickListener {
            finish()
        }
    }

    private fun onChangeFields() {
        val recipient = binding.mailRecipient
        val subject = binding.mailSubject
        val body = binding.mailBody

        recipient.addTextChangedListener {
            checkFieldsValidity()
        }
        subject.addTextChangedListener {
            checkFieldsValidity()
        }
        body.addTextChangedListener {
            checkFieldsValidity()
        }
    }

    private fun checkFieldsValidity() {
        val bodyMail = createBodyMail()

        val isValid =
            bodyMail.recipientMail.isNotEmpty() && bodyMail.subject.isNotEmpty() && bodyMail.body.isNotEmpty()
        binding.btnSend.isEnabled = isValid
        binding.btnSend.alpha = if (isValid) 1.0f else 0.7f
    }

    private fun createBodyMail(): BodyMail {
        val recipientField = binding.mailRecipient
        val recipient = recipientField.text.toString()
        val subjectField = binding.mailSubject
        val subject = subjectField.text.toString()
        val bodyField = binding.mailBody
        val body = bodyField.text.toString()

        return BodyMail(
            recipient,
            subject,
            body
        )
    }

    private val handler = Handler(Looper.getMainLooper())

    private fun senderMail(body: BodyMail) {
        val isExecuted = mailService.sendMailService(body)

        handler.postDelayed({
            Toast.makeText(
                this,
                "Enviando o e-mail...",
                Toast.LENGTH_SHORT
            ).show()
            when (isExecuted) {
                true -> makeText(
                    this,
                    "E-mail enviado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                else ->
                    makeText(
                        this,
                        "Ocorreu um erro. Tente novamente mais tarde!",
                        Toast.LENGTH_SHORT
                    ).show()
            }
            clearInputs()
        }, 250)
    }

    private fun clearInputs() {
        val listInputs = listOf(binding.mailRecipient, binding.mailSubject, binding.mailBody)
        listInputs.forEach { it.text = null }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
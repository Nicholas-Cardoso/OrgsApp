package br.com.linux.orgs.data.remote.mail

import android.util.Log
import br.com.linux.orgs.model.BodyMail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SendMailService {
    private val sendMail by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://webforum.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MailServiceApi::class.java)
    }

    fun sendMailService(body: BodyMail): Boolean {
        val call = sendMail.sendMail(body)
        call.enqueue(object : Callback<BodyMail> {
            override fun onResponse(call: Call<BodyMail>, response: Response<BodyMail>) {
                if (response.isSuccessful) {
                    Log.i("onResponse", "onResponse: E-mail enviado com sucesso.")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("onResponse", "Erro de resposta: ${errorBody ?: "Sem detalhes."}")
                }
            }

            override fun onFailure(call: Call<BodyMail>, t: Throwable) {
                Log.e("onFailure", "Erro na comunicação: ${t.message}")
            }
        })
        return call.isExecuted
    }
}
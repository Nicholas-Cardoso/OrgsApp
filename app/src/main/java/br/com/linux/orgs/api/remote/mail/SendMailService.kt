package br.com.linux.orgs.api.remote.mail

import android.util.Log
import br.com.linux.orgs.api.remote.MailServiceApi
import br.com.linux.orgs.model.BodyMail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SendMailService {
    private companion object {
        const val BASE_URL = ""
    }

    private val sendMail by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MailServiceApi::class.java)
    }

    fun sendMail(body: BodyMail, callback: (Boolean) -> Unit) {
        val call = sendMail.sendMail(body)
        call.enqueue(object : Callback<List<BodyMail>> {
            override fun onResponse(
                call: Call<List<BodyMail>>,
                response: Response<List<BodyMail>>
            ) {
                if (response.isSuccessful) {
                    Log.i("onResponse", "E-mail enviado com sucesso.")
                    callback(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("onResponse", "Erro de resposta: ${errorBody ?: "Sem detalhes."}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<List<BodyMail>>, error: Throwable) {
                Log.e("onFailure", "Erro na comunicação: ${error.message}")
                callback(false)
            }
        })
    }
}
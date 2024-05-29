package br.com.linux.orgs.data.remote.mail

import br.com.linux.orgs.model.BodyMail
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MailServiceApi {
    @POST("/send-mail")
    fun sendMail(@Body bodyMail: BodyMail): Call<List<BodyMail>>
}

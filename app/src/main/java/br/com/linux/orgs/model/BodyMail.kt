package br.com.linux.orgs.model

data class BodyMail(
    val recipientMail: String,
    val subject: String,
    val body: String,
)
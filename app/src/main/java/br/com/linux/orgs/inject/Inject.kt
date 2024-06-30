package br.com.linux.orgs.inject

import br.com.linux.orgs.api.remote.mail.SendMailService

object Inject {
    val mailService by lazy { SendMailService() }
}
package br.com.linux.orgs

import br.com.linux.orgs.data.remote.mail.SendMailService

object Inject {
    val mailService by lazy { SendMailService() }
}
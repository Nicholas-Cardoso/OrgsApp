package br.com.linux.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.linux.orgs.databinding.LogoutProfileBinding
import br.com.linux.orgs.ui.activity.UserBaseActivity

class DialogLogout(private val context: Context) : UserBaseActivity() {
    fun showDialog() {
        LogoutProfileBinding.inflate(LayoutInflater.from(context)).apply {
            val alertDialog = AlertDialog.Builder(context).setView(root).show()

            justKidding.setOnClickListener {
                alertDialog.dismiss()
            }
            logMeOut.setOnClickListener {
                logout()
            }
        }
    }
}
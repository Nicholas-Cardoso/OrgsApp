package br.com.linux.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.linux.orgs.databinding.FormImageBinding
import br.com.linux.orgs.extensions.tryLoadImage

class DialogImageForm(private val context: Context) {
    fun showDialog(
        urlDefault: String? = null,
        whenImageLoader: (image: String) -> Unit
    ) {
        FormImageBinding.inflate(LayoutInflater.from(context)).apply {
            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    val url = inputImageUrl.text.toString()
                    whenImageLoader(url)
                }
                .setNegativeButton("Cancelar") { _, _ ->

                }.show()

            urlDefault?.let {
                imageViewDefault.tryLoadImage(it)
                inputImageUrl.setText(it)
            }

            buttonLoad.setOnClickListener {
                val url = inputImageUrl.text.toString()
                imageViewDefault.tryLoadImage(url)
            }
        }
    }
}
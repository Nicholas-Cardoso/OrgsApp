package br.com.linux.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.linux.orgs.databinding.ActivityViewProductsBinding
import br.com.linux.orgs.extensions.formattedPrice
import br.com.linux.orgs.extensions.tryLoadImage
import br.com.linux.orgs.model.Products

class ViewProductsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityViewProductsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configureAllDataActivities()
    }

    private fun configureAllDataActivities() {
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")
        val url = intent.getStringExtra("url")

        insertAllDataInAcitvity(
            Products(
                name = name,
                description = description,
                price = price?.toBigDecimal(),
                url = url
            )
        )
    }

    private fun insertAllDataInAcitvity(products: Products) {
        binding.title.text = products.name
        binding.description.text = products.description
        val formattedPrice = formattedPrice(products.price!!)
        binding.textViewPrice.text = formattedPrice
        binding.centerImageView.tryLoadImage(products.url)
    }
}
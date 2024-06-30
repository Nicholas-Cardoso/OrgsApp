package br.com.linux.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.linux.orgs.R
import br.com.linux.orgs.database.AppDatabase
import br.com.linux.orgs.databinding.ActivityViewProductsBinding
import br.com.linux.orgs.extensions.formattedPrice
import br.com.linux.orgs.extensions.tryLoadImage
import br.com.linux.orgs.model.Products

class ViewProductsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityViewProductsBinding.inflate(layoutInflater)
    }
    private val database by lazy {
        AppDatabase.getInstance(this).productDao()
    }
    private var productId: Long? = null
    private var product: Products? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "View product"

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadProduct()
    }

    override fun onResume() {
        super.onResume()
        productId?.let {
            product = database.getById(it)
        }
        product?.let {
            insertAllDataInActivity(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.details_edit -> {
                Intent(this, FormProductActivity::class.java).apply {
                    putExtra("product", product)
                    startActivity(this)
                }
            }

            R.id.details_delete -> {
                product?.let {
                    database.delete(it)
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tryLoadProduct() {
        intent.getParcelableExtra<Products>("product")?.let {
            productId = it.id
        } ?: finish()
    }

    private fun insertAllDataInActivity(products: Products) {
        with(binding) {
            title.text = products.name
            description.text = products.description

            val formattedPrice = formattedPrice(products.price!!)
            textViewPrice.text = formattedPrice

            centerImageView.tryLoadImage(products.url)
        }
    }
}
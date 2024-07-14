package br.com.linux.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import br.com.linux.orgs.database.AppDatabase
import br.com.linux.orgs.databinding.ActivityFormProductBinding
import br.com.linux.orgs.extensions.tryLoadImage
import br.com.linux.orgs.model.Products
import br.com.linux.orgs.ui.dialog.DialogImageForm
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormProductActivity : UserBaseActivity() {
    private val binding by lazy {
        ActivityFormProductBinding.inflate(layoutInflater)
    }
    private val database by lazy {
        AppDatabase.getInstance(this).productDao()
    }
    private var urlDialog: String? = null
    private var productId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Create product"

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureButtonSave()

        intent.getParcelableExtra<Products>("product")?.let { product ->
            title = "Change product"
            this.productId = product.id
            with(binding) {
                name.setText(product.name)
                description.setText(product.description)
                price.setText(product.price.toString())
                imageView.tryLoadImage(product.url)
                urlDialog = product.url
            }
        }
    }

    private fun configureButtonSave() {
        onChangeFields()

        binding.imageView.setOnClickListener {
            DialogImageForm(this).showDialog(urlDialog) {
                urlDialog = it
                binding.imageView.tryLoadImage(urlDialog)
            }
        }

        val saveBtn = binding.btnSave
        saveBtn.setOnClickListener {
            lifecycleScope.launch {
                if (productId > 0) {
                    database.updateProduct(updateProduct())
                } else {
                    database.createProduct(createProduct())
                }
                finish()
            }
        }

        val homeBtn = binding.btnReturn
        homeBtn.setOnClickListener {
            finish()
        }
    }

    private fun onChangeFields() {
        val nameField = binding.name
        val descField = binding.description
        val priceField = binding.price

        val listData = listOf(nameField, descField, priceField)
        listData.forEach { it.addTextChangedListener { checkFieldsValidity() } }
    }

    private fun checkFieldsValidity() {
        val product = createProduct()
        val saveBtn = binding.btnSave

        val isValid =
            product.name!!.isNotEmpty() && product.description!!.isNotEmpty() && product.price!! > BigDecimal.ZERO
        saveBtn.isEnabled = isValid
        saveBtn.alpha = if (isValid) 1.0f else 0.7f
    }

    private fun createProduct(): Products {
        with(binding) {
            val name = name.text.toString()
            val desc = description.text.toString()
            val price = price.text.toString()

            val priceParse = if (price.isBlank()) {
                BigDecimal.ZERO
            } else {
                BigDecimal(price)
            }

            return Products(
                name = name,
                description = desc,
                price = priceParse,
                url = urlDialog,
                userId = user.value?.id.toString()
            )
        }
    }

    private fun updateProduct(): Products {
        with(binding) {
            val name = name.text.toString()
            val desc = description.text.toString()
            val price = price.text.toString()

            val priceParse = if (price.isBlank()) {
                BigDecimal.ZERO
            } else {
                BigDecimal(price)
            }

            return Products(
                id = productId,
                name = name,
                description = desc,
                price = priceParse,
                url = urlDialog,
                userId = user.value?.id.toString()
            )
        }
    }
}
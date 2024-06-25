package br.com.linux.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import br.com.linux.orgs.databinding.ActivityFormProductBinding
import br.com.linux.orgs.dto.ProductsDAO
import br.com.linux.orgs.extensions.tryLoadImage
import br.com.linux.orgs.model.Products
import br.com.linux.orgs.ui.dialog.DialogImageForm
import java.math.BigDecimal

class FormProductActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormProductBinding.inflate(layoutInflater)
    }
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureButtonSave()
    }

    private fun configureButtonSave() {
        val productsDao = ProductsDAO()
        onChangeFields()

        binding.imageView.setOnClickListener {
            DialogImageForm(this).showDialog(url) {
                url = it
                binding.imageView.tryLoadImage(url)
            }
        }

        val saveBtn = binding.btnSave
        saveBtn.setOnClickListener {
            productsDao.addProducts(createProduct())
            finish()
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
        nameField.addTextChangedListener {
            checkFieldsValidity()
        }
        descField.addTextChangedListener {
            checkFieldsValidity()
        }
        priceField.addTextChangedListener {
            checkFieldsValidity()
        }
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
        val nameField = binding.name
        val name = nameField.text.toString()
        val descField = binding.description
        val desc = descField.text.toString()
        val priceField = binding.price
        val price = priceField.text.toString()

        val priceParse = if (price.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(price)
        }

        return Products(
            name = name,
            description = desc,
            price = priceParse,
            url = url
        )
    }
}
package br.com.linux.orgs.ui.activity

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import br.com.linux.orgs.R
import br.com.linux.orgs.databinding.ActivityFormProductBinding
import br.com.linux.orgs.dto.ProductsDAO
import br.com.linux.orgs.model.Products
import java.math.BigDecimal

class FormProductActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormProductBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureButtonSave()
    }

    private fun configureButtonSave() {
        val productsDao = ProductsDAO()
        onChangeFields()

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
        nameField.addTextChangedListener {
            checkFieldsValidity()
        }
        descField.addTextChangedListener {
            checkFieldsValidity()
        }
    }

    private fun checkFieldsValidity() {
        val product = createProduct()
        val saveBtn = binding.btnSave

        val isValid = product.name.isNotEmpty() && product.description.isNotEmpty()
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
            name,
            desc,
            priceParse
        )
    }
}
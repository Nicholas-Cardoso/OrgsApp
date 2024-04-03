package br.com.linux.orgs.ui.activity

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import br.com.linux.orgs.R
import br.com.linux.orgs.dto.ProductsDAO
import br.com.linux.orgs.model.Products
import java.math.BigDecimal

class FormProductActivity : AppCompatActivity(R.layout.activity_form_product) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureButtonSave()
    }

    private fun configureButtonSave() {
        val productsDao = ProductsDAO()
        onChangeFields()

        val saveBtn = findViewById<Button>(R.id.btn_save)
        saveBtn.setOnClickListener {
            productsDao.addProducts(createProduct())
            finish()
        }

        val homeBtn = findViewById<Button>(R.id.btn_return)
        homeBtn.setOnClickListener {
            finish()
        }
    }

    private fun onChangeFields() {
        val nameField = findViewById<EditText>(R.id.name)
        val descField = findViewById<EditText>(R.id.description)
        nameField.addTextChangedListener {
            checkFieldsValidity()
        }
        descField.addTextChangedListener {
            checkFieldsValidity()
        }
    }

    private fun checkFieldsValidity() {
        val product = createProduct()
        val saveBtn = findViewById<Button>(R.id.btn_save)

        val isValid = product.name.isNotEmpty() && product.description.isNotEmpty()
        saveBtn.isEnabled = isValid
        saveBtn.alpha = if (isValid) 1.0f else 0.7f
    }

    private fun createProduct(): Products {
        val nameField = findViewById<EditText>(R.id.name)
        val name = nameField.text.toString()
        val descField = findViewById<EditText>(R.id.description)
        val desc = descField.text.toString()
        val priceField = findViewById<EditText>(R.id.price)
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
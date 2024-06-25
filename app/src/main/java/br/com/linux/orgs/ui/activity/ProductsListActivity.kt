package br.com.linux.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.linux.orgs.R
import br.com.linux.orgs.databinding.ActivityProductsBinding
import br.com.linux.orgs.dto.ProductsDAO
import br.com.linux.orgs.ui.recyclerview.adapter.ListProductsAdapter

class ProductsListActivity : AppCompatActivity() {
    private val dao = ProductsDAO()
    private val adapter = ListProductsAdapter(this, dao.listOfProducts())
    private val binding by lazy {
        ActivityProductsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureRecyclerView()
        setProductList()
        sendMailService()
    }

    override fun onResume() {
        super.onResume()
        adapter.update(dao.listOfProducts())
    }

    private fun configureRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        adapter.whenClickSomeItem = {
            if (!it.name.isNullOrBlank() && !it.description.isNullOrBlank() && it.price != null && !it.url.isNullOrBlank()) {
                val intent = Intent(this, ViewProductsActivity::class.java).apply {
                    putExtra("name", it.name)
                    putExtra("description", it.description)
                    putExtra("price", it.price.toString())
                    putExtra("url", it.url)
                }
                startActivity(intent)
            }
        }
    }

    private fun setProductList() {
        val floatingAdd = binding.floatingActionButton
        floatingAdd.setOnClickListener {
            val intent = Intent(this, FormProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendMailService() {
        val sendMail = binding.sendMail
        sendMail.setOnClickListener {
            val intent = Intent(this, FormSendMailActivity::class.java)
            startActivity(intent)
        }
    }
}
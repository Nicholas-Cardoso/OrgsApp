package br.com.linux.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.linux.orgs.R
import br.com.linux.orgs.databinding.ActivityProductsBinding
import br.com.linux.orgs.dto.ProductsDAO
import br.com.linux.orgs.ui.recyclerview.adapter.ListProductsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
    }

    override fun onResume() {
        super.onResume()
        adapter.update(dao.listOfProducts())
    }

    private fun configureRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
    }

    private fun setProductList() {
        val floatingAdd = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingAdd.setOnClickListener {
            val intent = Intent(this, FormProductActivity::class.java)
            startActivity(intent)
        }
    }
}
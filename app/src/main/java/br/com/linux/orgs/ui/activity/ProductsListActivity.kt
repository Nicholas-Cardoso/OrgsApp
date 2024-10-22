package br.com.linux.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import br.com.linux.orgs.R
import br.com.linux.orgs.database.AppDatabase
import br.com.linux.orgs.databinding.ActivityProductsBinding
import br.com.linux.orgs.model.Products
import br.com.linux.orgs.ui.dialog.DialogLogout
import br.com.linux.orgs.ui.recyclerview.adapter.ListProductsAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ProductsListActivity : UserBaseActivity() {
    private val adapter = ListProductsAdapter(this)
    private val binding by lazy {
        ActivityProductsBinding.inflate(layoutInflater)
    }
    private val databaseProduct by lazy {
        AppDatabase.getInstance(this).productDao()
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

        lifecycleScope.launch {
            user.filterNotNull().collect {
                title = "Products - ${it.name}"
                findAllProductsByUser()
            }
        }
    }

    private suspend fun findAllProductsByUser() {
        databaseProduct.getAllProductsByUser(user.value?.id.toString()).collect {
            adapter.update(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filters_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val product: List<Products>? = when (item.itemId) {
            R.id.menu_list_by_asc_name ->
                databaseProduct.findAllByNameAsc()

            R.id.menu_list_by_desc_name ->
                databaseProduct.findAllByNameDesc()

            R.id.menu_list_by_asc_description ->
                databaseProduct.findAllByDescriptionAsc()

            R.id.menu_list_by_desc_description ->
                databaseProduct.findAllByDescriptionDesc()

            R.id.menu_list_by_asc_price ->
                databaseProduct.findAllByPriceAsc()

            R.id.menu_list_by_desc_price ->
                databaseProduct.findAllByPriceDesc()

            else -> null
        }
        product?.let {
            adapter.update(it)
        }

        if (item.itemId == R.id.logout) {
            DialogLogout(this).showDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configureRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        adapter.whenClickSomeItem = {
            if (!it.name.isNullOrBlank() && !it.description.isNullOrBlank() && it.price != null) {
                Intent(this, ViewProductsActivity::class.java).apply {
                    putExtra("product", it)
                    startActivity(this)
                }
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
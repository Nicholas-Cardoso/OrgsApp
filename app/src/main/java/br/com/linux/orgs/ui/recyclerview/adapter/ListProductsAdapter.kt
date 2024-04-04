package br.com.linux.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.linux.orgs.R
import br.com.linux.orgs.databinding.ActivityProductsBinding
import br.com.linux.orgs.databinding.ProductItenBinding
import br.com.linux.orgs.model.Products

class ListProductsAdapter(
    private val context: Context,
    products: List<Products>
) : RecyclerView.Adapter<ListProductsAdapter.ViewHolder>() {
    private val products = products.toMutableList()

    class ViewHolder(private val binding: ProductItenBinding) : RecyclerView.ViewHolder(binding.root) {
        fun link(product: Products) {
            val name = binding.name
            name.text = product.name
            val description = binding.description
            description.text = product.description
            val price = binding.price
            price.text = product.price.toPlainString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProductItenBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.link(product)
    }

    override fun getItemCount(): Int = products.size

    fun update(products: List<Products>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }
}

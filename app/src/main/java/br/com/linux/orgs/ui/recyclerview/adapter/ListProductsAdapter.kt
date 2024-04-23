package br.com.linux.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.linux.orgs.databinding.ProductItemBinding
import br.com.linux.orgs.model.Products
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ListProductsAdapter(
    private val context: Context,
    products: List<Products>
) : RecyclerView.Adapter<ListProductsAdapter.ViewHolder>() {
    private val products = products.toMutableList()

    class ViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun link(product: Products) {
            val name = binding.name
            name.text = product.name
            val description = binding.description
            description.text = product.description
            val price = binding.price
            val formattedPrice = formattedPrice(product.price)
            price.text = formattedPrice
        }

        private fun formattedPrice(price: BigDecimal): String? {
            val currencyInstance = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            return currencyInstance.format(price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
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

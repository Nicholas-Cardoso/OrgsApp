package br.com.linux.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.linux.orgs.R
import br.com.linux.orgs.databinding.ProductItemBinding
import br.com.linux.orgs.extensions.formattedPrice
import br.com.linux.orgs.model.Products
import coil.load

class ListProductsAdapter(
    private val context: Context,
    products: List<Products>,
    var whenClickSomeItem: (product: Products) -> Unit = {}
) : RecyclerView.Adapter<ListProductsAdapter.ViewHolder>() {
    private val products = products.toMutableList()

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var product: Products

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    whenClickSomeItem(product)
                }
            }
        }

        fun link(product: Products) {
            this.product = product
            val name = binding.name
            name.text = product.name
            val description = binding.description
            description.text = product.description
            val price = binding.price
            val formattedPrice = formattedPrice(product.price!!)
            price.text = formattedPrice

            val visibility = if (product.url != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.imageView.visibility = visibility
            binding.imageView.load(product.url) {
                placeholder(R.drawable.loading)
            }
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

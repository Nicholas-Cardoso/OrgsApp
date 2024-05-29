package br.com.linux.orgs.dto

import br.com.linux.orgs.model.Products

class ProductsDAO {
    companion object {
        val products = mutableListOf<Products>()
    }

    fun addProducts(product: Products) {
        products.add(product)
    }

    fun listOfProducts(): List<Products> {
        return products.toList()
    }
}
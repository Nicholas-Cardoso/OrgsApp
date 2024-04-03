package br.com.linux.orgs.dto

import br.com.linux.orgs.model.Products

class ProductsDAO {

    fun addProducts(product: Products) {
        products.add(product)
    }

    fun listOfProducts(): List<Products> {
        return products.toList()
    }

    companion object {
        val products = mutableListOf<Products>()
    }
}
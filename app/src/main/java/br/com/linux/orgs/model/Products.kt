package br.com.linux.orgs.model

import java.math.BigDecimal

data class Products(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val isValid: Boolean = false
)

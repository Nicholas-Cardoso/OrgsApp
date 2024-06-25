package br.com.linux.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun formattedPrice(price: BigDecimal): String? {
    val currencyInstance = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
    return currencyInstance.format(price)
}
package br.com.linux.orgs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Products(
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val url: String? = null,
    val isValid: Boolean = false
) : Parcelable

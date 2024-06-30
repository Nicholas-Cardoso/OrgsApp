package br.com.linux.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Products(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String?,
    val description: String?,
    val price: BigDecimal?,
    val url: String? = null,
    val isValid: Boolean = false
) : Parcelable

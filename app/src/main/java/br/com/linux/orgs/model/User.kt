package br.com.linux.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val user: String?,
    val name: String?,
    val password: String?
) : Parcelable
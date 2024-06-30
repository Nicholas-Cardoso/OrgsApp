package br.com.linux.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.linux.orgs.model.Products

@Dao
interface ProductDAO {
    @Query("SELECT * FROM Products")
    fun getAll(): List<Products>

    @Query("SELECT * FROM Products WHERE id = :id")
    fun getById(id: Long): Products

    @Insert
    fun insertProduct(vararg products: Products)

    @Update
    fun updateProduct(product: Products)

    @Delete
    fun delete(products: Products)

    // Filters
    @Query("SELECT * FROM Products ORDER BY name ASC")
    fun findAllByNameAsc(): List<Products>

    @Query("SELECT * FROM Products ORDER BY name DESC")
    fun findAllByNameDesc(): List<Products>

    @Query("SELECT * FROM Products ORDER BY description ASC")
    fun findAllByDescriptionAsc(): List<Products>

    @Query("SELECT * FROM Products ORDER BY description DESC")
    fun findAllByDescriptionDesc(): List<Products>

    @Query("SELECT * FROM Products ORDER BY price ASC")
    fun findAllByPriceAsc(): List<Products>

    @Query("SELECT * FROM Products ORDER BY price DESC")
    fun findAllByPriceDesc(): List<Products>
}
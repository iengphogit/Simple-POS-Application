package com.reansen.simple_pos_application.room.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.reansen.simple_pos_application.room.model.room.entity.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(ProductEntity productEntity);

    @Update
    void updateProduct(ProductEntity productEntity);

    @Delete
    void deleteProduct(ProductEntity productEntity);

    @Query("SELECT * FROM ProductEntity")
    List<ProductEntity> getAllProducts();

    @Query("SELECT * FROM ProductEntity WHERE id = :productId")
    ProductEntity getProductById(long productId);
}
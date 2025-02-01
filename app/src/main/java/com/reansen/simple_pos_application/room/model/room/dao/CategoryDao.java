package com.reansen.simple_pos_application.room.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.reansen.simple_pos_application.room.model.room.entity.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CategoryEntity CategoryEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CategoryEntity... categories);

    @Update
    void update(CategoryEntity CategoryEntity);

    @Delete
    void delete(CategoryEntity CategoryEntity);

    @Query("SELECT * FROM categories")
    List<CategoryEntity> getAllCategories();

    // Optional methods for specific queries
    @Query("SELECT * FROM categories WHERE id = :id")
    CategoryEntity getCategoryEntityById(long id);

    @Query("SELECT * FROM categories WHERE name LIKE :name")
    List<CategoryEntity> getCategoriesByName(String name);

}

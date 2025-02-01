package com.reansen.simple_pos_application.room.model.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    // Optional fields (consider your specific needs)
    public String description;
}
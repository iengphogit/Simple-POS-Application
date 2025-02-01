package com.reansen.simple_pos_application.room.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.reansen.simple_pos_application.room.model.room.dao.CategoryDao;
import com.reansen.simple_pos_application.room.model.room.dao.ProductDao;
import com.reansen.simple_pos_application.room.model.room.entity.CategoryEntity;
import com.reansen.simple_pos_application.room.model.room.entity.ProductEntity;

@Database(entities = {ProductEntity.class, CategoryEntity.class}, version = 2)
public abstract class POSDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();

    private static volatile POSDatabase INSTANCE;

    public static POSDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (POSDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), POSDatabase.class, "pos_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
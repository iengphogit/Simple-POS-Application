package com.reansen.simple_pos_application.ui;

import android.content.Context;
import android.content.Intent;

import com.reansen.simple_pos_application.ui.activity.CategoriesActivity;
import com.reansen.simple_pos_application.ui.activity.CategoryActivity;
import com.reansen.simple_pos_application.ui.activity.ProductActivity;

public class Navigator {

    public static Intent getCategoriesActivity(Context context){
        return new Intent(context, CategoriesActivity.class);
    }

    public static Intent getCreateCategoryActivity(Context context){
        return new Intent(context, CategoryActivity.class);
    }

    public static Intent getProductActivity(Context context){
        return new Intent(context, ProductActivity.class);
    }
}

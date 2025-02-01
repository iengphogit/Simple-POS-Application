package com.reansen.simple_pos_application.room.model.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reansen.simple_pos_application.R;
import com.reansen.simple_pos_application.room.model.room.entity.CategoryEntity;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCategoryName;
    private TextView tvCategoryDesc;
    private Button btnSelection;

    //R.layout.category_item_view
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        tvCategoryDesc = itemView.findViewById(R.id.tvCategoryDesc);
        btnSelection = itemView.findViewById(R.id.btnSelection);
    }

    public Button getBtnSelect() {
        return btnSelection;
    }

    public void updateItem(CategoryEntity category) {
        if (category != null) {
            if (category.name != null) {
                tvCategoryName.setText(category.name);
            }
            if (category.description != null) {
                tvCategoryDesc.setText(category.description);
            }
        }
    }

}
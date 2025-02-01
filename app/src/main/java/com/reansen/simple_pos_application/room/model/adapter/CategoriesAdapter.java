package com.reansen.simple_pos_application.room.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reansen.simple_pos_application.R;
import com.reansen.simple_pos_application.room.model.room.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private List<CategoryEntity> categories = new ArrayList<>();

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view, parent, false);
        return new CategoryViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        //bind data
        CategoryEntity entity = categories.get(position);
        holder.updateItem(entity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Item clicked: " + entity.name, Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (categoryListener != null) {
                    categoryListener.onCategoryLongClick(entity);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    private CategoryListener categoryListener;
    public void setCategoryListener(CategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }

    public interface CategoryListener {
        void onCategoryLongClick(CategoryEntity categoryEntity);
    }
}

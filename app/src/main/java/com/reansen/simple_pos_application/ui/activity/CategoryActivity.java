package com.reansen.simple_pos_application.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.reansen.simple_pos_application.R;
import com.reansen.simple_pos_application.room.model.room.POSDatabase;
import com.reansen.simple_pos_application.room.model.room.dao.CategoryDao;
import com.reansen.simple_pos_application.room.model.room.entity.CategoryEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryActivity extends BaseActivity {

    public static String CategoryId = "categoryId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Main-thread => UI TextView, Button  -> TextView
        //IO-thread => DB -> save database
        //newSingleThreadExecutor => DB

        POSDatabase posDatabase = POSDatabase.getInstance(this);

        TextInputEditText edtCategoryName = findViewById(R.id.edtCategoryName);
        TextInputEditText edtCategoryDesc = findViewById(R.id.edtCategoryDesc);
        Button btnCreateNewCategory = findViewById(R.id.btnCreateNewCategory);

        //Todo save/insert
        btnCreateNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = edtCategoryName.getText().toString();
                String categoryDesc = edtCategoryDesc.getText().toString();


                if (TextUtils.isEmpty(categoryName)) {
                    Toast.makeText(CategoryActivity.this, "Please enter category name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(categoryDesc)) {
                    Toast.makeText(CategoryActivity.this, "Please enter category description", Toast.LENGTH_SHORT).show();
                } else {
                    //Todo create category
                    CategoryDao categoryDao = posDatabase.categoryDao();
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.name = categoryName;
                    categoryEntity.description = categoryDesc;
                    executorService.execute(() -> {
                        categoryDao.insert(categoryEntity);
                    });
                    Log.d("ieng-tag", "onClick: saved");
                    setResult(RESULT_OK);
                    Toast.makeText(CategoryActivity.this, "Category created", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        //Todo edit/update
        long categoryId = getIntent().getLongExtra(CategoryId, 0);
        if (categoryId != 0) {
            //Edit mode
            CategoryDao categoryDao = posDatabase.categoryDao();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    CategoryEntity entity = categoryDao.getCategoryEntityById(categoryId);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (entity != null) {
                                // Bind data
                                edtCategoryName.setText(entity.name);
                                edtCategoryDesc.setText(entity.description);

                                //Todo apply update listener, set new data
                                btnCreateNewCategory.setText("Update");
                                btnCreateNewCategory.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        entity.name = edtCategoryName.getText().toString();
                                        entity.description = edtCategoryDesc.getText().toString();
                                        executorService.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                categoryDao.update(entity);
                                            }
                                        });

                                        // Set result after updating entity (and potentially before finish())
                                        setResult(RESULT_OK);
                                        Toast.makeText(CategoryActivity.this, "Category updated", Toast.LENGTH_SHORT).show();
                                        finish(); // This can potentially move after setting result
                                    }
                                });
                            }
                        }
                    });
                }
            });

        }


    }

    @Override
    String mTitle() {
        return "New Category";
    }
}
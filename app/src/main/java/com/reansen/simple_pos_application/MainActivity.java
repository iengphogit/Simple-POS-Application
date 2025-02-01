package com.reansen.simple_pos_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.reansen.simple_pos_application.ui.Navigator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Todo bind view
        Button btnProducts = findViewById(R.id.btnProducts);
        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Products clicked!");
                Intent intent = Navigator.getProductActivity(MainActivity.this);
                startActivity(intent);
            }
        });

        Button btnCategories = findViewById(R.id.btnCategories);
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToastMessage("Categories clicked!");
                Intent intent = Navigator.getCategoriesActivity(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
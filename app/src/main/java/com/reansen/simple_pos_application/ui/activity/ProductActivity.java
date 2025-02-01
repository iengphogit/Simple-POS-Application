package com.reansen.simple_pos_application.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.reansen.simple_pos_application.R;
import com.reansen.simple_pos_application.room.model.room.POSDatabase;
import com.reansen.simple_pos_application.room.model.room.dao.ProductDao;
import com.reansen.simple_pos_application.room.model.room.entity.ProductEntity;
import com.reansen.simple_pos_application.ui.Navigator;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductActivity extends AppCompatActivity {

    private EditText editTextProductCode, editTextProductName,
            editTextProductPrice, editTextProductDescription, editTextCategory;

    private Button buttonSaveProduct;

    private ImageView actionScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        initAction();

    }

    private void initAction() {
        actionScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions options = new ScanOptions();
                options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
                options.setPrompt("Scan a barcode");
                options.setCameraId(0);  // Use a specific camera of the device
                options.setBeepEnabled(false);
                options.setBarcodeImageEnabled(true);
                options.setOrientationLocked(true);
                barcodeLauncher.launch(options);
            }
        });
        POSDatabase posDatabase = POSDatabase.getInstance(ProductActivity.this);
        ProductDao productDao = posDatabase.productDao();

        buttonSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo your code here.
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String cleanString = editTextProductPrice.getText().toString().replaceAll("[$,.]", "");

                        ProductEntity productEntity = new ProductEntity();
                        productEntity.productCode = editTextProductCode.getText().toString();
                        productEntity.name = editTextProductName.getText().toString();
                        productEntity.price = Double.parseDouble(cleanString);
                        productEntity.description = editTextProductDescription.getText().toString();
                        productDao.insertProduct(productEntity);

                        //Todo 1, ,2 = index = 0, index = 1
                        ProductEntity entity = productDao.getAllProducts().get(productDao.getAllProducts().size() - 1);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Ui thread
                                Toast.makeText(ProductActivity.this, "Save product: " + entity.name, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }
        });
    }

    private String current = "";

    private void initView() {
        editTextProductCode = findViewById(R.id.editTextProductCode);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        buttonSaveProduct = findViewById(R.id.buttonSaveProduct);
        actionScan = findViewById(R.id.actionScanBarcode);
        editTextCategory = findViewById(R.id.editTextCategory);

        Button btnSelectCat = findViewById(R.id.btnSelectCat);
        btnSelectCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Navigator.getCategoriesActivity(ProductActivity.this);
                intent.putExtra("mode", "selection");
                selectCategoryLauncher.launch(intent);
            }
        });
        //Todo listener

        editTextProductPrice.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(current)) {
                    editTextProductPrice.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted;
                    editTextProductPrice.setText(formatted);
                    editTextProductPrice.setSelection(formatted.length());

                    editTextProductPrice.addTextChangedListener(this);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(ProductActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    editTextProductCode.setText(result.getContents());
                }
            });

    private final ActivityResultLauncher<Intent> selectCategoryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(this, "Item selected..!", Toast.LENGTH_SHORT).show();
                }
            });
}
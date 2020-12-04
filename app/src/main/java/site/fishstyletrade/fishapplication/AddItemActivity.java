package site.fishstyletrade.fishapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddItemActivity extends AppCompatActivity {

    public static final String ROOT_URL = "http://fishstyle-trade.site/";

    private EditText editText_products_id;
    private Spinner spinner_categories_id;
    private EditText editText_name;
    private EditText editText_description;
    private EditText editText_price;
    private EditText editText_quantity;
    private EditText editText_weight;
    private Spinner spinner_weight_unit;
    private SwitchCompat switch_stock_type;

    int products_id = 0;
    int products_categories_id;
    Double products_price;
    int products_quantity;
    String products_weight;
    String products_weight_unit;
    int products_slug;
    int stock;
    Double purchase_price;
    int products_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText_products_id = findViewById(R.id.editText_products_id);
        spinner_categories_id = findViewById(R.id.spinner_categories_id);
        editText_name = findViewById(R.id.editText_name);
        editText_description = findViewById(R.id.editText_description);
        editText_price = findViewById(R.id.editText_price);
        editText_quantity = findViewById(R.id.editText_quantity);
        editText_weight = findViewById(R.id.editText_weight);
        spinner_weight_unit = findViewById(R.id.spinner_weight_unit);
        switch_stock_type = findViewById(R.id.switch_stock_type);

    }

    private void addItem(final Product product) {

        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ROOT_URL).build();
        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.addToProducts(

                product.products_id,
                product.products_quantity,
                product.products_image,
                product.products_price,
                product.products_weight,
                product.products_weight_unit,
                product.products_type,
                1,
                1,
                product.products_id,

                new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        BufferedReader reader;
                        String output = "";
                        try {
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(AddItemActivity.this, output, Toast.LENGTH_LONG).show();
                        if (output.equals("error")) {
                            Toast.makeText(AddItemActivity.this, "Такой id уже используется", Toast.LENGTH_LONG).show();
                            editText_products_id.setError("Такой id уже используется");
                        } else {
                            Toast.makeText(AddItemActivity.this, "Успех!", Toast.LENGTH_LONG).show();
                            addRest(product, adapter);
                            finish();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(AddItemActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void addRest(Product product, RestAdapter adapter) {
        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.addToProductsDescription(

                product.products_id,
                product.language_id,
                product.products_name,
                product.products_description,

                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        BufferedReader reader;
                        String output = "";
                        try {
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(AddItemActivity.this, "DESCRIPTION" + output, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(AddItemActivity.this, "DESCRIPTION" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );


        api.addToProductsToCategories(

                product.products_id,
                product.categories_id,

                new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        BufferedReader reader;
                        String output = "";
                        try {
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(AddItemActivity.this, output, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(AddItemActivity.this, "CATEGORY" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        api.addToInventory(

                product.products_id,
                product.products_price,
                product.products_quantity,
                product.stock_type,

                new Callback<Response>() {

                    @Override
                    public void success(Response result, Response response) {
                        BufferedReader reader;
                        String output = "";
                        try {
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //inventory
//                        Toast.makeText(AddItemActivity.this, "INVENTORY" + output, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //inventory
                        Toast.makeText(AddItemActivity.this, "INVENTORY" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean isReady = true;

        if (id == R.id.action_settings) {
            if (editText_products_id.getText().toString().trim().equals("")) {
                editText_products_id.setError("Введите id");
                isReady = false;
            }
            if (editText_name.getText().toString().trim().equals("")) {
                editText_name.setError("Введите имя");
                isReady = false;
            }
            if (editText_description.getText().toString().trim().equals("")) {
                editText_description.setError("Введите описание");
                isReady = false;
            }
            if (editText_price.getText().toString().trim().equals("")) {
                editText_price.setError("Введите цену");
                isReady = false;
            }
            if (editText_quantity.getText().toString().trim().equals("")) {
                editText_quantity.setError("Введите количество");
                isReady = false;
            }
            if (editText_weight.getText().toString().trim().equals("")) {
                editText_weight.setError("Введите вес");
                isReady = false;
            }

            if (isReady) {
                products_id = Integer.parseInt(editText_products_id.getText().toString());
                switch (spinner_categories_id.getSelectedItem().toString()) {
                    case "Рыба охлажденная":
                        products_categories_id = 2;
                        break;
                    case "Морепродукты":
                        products_categories_id = 3;
                        break;
                    case "Рыбное филе":
                        products_categories_id = 4;
                        break;
                    case "Японская кухня":
                        products_categories_id = 5;
                        break;
                }
                products_price = Double.parseDouble(editText_price.getText().toString());
                products_quantity = Integer.parseInt(editText_quantity.getText().toString());
                products_weight = editText_weight.getText().toString();
                switch (spinner_weight_unit.getSelectedItem().toString()) {
                    case "Килограммы":
                        products_weight_unit = "Kilogram";
                        break;
                    case "Граммы":
                        products_weight_unit = "Gram";
                        break;
                }

                if (switch_stock_type.isChecked()) {
                    products_status = 1;
                } else {
                    products_status = 0;
                }

                products_slug = products_id;
                stock = products_quantity;
                purchase_price = products_price;

                Product product = new Product(
                        products_id,
                        1,
                        editText_name.getText().toString(),
                        editText_description.getText().toString(),
                        products_quantity,
                        "83",
                        products_price,
                        products_weight,
                        products_weight_unit,
                        0,
                        products_status,
                        1,
                        products_slug,
                        products_categories_id,
                        purchase_price,
                        stock,
                        "in"
                );
                addItem(product);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
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

public class EditItemActivity extends AppCompatActivity {

    private EditText editText_products_id;
    private Spinner spinner_categories_id;
    private EditText editText_name;
    private EditText editText_description;
    private EditText editText_price;
    private EditText editText_quantity;
    private EditText editText_weight;
    private Spinner spinner_weight_unit;
    private SwitchCompat switch_stock_type;

    public static final String ROOT_URL = "http://fishstyle-trade.site/";

    Product product;

    int products_id;
    String products_weight_unit;
    int products_status;
    int products_categories_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
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

        product = (Product) getIntent().getSerializableExtra("product");

        editText_products_id.setText(product.products_id.toString());
        switch (product.categories_id) {
            case 2:
                spinner_categories_id.setSelection(0);
                break;
            case 3:
                spinner_categories_id.setSelection(1);
                break;
            case 4:
                spinner_categories_id.setSelection(2);
                break;
            case 5:
                spinner_categories_id.setSelection(3);
                break;
        }
        editText_products_id.setEnabled(false);
        editText_name.setText(product.products_name);
        editText_description.setText(product.products_description);
        editText_price.setText(product.products_price.toString());
        editText_quantity.setText(product.products_quantity.toString());
        editText_weight.setText(product.products_weight);
        switch (product.products_weight_unit) {
            case "Kilogram":
                spinner_weight_unit.setSelection(0);
                break;
            case "Gram":
                spinner_weight_unit.setSelection(1);
                break;
        }
        if (product.products_status == 0) {
            switch_stock_type.setChecked(false);
        } else {
            switch_stock_type.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_save) {

            final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ROOT_URL).build();
            RegisterAPI api = adapter.create(RegisterAPI.class);

            products_id = Integer.parseInt(editText_products_id.getText().toString());
            products_weight_unit = editText_weight.getText().toString();

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

            api.editToProductsDescription(
                    products_id,
                    editText_name.getText().toString(),
                    editText_description.getText().toString(),

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
//                            Toast.makeText(EditItemActivity.this, output, Toast.LENGTH_LONG).show();
                            updateToProducts(adapter);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(EditItemActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateToProducts(final RestAdapter adapter) {

        RegisterAPI api = adapter.create(RegisterAPI.class);
        api.editToProducts(
                products_id,
                Integer.parseInt(editText_quantity.getText().toString()),
                Double.parseDouble(editText_price.getText().toString()),
                editText_weight.getText().toString(),
                products_weight_unit,
                products_status,

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
//                        Toast.makeText(EditItemActivity.this, output, Toast.LENGTH_LONG).show();
                        updateToCategories(adapter);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(EditItemActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void updateToCategories(final RestAdapter adapter) {

        RegisterAPI api = adapter.create(RegisterAPI.class);
        api.editToProductsToCategories(
                products_id,
                products_categories_id,

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
//                        Toast.makeText(EditItemActivity.this, output, Toast.LENGTH_LONG).show();
                        updateToInventory(adapter);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(EditItemActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void updateToInventory(final RestAdapter adapter) {

        RegisterAPI api = adapter.create(RegisterAPI.class);
        api.updateToInventory(
                products_id,
                Double.parseDouble(editText_price.getText().toString()),
                Integer.parseInt(editText_quantity.getText().toString()),

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
//                        Toast.makeText(EditItemActivity.this, output, Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(EditItemActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
package site.fishstyletrade.fishapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int products_id;
    String products_name;
    String products_description;
    int products_categories_id;
    Double products_price;
    int products_quantity;
    String products_weight;
    String products_weight_unit;
    int products_status;

    List<Product> productList;

    Intent intent_add_item;
    Intent intent_edit_item;

    private static final String FETCH_DATA_LINK = "http://fishstyle-trade.site/public/mobile/get_all_products.php";

    RecyclerView recyclerView;
    ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar);

        intent_add_item = new Intent(this, AddItemActivity.class);
        intent_edit_item = new Intent(this, EditItemActivity.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_add_item);
            }
        });
        productList = new ArrayList<>();
//        downloadJSON(FETCH_DATA_LINK);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ProductsAdapter(productList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloadJSON(FETCH_DATA_LINK);
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        productList.clear();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            products_id = obj.getInt("products_id");
            products_name = obj.getString("products_name");
            products_description = obj.getString("products_description");
            products_quantity = obj.getInt("products_quantity");
            products_price = obj.getDouble("products_price");
            products_weight = obj.getString("products_weight");
            products_weight_unit = obj.getString("products_weight_unit");
            products_status = obj.getInt("products_status");
            products_categories_id = obj.getInt("categories_id");

            productList.add(new Product(
                    products_id,
                    1,
                    products_name,
                    products_description,
                    products_quantity,
                    "83",
                    products_price,
                    products_weight,
                    products_weight_unit,
                    0,
                    products_status,
                    1,
                    products_id, //slug
                    products_categories_id,
                    products_price, //purchase_price
                    products_quantity, //stock
                    "in"
            ));
        }
        Collections.sort(productList);

        adapter = new ProductsAdapter(productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("TAG", "onItemClick position: " + position);
                Product product = productList.get(position);
                intent_edit_item.putExtra("product", product);
                startActivity(intent_edit_item);
            }
        });

    }
}
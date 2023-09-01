package com.jpa.myrestoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class detailCafeMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cafe_menu);

        Intent intent = getIntent();
        String nomormejad = intent.getStringExtra("nomor_meja");
        Log.d("nomormejad", "onCreate: "+nomormejad);
        // Menghubungkan komponen layout dengan elemen di Java
        ImageView cafeDetailImage = findViewById(R.id.cafeDetailImage);
        TextView cafeDetailName = findViewById(R.id.cafeDetailName);
        TextView cafeDetailDescription = findViewById(R.id.cafeDetailDescription);
        TextView mejaDetail = findViewById(R.id.mejaDetail);
        ImageView cartIcon = findViewById(R.id.cartIcon);

        // Mendapatkan objek SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("sessionData", Context.MODE_PRIVATE);

        // Mendapatkan nilai-nilai dari SharedPreferences
        int restoranId = sharedPreferences.getInt("restoran_id", -1);
        String restoranNama = sharedPreferences.getString("restoran_nama", "");
        String restoranDeskripsi = sharedPreferences.getString("restoran_deskripsi", "");
        String restoranFoto = sharedPreferences.getString("restoran_foto", "");
        String nomorm = sharedPreferences.getString("nomormeja", "");

        Picasso.get().load("http://192.168.1.2:8080"+restoranFoto).into(cafeDetailImage); // Menggunakan Picasso untuk memuat gambar
        cafeDetailName.setText(restoranNama);
        cafeDetailDescription.setText(restoranDeskripsi);
        mejaDetail.setText("nomor Meja : "+nomorm);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Kembali ke aktivitas sebelumnya
            }
        });

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detailCafeMenuActivity.this, keranjangActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.menuRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        new FetchProductsTask().execute();
    }

    private class FetchProductsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getSharedPreferences("sessionData", Context.MODE_PRIVATE);

            // Mendapatkan nilai-nilai dari SharedPreferences
            int restoranId = sharedPreferences.getInt("restoran_id", -1);
            String endpoint = "http://192.168.1.2:3001/restaurants/"+restoranId+"/products";
            try {
                URL url = new URL(endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Product product = new Product();
                        product.setKodeProduk(jsonObject.getString("kode_produk"));
                        product.setIdProduk(jsonObject.getInt("id_produk"));
                        product.setNamaProduk(jsonObject.getString("nama_produk"));
                        product.setNamaKategori(jsonObject.getString("nama_kategori"));
                        product.setHarga(jsonObject.getInt("harga"));
                        product.setFotoProduk(jsonObject.getString("foto_produk"));
                        product.setIdRestoran(jsonObject.getInt("id_restoran"));
                        productList.add(product);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(detailCafeMenuActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
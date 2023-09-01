package com.jpa.myrestoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class keranjangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private KeranjangAdapter adapter;
    private List<KeranjangItem> keranjangItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        recyclerView = findViewById(R.id.recyclerViewKeranjang);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi list data keranjang
        keranjangItemList = new ArrayList<>();

        // Buat permintaan JSON untuk mengambil data keranjang
        SharedPreferences sharedPreferences2 = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences2.getString("email", ""); // Ganti dengan email yang sesuai
        String url = "http://192.168.1.2:3001/keranjang/" + email;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing data JSON
                for (int i = 0; i < response.length(); i++) {
                    try {
                        // Ambil objek JSON
                        JSONObject obj = response.getJSONObject(i);

                        // Ambil data dari objek JSON dan tambahkan ke list
                        int idProduk = obj.getInt("id_produk");
                        String namaProduk = obj.getString("nama_produk");
                        String kodeProduk = obj.getString("kode_produk");
                        int idResto = obj.getInt("id_resto");
                        int harga = obj.getInt("harga");
                        String noMeja = obj.getString("no_meja");
                        double subQty = obj.getDouble("sub_qty");
                        String namaKategori = obj.getString("nama_kategori");

                        keranjangItemList.add(new KeranjangItem(idProduk, namaProduk, kodeProduk, idResto, harga, noMeja, subQty, namaKategori));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // Set data ke RecyclerView menggunakan KeranjangAdapter
                adapter = new KeranjangAdapter(keranjangItemList);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(keranjangActivity.this, "Terjadi kesalahan dalam mengambil data keranjang.", Toast.LENGTH_SHORT).show();
            }
        });

        // Create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Add the JSON request to the request queue
        requestQueue.add(request);
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Kembali ke aktivitas sebelumnya
            }
        });
        Button buttonBookingNow = findViewById(R.id.buttonBookingNow);
        buttonBookingNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil metode untuk mengirim data reservasi
                kirimDataReservasi();
            }
        });
    }
    private void kirimDataReservasi() {
        // Tentukan objek JSON untuk data reservasi
        JSONObject dataReservasi = new JSONObject();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("sessionData", Context.MODE_PRIVATE);
            SharedPreferences sharedPreferences2 = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            // Mendapatkan nilai-nilai dari SharedPreferences
            int restoranId = sharedPreferences.getInt("restoran_id", -1);
            String nomorm = sharedPreferences.getString("nomormeja", "");
            String email = sharedPreferences2.getString("email", "");
            Date tanggalSekarang = new Date();
            // Membuat objek SimpleDateFormat dengan format "yyyy-MM-dd"
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Menggunakan SimpleDateFormat untuk memformat tanggal
            String tanggalDalamFormat = dateFormat.format(tanggalSekarang);

            dataReservasi.put("email", email);
            dataReservasi.put("tanggal", tanggalDalamFormat);
            dataReservasi.put("meja", nomorm);
            dataReservasi.put("id_restoran", restoranId);
            dataReservasi.put("status", "Dipesan");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Tentukan URL untuk endpoint reservasi
        String url = "http://192.168.1.2:3001/reservasi";

        // Buat permintaan objek JSON
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, dataReservasi,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Tangani respons jika diperlukan
                        Log.d("d", "onResponse: "+response);
                        Intent intent = new Intent(keranjangActivity.this, ReservasiActivity.class);
                        startActivity(intent);
                        Toast.makeText(keranjangActivity.this, "Reservasi berhasil!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("d", "onResponse: "+error);
                        // Tangani respons error jika ada
                        Toast.makeText(keranjangActivity.this, "Terjadi kesalahan saat melakukan reservasi.", Toast.LENGTH_SHORT).show();
                    }
                });

        // Tambahkan permintaan ke antrian permintaan
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
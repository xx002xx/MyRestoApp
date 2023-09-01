package com.jpa.myrestoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReservasiActivity extends AppCompatActivity {
    private RecyclerView recyclerViewHistoriReservasi;
    private HistoriReservasiAdapter adapter;
    private List<HistoriReservasiItem> historiReservasiList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);
        recyclerViewHistoriReservasi = findViewById(R.id.recyclerViewHistoriReservasi);
        recyclerViewHistoriReservasi.setLayoutManager(new LinearLayoutManager(this));

        historiReservasiList = new ArrayList<>();

        // Mengambil data dari endpoint HTTP menggunakan Volley
        fetchDataFromHttp();

        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReservasiActivity.this, MainActivity.class);
                startActivity(intent);
                 // Kembali ke aktivitas sebelumnya
            }
        });
    }

    private void fetchDataFromHttp() {
        SharedPreferences sharedPreferences2 = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        // Mendapatkan nilai-nilai dari SharedPreferences
        String email = sharedPreferences2.getString("email", "");

        String url = "http://192.168.1.2:3001/reservasi/"+email;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String email = jsonObject.getString("email");
                                String tanggal = jsonObject.getString("tanggal");
                                String nomorBooking = jsonObject.getString("nomor_booking");
                                String meja = jsonObject.getString("meja");
                                String namaRestoran = jsonObject.getString("nama_restoran");
                                String nama = jsonObject.getString("nama");
                                String status = jsonObject.getString("status");

                                historiReservasiList.add(new HistoriReservasiItem(email, tanggal, nomorBooking, meja, namaRestoran, nama, status));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Setelah mengisi data, inisialisasikan adapter dan set ke RecyclerView
                        adapter = new HistoriReservasiAdapter(ReservasiActivity.this, historiReservasiList);
                        recyclerViewHistoriReservasi.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        // Menambahkan request ke antrian Volley
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
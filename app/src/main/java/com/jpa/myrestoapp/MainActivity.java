package com.jpa.myrestoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViews;
    private List<Restoran> restoranList = new ArrayList<>();
    private RestoranAdapter adapter;
    ImageView accountIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViews = findViewById(R.id.recyclerViewResto);
        accountIcon = findViewById(R.id.accountIcon);
        // Menggunakan GridLayoutManager dengan 2 kolom
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViews.setLayoutManager(layoutManager);

        adapter = new RestoranAdapter(restoranList);
        recyclerViews.setAdapter(adapter);

        // Mengambil data dari endpoint menggunakan Thread
        fetchDataFromEndpoint();

        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void fetchDataFromEndpoint() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.1.2:3001/restaurants")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id_restoran");
                        String nama = jsonObject.getString("nama_restoran");
                        String deskripsi = jsonObject.getString("deskripsi");
                        String foto = jsonObject.getString("foto");
                        restoranList.add(new Restoran(id, nama, deskripsi, foto));
                    }

                    // Memperbarui tampilan di UI thread setelah operasi selesai
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
package com.jpa.myrestoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class detailCafeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cafe);

        // Mendapatkan data dari intent
        Intent intent = getIntent();
        int restoranId = intent.getIntExtra("restoran_id", -1);
        String restoranNama = intent.getStringExtra("restoran_nama");
        String restoranDeskripsi = intent.getStringExtra("restoran_deskripsi");
        String restoranFoto = intent.getStringExtra("restoran_foto");

        SharedPreferences sharedPreferences = getSharedPreferences("sessionData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("restoran_id", restoranId);
        editor.putString("restoran_nama", restoranNama);
        editor.putString("restoran_deskripsi", restoranDeskripsi);
        editor.putString("restoran_foto", restoranFoto);
        editor.apply();

        // Menghubungkan komponen layout dengan elemen di Java
        ImageView cafeDetailImage = findViewById(R.id.cafeDetailImage);
        TextView cafeDetailName = findViewById(R.id.cafeDetailName);
        TextView cafeDetailDescription = findViewById(R.id.cafeDetailDescription);

        // Mengatur data ke komponen layout
        Picasso.get().load("http://192.168.1.2:8080"+restoranFoto).into(cafeDetailImage); // Menggunakan Picasso untuk memuat gambar
        cafeDetailName.setText(restoranNama);
        cafeDetailDescription.setText(restoranDeskripsi);

        // ...
        // Anda juga perlu menghubungkan dan mengisi data untuk RecyclerView meja di sini
        // ...

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Kembali ke aktivitas sebelumnya
            }
        });
        setupMejaRecyclerView();
    }


    private void setupMejaRecyclerView() {
        RecyclerView mejaRecyclerView = findViewById(R.id.mejaRecyclerView);
        // Menggunakan GridLayoutManager dengan 2 kolom
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false);
        mejaRecyclerView.setLayoutManager(layoutManager);

        List<Meja> mejaList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mejaList.add(new Meja(i));
        }


        MejaAdapter mejaAdapter = new MejaAdapter(mejaList);
        mejaRecyclerView.setAdapter(mejaAdapter);
    }


}
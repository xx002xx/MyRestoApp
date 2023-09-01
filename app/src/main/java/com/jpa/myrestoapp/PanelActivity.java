package com.jpa.myrestoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        Button buttonLogout = findViewById(R.id.buttonLogout);
        Button buttonHistoryReservasi = findViewById(R.id.buttonHistoryReservasi);

        // Menambahkan penanganan klik untuk tombol "Logout"
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menghapus data dari SharedPreferences "loginStatus"
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Menghapus data dari SharedPreferences "sessionData"
                SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("sessionData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.clear();
                editor1.apply();

                // Menghapus data dari SharedPreferences "MyPrefs"
                SharedPreferences sharedPreferences2 = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.clear();
                editor2.apply();

                Intent intent = new Intent(PanelActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Menambahkan penanganan klik untuk tombol "History Reservasi"
        buttonHistoryReservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PanelActivity.this, ReservasiActivity.class);
                startActivity(intent);
            }
        });
    }
}
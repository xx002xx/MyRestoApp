package com.jpa.myrestoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class RegisterActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPassword, editTextPassword2;
    private Button buttonRegister;
    private TextView textViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Arahkan pengguna kembali ke halaman login
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void registerUser() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String password2 = editTextPassword2.getText().toString();

        // Validasi input
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)) {
            Toast.makeText(this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat objek JSON dengan data yang diperlukan
        JSONObject postData = new JSONObject();
        try {
            postData.put("nama", name);
            postData.put("email", email);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int responseCode = -1;

                try {
                    String endpoint = "http://192.168.1.2:3001/register";
                    URL url = new URL(endpoint);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setDoOutput(true);

                    // Kirim data JSON
                    DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                    os.write(postData.toString().getBytes("UTF-8"));
                    os.flush();
                    os.close();

                    responseCode = connection.getResponseCode();

                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return responseCode;
            }

            @Override
            protected void onPostExecute(Integer responseCode) {
                super.onPostExecute(responseCode);

                if (responseCode == 201) {
                    Toast.makeText(RegisterActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("post errr", "onPostExecute: "+responseCode);
                    Toast.makeText(RegisterActivity.this, "Email sudah digunakan", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

}
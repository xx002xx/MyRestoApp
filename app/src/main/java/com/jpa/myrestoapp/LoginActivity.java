package com.jpa.myrestoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                performLoginWithOkHttp(email, password);
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Arahkan pengguna ke halaman pendaftaran
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performLoginWithOkHttp(String email, String password) {
        // Buat objek JSON dengan data yang diperlukan
        JSONObject postData = new JSONObject();
        try {
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
                    String endpoint = "http://192.168.1.2:3001/login";
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
                Log.e("post errr", "onPostExecute: "+responseCode);
                if (responseCode == 200) {
                    Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                    String email = editTextEmail.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.apply();

                    SharedPreferences sharedPreferences2 = getSharedPreferences("loginStatus", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.putBoolean("userIsLoggedIn", true);
                    editor2.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("post errr", "onPostExecute: "+responseCode);
                    Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();

                }
            }
        }.execute();
    }


}
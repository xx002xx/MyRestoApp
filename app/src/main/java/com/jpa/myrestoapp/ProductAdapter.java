package com.jpa.myrestoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private TextView productNameTextView;
        private TextView productPriceTextView, menuItemDescription;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.menuItemImage);
            productNameTextView = itemView.findViewById(R.id.menuItemName);
            productPriceTextView = itemView.findViewById(R.id.menuItemPrice);
            menuItemDescription = itemView.findViewById(R.id.menuItemDescription);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Set data to views
        holder.productNameTextView.setText(product.getNamaProduk());
        holder.productPriceTextView.setText("Harga: Rp" + product.getHarga());
        holder.menuItemDescription.setText(product.getNamaKategori());
        // Load image using Picasso (or any other image loading library)
        Picasso.get().load("http://192.168.1.2:8080"+product.getFotoProduk()).into(holder.productImageView);
        // Set click listener to the card

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("sessionData", Context.MODE_PRIVATE);
                SharedPreferences sharedPreferences2 = v.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                // Mendapatkan nilai-nilai dari SharedPreferences
                int restoranId = sharedPreferences.getInt("restoran_id", -1);
                String nomorm = sharedPreferences.getString("nomormeja", "");
                String email = sharedPreferences2.getString("email", "");
                // Create JSON data for the POST request
                JSONObject postData = new JSONObject();
                try {
                    postData.put("email", email);
                    postData.put("id_produk", product.getIdProduk());
                    postData.put("id_resto", restoranId);
                    postData.put("no_meja", nomorm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Create a RequestQueue using Volley (or use OkHttp, Retrofit, etc.)
                RequestQueue requestQueue = Volley.newRequestQueue(holder.itemView.getContext());

                // Make the POST request
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                        "http://192.168.1.2:3001/insert-keranjang", postData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Handle response
                                Toast.makeText(holder.itemView.getContext(), "Data added to keranjang", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                Toast.makeText(holder.itemView.getContext(), "Error adding data to keranjang", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Add the request to the queue
                requestQueue.add(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

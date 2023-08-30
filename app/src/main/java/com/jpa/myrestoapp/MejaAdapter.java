package com.jpa.myrestoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MejaAdapter extends RecyclerView.Adapter<MejaAdapter.MejaViewHolder> {

    private List<Meja> mejaList;

    public MejaAdapter(List<Meja> mejaList) {
        this.mejaList = mejaList;
    }

    @NonNull
    @Override
    public MejaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meja_item, parent, false);
        return new MejaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MejaViewHolder holder, int position) {
        Meja meja = mejaList.get(position);
        holder.mejaButton.setText("Meja " + meja.getNomorMeja());
        holder.mejaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
                boolean userIsLoggedIn = sharedPreferences.getBoolean("userIsLoggedIn", false);
                if (!userIsLoggedIn) {
                    // Jika pengguna belum masuk, arahkan ke halaman masuk
                    showLoginAlert(view.getContext());

                    return; // Keluar dari method karena tindakan tidak dapat dilanjutkan
                }else{
                    Intent intent = new Intent(view.getContext(), detailCafeMenuActivity.class);
                    intent.putExtra("nomor_meja", meja.getNomorMeja()); // Kirim data ID restoran ke detailCafeActivity
                    view.getContext().startActivity(intent);
                    Log.d("Debug", "meja.getNomorMeja(): " + meja.getNomorMeja());
                    SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("sessionData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("nomormeja",  String.valueOf(meja.getNomorMeja()));
                    editor.apply();
                }


            }
        });
    }

    private void showLoginAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Login");
        builder.setMessage("Kamu Harus Login Terlebih dahulu untuk meneruskan pesanan.");
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Arahkan pengguna ke halaman masuk
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public int getItemCount() {
        return mejaList.size();
    }

    static class MejaViewHolder extends RecyclerView.ViewHolder {

        Button mejaButton;

        MejaViewHolder(@NonNull View itemView) {
            super(itemView);
            mejaButton = itemView.findViewById(R.id.mejaButton);
        }
    }
}
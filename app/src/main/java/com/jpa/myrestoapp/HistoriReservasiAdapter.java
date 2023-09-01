package com.jpa.myrestoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoriReservasiAdapter extends RecyclerView.Adapter<HistoriReservasiAdapter.ViewHolder> {
    private List<HistoriReservasiItem> historiReservasiList;
    private Context context;

    public HistoriReservasiAdapter(Context context, List<HistoriReservasiItem> historiReservasiList) {
        this.context = context;
        this.historiReservasiList = historiReservasiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_histori_reservasi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoriReservasiItem item = historiReservasiList.get(position);

        holder.textViewEmail.setText("Email: " + item.getEmail());
        holder.textViewTanggal.setText("Tanggal: " + item.getTanggal());
        holder.textViewNomorBooking.setText("Nomor Booking: " + item.getNomorBooking());
        holder.textViewMeja.setText("Meja: " + item.getMeja());
        holder.textViewNamaRestoran.setText("Nama Restoran: " + item.getNamaRestoran());
        holder.textViewNama.setText("Nama: " + item.getNama());
        holder.textViewStatus.setText("Status: " + item.getStatus());
    }

    @Override
    public int getItemCount() {
        return historiReservasiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEmail, textViewTanggal, textViewNomorBooking, textViewMeja, textViewNamaRestoran, textViewNama, textViewStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewTanggal = itemView.findViewById(R.id.textViewTanggal);
            textViewNomorBooking = itemView.findViewById(R.id.textViewNomorBooking);
            textViewMeja = itemView.findViewById(R.id.textViewMeja);
            textViewNamaRestoran = itemView.findViewById(R.id.textViewNamaRestoran);
            textViewNama = itemView.findViewById(R.id.textViewNama);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }
    }
}
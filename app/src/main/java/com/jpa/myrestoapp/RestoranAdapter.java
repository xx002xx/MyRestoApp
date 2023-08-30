package com.jpa.myrestoapp;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RestoranAdapter extends RecyclerView.Adapter<RestoranAdapter.RestoranViewHolder> {

    private List<Restoran> restoranList;

    public RestoranAdapter(List<Restoran> restoranList) {
        this.restoranList = restoranList;
    }

    @NonNull
    @Override
    public RestoranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_item, parent, false);
        return new RestoranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoranViewHolder holder, int position) {
        Restoran restoran = restoranList.get(position);
        holder.cafeName.setText(restoran.getNama());
        holder.cafeDescription.setText(restoran.getDeskripsi());

        // Load gambar menggunakan Picasso
        Picasso.get().load("http://192.168.1.2:8080"+restoran.getFoto()).into(holder.cafeImage);
        holder.viewButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), detailCafeActivity.class);
                intent.putExtra("restoran_id", restoran.getId()); // Kirim data ID restoran ke detailCafeActivity
                intent.putExtra("restoran_nama", restoran.getNama());
                intent.putExtra("restoran_deskripsi", restoran.getDeskripsi()); // Kirim deskripsi restoran
                intent.putExtra("restoran_foto", restoran.getFoto()); // Kirim URL foto restoran
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return restoranList.size();
    }

    static class RestoranViewHolder extends RecyclerView.ViewHolder {

        Button viewButtons;
        ImageView cafeImage;
        TextView cafeName;
        TextView cafeDescription;

        RestoranViewHolder(@NonNull View itemView) {
            super(itemView);
            cafeImage = itemView.findViewById(R.id.cafeImage);
            cafeName = itemView.findViewById(R.id.cafeName);
            cafeDescription = itemView.findViewById(R.id.cafeDescription);
            viewButtons = itemView.findViewById(R.id.viewButtons) ;
        }
    }
}

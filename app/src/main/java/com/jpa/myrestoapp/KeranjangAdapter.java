package com.jpa.myrestoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder> {

    private List<KeranjangItem> keranjangItemList;

    public KeranjangAdapter(List<KeranjangItem> keranjangItemList) {
        this.keranjangItemList = keranjangItemList;
    }

    public class KeranjangViewHolder extends RecyclerView.ViewHolder {

        private TextView productNameTextView;
        private TextView productPriceTextView;
        private TextView menuItemDescription;

        public KeranjangViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.menuItemName);
            productPriceTextView = itemView.findViewById(R.id.menuItemPrice);
            menuItemDescription = itemView.findViewById(R.id.menuItemDescription);
        }
    }

    @NonNull
    @Override
    public KeranjangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_keranjang, parent, false);
        return new KeranjangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangViewHolder holder, int position) {
        KeranjangItem keranjangItem = keranjangItemList.get(position);

        // Set data to views
        holder.productNameTextView.setText(keranjangItem.getSubQty()+" x "+keranjangItem.getNamaProduk());
        holder.productPriceTextView.setText("Sub Total : "+keranjangItem.getSubQty()+" x Rp" + keranjangItem.getHarga()+" = Rp "+keranjangItem.getHarga()*keranjangItem.getSubQty());
        holder.menuItemDescription.setText("Kategori : "+keranjangItem.getNamaKategori());

    }

    @Override
    public int getItemCount() {
        return keranjangItemList.size();
    }
}

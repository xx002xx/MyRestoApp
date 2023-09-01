package com.jpa.myrestoapp;

public class KeranjangItem {
    private int idProduk;
    private String namaProduk;
    private String kodeProduk;
    private int idResto;
    private int harga;
    private String noMeja;
    private double subQty;
    private String namaKategori;

    public KeranjangItem(int idProduk, String namaProduk, String kodeProduk, int idResto,int harga, String noMeja, double subQty, String namaKategori) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.kodeProduk = kodeProduk;
        this.idResto = idResto;
        this.harga = harga;
        this.noMeja = noMeja;
        this.subQty = subQty;
        this.namaKategori = namaKategori;
    }

    public int getIdProduk() {
        return idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public int getIdResto() {
        return idResto;
    }

    public String getNoMeja() {
        return noMeja;
    }

    public double getSubQty() {
        return subQty;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}

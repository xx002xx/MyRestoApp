package com.jpa.myrestoapp;

public class HistoriReservasiItem {
    private String email;
    private String tanggal;
    private String nomorBooking;
    private String meja;
    private String namaRestoran;
    private String nama;
    private String status;

    public HistoriReservasiItem(String email, String tanggal, String nomorBooking, String meja, String namaRestoran, String nama, String status) {
        this.email = email;
        this.tanggal = tanggal;
        this.nomorBooking = nomorBooking;
        this.meja = meja;
        this.namaRestoran = namaRestoran;
        this.nama = nama;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNomorBooking() {
        return nomorBooking;
    }

    public String getMeja() {
        return meja;
    }

    public String getNamaRestoran() {
        return namaRestoran;
    }

    public String getNama() {
        return nama;
    }

    public String getStatus() {
        return status;
    }
}

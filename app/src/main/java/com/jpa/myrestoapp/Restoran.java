package com.jpa.myrestoapp;

public class Restoran {
    private int id;
    private String nama;
    private String deskripsi;
    private String foto;

    public Restoran(int id, String nama, String deskripsi, String foto) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getFoto() {
        return foto;
    }
}

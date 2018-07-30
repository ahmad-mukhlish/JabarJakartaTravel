package com.programmerbaper.jabarjakartatravel.entities;

public class Tiket {

    private String nama;
    private int harga;
    private int jumlah;
    private int kode;
    private int status;
    private String tanggal;
    private String token;

    public Tiket(String nama, int harga, int jumlah, int kode, String token, String tanggal, int status) {
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
        this.kode = kode;
        this.token = token;
        this.tanggal = tanggal;
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getKode() {
        return kode;
    }

    public String getToken() {
        return token;
    }

    public int getStatus() {
        return status;
    }
}

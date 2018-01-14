package com.programmerbaper.jabarjakartatravel.entities;

import android.support.v7.widget.CardView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by ahmad on 1/14/2018.
 */

public class Waktu {

    private String waktu;
    private int jumlah_kursi;


    private ProgressBar progressBar;
    private CardView cardView;
    private TextView txtKursi;
    private TextView txtTulisan;


    public Waktu(ProgressBar progressBar, CardView cardView, TextView txtKursi, TextView txtTulisan) {
        this.progressBar = progressBar;
        this.cardView = cardView;
        this.txtKursi = txtKursi;
        this.txtTulisan = txtTulisan;
    }

    public String getWaktu() {
        return waktu;
    }

    public int getJumlah_kursi() {
        return jumlah_kursi;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public CardView getCardView() {
        return cardView;
    }

    public TextView getTxtKursi() {
        return txtKursi;
    }

    public TextView getTxtTulisan() {
        return txtTulisan;
    }


    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public void setJumlah_kursi(int jumlah_kursi) {
        this.jumlah_kursi = jumlah_kursi;
    }
}

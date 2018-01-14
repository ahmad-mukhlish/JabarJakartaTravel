package com.programmerbaper.jabarjakartatravel.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by ahmad on 1/12/2018.
 */

public class Trayek implements Parcelable {

    private int mIdTrayek;
    private String mNama;
    private int mTarif;

    //TODO the link should be understood by the Lecture
    public static final String BASE_PATH = "http://192.168.1.3/travel/";
    public static final String JSON_TRAYEK = "server.php?operasi=trayek";
    public static final String JSON_WAKTU = "server.php?operasi=pilih_waktu&kode_trayek=";
    public static final String JSON_JADWAL1 = "server.php?operasi=jadwal&kode_trayek=";
    public static final String JSON_JADWAL2 = "&waktu=";
    public static final String JSON_TRANSAKSI = "server.php?operasi=transaksi";


    public Trayek(int mIdTrayek, String mNama, int mTarif) {
        this.mIdTrayek = mIdTrayek;
        this.mNama = mNama;
        this.mTarif = mTarif;
    }

    protected Trayek(Parcel in) {
        mIdTrayek = in.readInt();
        mNama = in.readString();
        mTarif = in.readInt();
    }

    public static final Creator<Trayek> CREATOR = new Creator<Trayek>() {
        @Override
        public Trayek createFromParcel(Parcel in) {
            return new Trayek(in);
        }

        @Override
        public Trayek[] newArray(int size) {
            return new Trayek[size];
        }
    };

    public int getmIdTrayek() {
        return mIdTrayek;
    }

    public String getmNama() {
        return mNama;
    }

    public int getmTarif() {
        return mTarif;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIdTrayek);
        dest.writeString(mNama);
        dest.writeInt(mTarif);
    }

    public static String formatter(String input) {
        if (!input.isEmpty()) {
            DecimalFormatSymbols symbol = new DecimalFormatSymbols();
            symbol.setGroupingSeparator('.');

            DecimalFormat format = new DecimalFormat(" Rp ###,###");
            format.setDecimalFormatSymbols(symbol);

            return format.format(Double.parseDouble(input));
        } else {
            return "";
        }

    }

}


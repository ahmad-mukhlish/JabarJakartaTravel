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

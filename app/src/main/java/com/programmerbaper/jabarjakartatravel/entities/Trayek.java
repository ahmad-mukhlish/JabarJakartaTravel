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
    private String mWaktuBerangkat;

    public static final String BASE_PATH = "https://tr4veler.gurisa.com/api/v0/";
    public static final String GET_TRAYEK = "routes";
    public static final String POST_REGISTER = "users";
    public static final String POST_AUTH = "auth/login" ;
    public static final String POST_TRANSACTION = "transactions" ;
    public static final String GET_TIKET = "users/";
    public static final String GET_TIKET_2 = "/details";







    public Trayek(int mIdTrayek, String mNama, int mTarif, String mWaktuBerangkat) {
        this.mIdTrayek = mIdTrayek;
        this.mNama = mNama;
        this.mTarif = mTarif;
        this.mWaktuBerangkat = mWaktuBerangkat;
    }

    protected Trayek(Parcel in) {
        mIdTrayek = in.readInt();
        mNama = in.readString();
        mTarif = in.readInt();
        mWaktuBerangkat = in.readString();
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

    public String getmWaktuBerangkat() {
        return mWaktuBerangkat;
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
        dest.writeString(mWaktuBerangkat);
    }

    public static String formatter(String input) {
        if (!input.isEmpty()) {
            DecimalFormatSymbols symbol = new DecimalFormatSymbols();
            symbol.setGroupingSeparator('.');

            DecimalFormat format = new DecimalFormat("Rp ###,###");
            format.setDecimalFormatSymbols(symbol);

            return format.format(Double.parseDouble(input));
        } else {
            return "";
        }

    }


}


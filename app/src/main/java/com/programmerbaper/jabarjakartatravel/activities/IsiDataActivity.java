package com.programmerbaper.jabarjakartatravel.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.TrayekRecycleAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;

public class IsiDataActivity extends AppCompatActivity {

    private Trayek mChosenTrayek;
    private int mJumlahKursi;
    private String mWaktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data);

        getBundle();
        setTitle("Isi Data Transaksi");

        TextView trayek = findViewById(R.id.trayek);
        trayek.setText(mChosenTrayek.getmNama());

        TextView tanggal = findViewById(R.id.tanggal);
        tanggal.setText(TrayekRecycleAdapter.mTanggal);

        TextView jam = findViewById(R.id.jam);
        jam.setText(mWaktu);
    }

    private void getBundle() {

        Bundle bundle = getIntent().getExtras();
        mChosenTrayek = bundle.getParcelable("trayek");
        mWaktu = bundle.getString("waktu");
        mJumlahKursi = bundle.getInt("kursi");

    }

}

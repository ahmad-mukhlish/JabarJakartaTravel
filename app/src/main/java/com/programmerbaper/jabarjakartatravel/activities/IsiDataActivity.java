package com.programmerbaper.jabarjakartatravel.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.TrayekRecycleAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.redmadrobot.inputmask.MaskedTextChangedListener;

public class IsiDataActivity extends AppCompatActivity {

    private Trayek mChosenTrayek;
    private int mJumlahKursi, mDiambil = 1;
    private String mWaktu;
    private boolean mBack = false;

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

        final TextView tersedia = findViewById(R.id.tersedia);
        tersedia.setText(mDiambil + " dari " + mJumlahKursi);

        Button plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDiambil == mJumlahKursi) {
                    Toast.makeText(getBaseContext(), "Jumlah kursi sudah maksimal", Toast.LENGTH_SHORT).show();
                } else {
                    mDiambil++;
                    tersedia.setText(mDiambil + " dari " + mJumlahKursi);
                }
            }
        });

        Button minus = findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDiambil == 1) {
                    Toast.makeText(getBaseContext(), "Jumlah kursi sudah minimal", Toast.LENGTH_SHORT).show();

                } else {
                    mDiambil--;
                    tersedia.setText(mDiambil + " dari " + mJumlahKursi);
                }
            }
        });

        final EditText nama = findViewById(R.id.nama);
        final EditText nomor_ktp = findViewById(R.id.ktp);


        final MaskedTextChangedListener ktpListener = new MaskedTextChangedListener(
                "[0000]-[0000]-[0000]-[0000]",
                true,
                nomor_ktp,
                null,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                        Log.d(MainActivity.class.getSimpleName(), extractedValue);
                        Log.d(MainActivity.class.getSimpleName(), String.valueOf(maskFilled));
                    }
                }
        );

        nomor_ktp.addTextChangedListener(ktpListener);
        nomor_ktp.setOnFocusChangeListener(ktpListener);

        final EditText nomor_telp = findViewById(R.id.telp);

        final MaskedTextChangedListener telpListener = new MaskedTextChangedListener(
                "+62 [0000]-[0000]-[0000]",
                true,
                nomor_telp,
                null,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                        Log.d(MainActivity.class.getSimpleName(), extractedValue);
                        Log.d(MainActivity.class.getSimpleName(), String.valueOf(maskFilled));
                    }
                }
        );

        nomor_telp.addTextChangedListener(telpListener);
        nomor_telp.setOnFocusChangeListener(telpListener);


        final EditText nomor_rek = findViewById(R.id.rekening);
        final MaskedTextChangedListener rekListener = new MaskedTextChangedListener(
                "[0000]-[0000]-[00000]",
                true,
                nomor_rek,
                null,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                        Log.d(MainActivity.class.getSimpleName(), extractedValue);
                        Log.d(MainActivity.class.getSimpleName(), String.valueOf(maskFilled));
                    }
                }
        );

        nomor_rek.addTextChangedListener(rekListener);
        nomor_rek.setOnFocusChangeListener(rekListener);


        Button pesan = findViewById(R.id.pesan);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(nama.getText().toString().isEmpty() || nomor_ktp.getText().toString().isEmpty()
                        || nomor_telp.getText().toString().isEmpty() || nomor_rek.getText().toString().isEmpty())) {
                    //TODO POST THE DATA TO THE DATABASE HERE
                } else {
                    Toast.makeText(getBaseContext(), "Masih ada field yang kosong...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getBundle() {

        Bundle bundle = getIntent().getExtras();
        mChosenTrayek = bundle.getParcelable("trayek");
        mWaktu = bundle.getString("waktu");
        mJumlahKursi = bundle.getInt("kursi");

    }

    @Override
    public void onBackPressed() {
        if (mBack)
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Tekan back sekali lagi untuk kembali", Toast.LENGTH_SHORT).show();
        mBack = true;


    }
}

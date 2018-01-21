package com.programmerbaper.jabarjakartatravel.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.networking.QueryUtils;
import com.redmadrobot.inputmask.MaskedTextChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CekKodeTiketActivity extends AppCompatActivity {

    private String LOG_TAG = CekKodeTiketActivity.class.getName();

    private String mID, mNomorKtp, mKode;

    private TextView mTiket;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mShorcut = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_kode_tiket);

        Bundle bundle = getIntent().getExtras();
        mShorcut = bundle.getBoolean("shortcut");

        mSwipeRefreshLayout = findViewById(R.id.swipe);
        mTiket = findViewById(R.id.tiket);

        final EditText idTransaksi = findViewById(R.id.id_transaksi);
        final MaskedTextChangedListener idListener = new MaskedTextChangedListener(
                "[000]-[000]-[000]",
                true,
                idTransaksi,
                null,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                        Log.d(MainActivity.class.getSimpleName(), extractedValue);
                        Log.d(MainActivity.class.getSimpleName(), String.valueOf(maskFilled));
                    }
                }
        );

        idTransaksi.addTextChangedListener(idListener);
        idTransaksi.setOnFocusChangeListener(idListener);

        final EditText nomorKtp = findViewById(R.id.nomor_ktp);
        final MaskedTextChangedListener ktpListener = new MaskedTextChangedListener(
                "[0000]-[0000]-[0000]-[0000]",
                true,
                nomorKtp,
                null,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                        Log.d(MainActivity.class.getSimpleName(), extractedValue);
                        Log.d(MainActivity.class.getSimpleName(), String.valueOf(maskFilled));
                    }
                }
        );

        nomorKtp.addTextChangedListener(ktpListener);
        nomorKtp.setOnFocusChangeListener(ktpListener);

        if (!mShorcut) {
            idTransaksi.setText(IsiDataActivity.mask(IsiDataActivity.generateKode(IsiDataActivity.mKode)));
            nomorKtp.setText(IsiDataActivity.mKtp);

        }


        Button ambil = findViewById(R.id.ambil);
        ambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new Intent(CekKodeTiketActivity.this, MainActivity.class)));
            }
        });

        Button hubungi = findViewById(R.id.hubungi);
        hubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "089510580775";
                String uri = "tel:" + number;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        Button cek = findViewById(R.id.cek);
        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mID = IsiDataActivity.unmask(idTransaksi.getText().toString());
                mNomorKtp = IsiDataActivity.unmask(nomorKtp.getText().toString());

                if (!(mID.length() != 9 || mNomorKtp.length() != 16)) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mKode = "";

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new CekAsyncTask().execute(Trayek.BASE_PATH + Trayek.JSON_CEK1 + mID
                                    + Trayek.JSON_CEK2 + mNomorKtp);
                        }
                    }, 2000);

                } else {
                    Toast.makeText(getBaseContext(), "Masih ada field yang belum valid...", Toast.LENGTH_SHORT).show();
                }


            }
        });

        setTitle("Cek Kode Tiket");

    }

    private class CekAsyncTask extends AsyncTask<String, Void, String> {


        public CekAsyncTask() {
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }


            try {
                JSONArray rootArray = new JSONArray(QueryUtils.fetchResponse(urls[0]));
                for (int i = 0; i < rootArray.length(); i++) {
                    JSONObject object = rootArray.getJSONObject(i);
                    mKode = object.getString("kode_tiket");
                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            mSwipeRefreshLayout.setRefreshing(false);
            Log.v("cik", mKode);
            if (!mKode.equals("") && !mKode.equals("-"))
                mTiket.setText("Kode Tiket : " + mKode);
            else if (mKode.equals("-"))
                mTiket.setText("Pembayaran belum tervalidasi");
            else
                mTiket.setText("ID Transaksi atau Nomor KTP tidak cocok");
        }
    }

}

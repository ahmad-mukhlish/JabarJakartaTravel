package com.programmerbaper.jabarjakartatravel.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.TrayekRecycleAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.networking.QueryUtils;
import com.slmyldz.random.Randoms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsiDataActivity extends AppCompatActivity {

    private String LOG_TAG = IsiDataActivity.class.getName();

    private boolean mBack = false;

    private Button mPesan;

    public static int mKode, mDiambil = 1;
    public static Trayek mChosenTrayek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data);

        getBundle();
        setTitle("Pilih Jumlah Kursi");


        TextView trayek = findViewById(R.id.trayek);
        trayek.setText(mChosenTrayek.getmNama());

        TextView tanggal = findViewById(R.id.tanggal);
        tanggal.setText(TrayekRecycleAdapter.mTanggal);

        TextView jam = findViewById(R.id.jam);
        jam.setText(mChosenTrayek.getmWaktuBerangkat().substring(11));

        TextView kode = findViewById(R.id.kode);
        mKode = Randoms.Integer(0, 999);
        kode.setText(generateKode(mKode));

        final TextView harga = findViewById(R.id.harga);
        harga.setText("Rp. " + (mChosenTrayek.getmTarif() + mKode) + "");

        TextView nama = findViewById(R.id.nama);
        nama.setText(LoginActivity.user.getName());

        TextView email = findViewById(R.id.email);
        email.setText(LoginActivity.user.getEmail());

        final TextView tersedia = findViewById(R.id.tersedia);
        tersedia.setText(mDiambil + "");

        Button plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiambil++;
                harga.setText(("Rp. " + (mChosenTrayek.getmTarif() * mDiambil + mKode) + ""));
                tersedia.setText(mDiambil + "");
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
                    harga.setText(("Rp. " + (mChosenTrayek.getmTarif() * mDiambil + mKode) + ""));
                    tersedia.setText(mDiambil + "");
                }
            }
        });


        mPesan = findViewById(R.id.pesan);
        mPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IsiAsyncTask(getBaseContext()).execute(Trayek.BASE_PATH + Trayek.POST_TRANSACTION);
            }
        });

    }

    private void getBundle() {

        Bundle bundle = getIntent().getExtras();
        mChosenTrayek = bundle.getParcelable("trayek");
    }


    @Override
    public void onBackPressed() {
        if (mBack)
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Tekan back sekali lagi untuk kembali", Toast.LENGTH_SHORT).show();
        mBack = true;


    }


    private String sqlFormatDate(SimpleDateFormat sdf, String input) {

        Date d = null;
        try {
            d = sdf.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(d);


    }


    public static String generateKode(int kode) {

        String hasil = "";


        if (kode < 1000 && kode > 99)
            hasil += kode;
        else if (kode < 100 && kode > 9)
            hasil += "0" + kode;
        else
            hasil += "00" + kode;


        return hasil;
    }

    private class IsiAsyncTask extends AsyncTask<String, Void, String> {

        private Context mContext;
        private Boolean status = false;

        IsiAsyncTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                JSONObject jsonObject = new JSONObject(QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[0]), createJsonMessage()));
                status = jsonObject.getBoolean("status");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;


        }


        @Override
        protected void onPostExecute(String response) {
            if (status) {
                Toast.makeText(getBaseContext(), "Transaksi Sukses", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IsiDataActivity.this, CaraBayarActivity.class);
                intent.putExtra("shortcut", false);
                intent.putExtra("kode", mKode);
                intent.putExtra("harga", mChosenTrayek.getmTarif());
                intent.putExtra("jumlah", mDiambil);
                startActivity(intent);
            } else
                Toast.makeText(getBaseContext(), "Terjadi kesalahan ^_^", Toast.LENGTH_SHORT).show();
        }


        private String createJsonMessage() {

            JSONObject root = new JSONObject();
            JSONObject trayek = new JSONObject();
            JSONArray data = new JSONArray();

            try {
                trayek.accumulate("id", mChosenTrayek.getmIdTrayek());
                trayek.accumulate("cart", mDiambil);

                data.put(trayek);

                root.accumulate("user_id", LoginActivity.user.getId());
                root.accumulate("data", data);
                root.accumulate("secret", mKode + "");

            } catch (JSONException e) {
                Log.e("Kelas Login", "Error when create JSON message", e);
            }

            return root.toString();

        }
    }

}

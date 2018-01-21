package com.programmerbaper.jabarjakartatravel.activities;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.programmerbaper.jabarjakartatravel.networking.QueryUtils;
import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.helper.Mask;
import com.redmadrobot.inputmask.model.CaretString;
import com.slmyldz.random.Randoms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IsiDataActivity extends AppCompatActivity {

    private String LOG_TAG = IsiDataActivity.class.getName();

    private int mJumlahKursi, mJadwal;
    private String mWaktu, mNama, mKtp, mTelp, mRekening;
    private boolean mBack = false;

    private Button mPesan;

    public static int mKode, mDiambil = 1;
    public static Trayek mChosenTrayek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data);

        getBundle();
        setTitle("Isi Data Transaksi");

        new JadwalAsyncTask().execute(Trayek.BASE_PATH + Trayek.JSON_JADWAL1 + mChosenTrayek.getmIdTrayek()
                + Trayek.JSON_JADWAL2 + mWaktu);


        TextView trayek = findViewById(R.id.trayek);
        trayek.setText(mChosenTrayek.getmNama());

        TextView tanggal = findViewById(R.id.tanggal);
        tanggal.setText(TrayekRecycleAdapter.mTanggal);

        TextView jam = findViewById(R.id.jam);
        jam.setText(mWaktu);

        TextView kode = findViewById(R.id.kode);
        mKode = Randoms.Integer(0, 999999999);
        kode.setText(mask(generateKode(mKode)));

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
        final EditText nomorKtp = findViewById(R.id.nomor_ktp);




        final EditText nomorTelp = findViewById(R.id.telp);

        final MaskedTextChangedListener telpListener = new MaskedTextChangedListener(
                "+62[000]-[0000]-[00000]",
                true,
                nomorTelp,
                null,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                        Log.d(MainActivity.class.getSimpleName(), extractedValue);
                        Log.d(MainActivity.class.getSimpleName(), String.valueOf(maskFilled));
                    }
                }
        );

        nomorTelp.addTextChangedListener(telpListener);
        nomorTelp.setOnFocusChangeListener(telpListener);


        final EditText nomorRek = findViewById(R.id.rekening);
        final MaskedTextChangedListener rekListener = new MaskedTextChangedListener(
                "[0000]-[0000]-[00000]",
                true,
                nomorRek,
                null,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
                        Log.d(MainActivity.class.getSimpleName(), extractedValue);
                        Log.d(MainActivity.class.getSimpleName(), String.valueOf(maskFilled));
                    }
                }
        );

        nomorRek.addTextChangedListener(rekListener);
        nomorRek.setOnFocusChangeListener(rekListener);


        mPesan = findViewById(R.id.pesan);
        mPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNama = nama.getText().toString();
                mKtp = unmask(nomorKtp.getText().toString());
                mTelp = unmask(nomorTelp.getText().toString());
                mRekening = unmask(nomorRek.getText().toString());

                if (!(mNama.isEmpty() || mKtp.length() != 16
                        || mTelp.length() < 14 || mRekening.length() < 8)) {

                    new TransaksiAsyncTask().execute(Trayek.BASE_PATH + Trayek.JSON_TRANSAKSI);

                } else {
                    Toast.makeText(getBaseContext(), "Masih ada field yang belum valid...", Toast.LENGTH_SHORT).show();
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

    public static String unmask(String input) {

        String hasil = "";
        String[] splits = input.split("-");
        for (String splitsNow : splits) {

            hasil += splitsNow;

        }

        return hasil;
    }

    @Override
    public void onBackPressed() {
        if (mBack)
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Tekan back sekali lagi untuk kembali", Toast.LENGTH_SHORT).show();
        mBack = true;


    }


    private class JadwalAsyncTask extends AsyncTask<String, Void, String> {


        public JadwalAsyncTask() {
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
                    mJadwal = object.getInt("id_jadwal");
                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            mPesan.setVisibility(View.VISIBLE);
        }

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

    private class TransaksiAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {

                boolean gagal = QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[0]), createJsonMessage()).equals("ada yang salah");

                while (gagal) {
                    gagal = QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[0]), createJsonMessage()).equals("ada yang salah");
                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error when post with http", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            startActivity(new Intent(new Intent(getBaseContext(), CaraBayarActivity.class)));
            Toast.makeText(getBaseContext(), "Pesanan Anda telah dikirim", Toast.LENGTH_SHORT).show();
        }


        private String createJsonMessage() {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.accumulate("id", generateKode(mKode));
                jsonObject.accumulate("nama", mNama);
                jsonObject.accumulate("ktp", mKtp);
                jsonObject.accumulate("telp", mTelp);
                jsonObject.accumulate("rek", mRekening);
                jsonObject.accumulate("jadwal", mJadwal);
                jsonObject.accumulate("tanggal",
                        sqlFormatDate(new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("in", "ID")),
                                TrayekRecycleAdapter.mTanggal
                        ));
                jsonObject.accumulate("kursi", mDiambil);


            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error when create JSON message", e);
            }

            return jsonObject.toString();

        }

    }

    public static String generateKode(int kode) {

        String hasil = "";

        if (kode < 1000000000 && kode > 99999999)
            hasil += kode;
        else if (kode < 100000000 && kode > 9999999)
            hasil += "0" + kode;
        else if (kode < 10000000 && kode > 999999)
            hasil += "00" + kode;
        else if (kode < 1000000 && kode > 99999)
            hasil += "000" + kode;
        else if (kode < 100000 && kode > 9999)
            hasil += "0000" + kode;
        else if (kode < 10000 && kode > 999)
            hasil += "00000" + kode;
        else if (kode < 1000 && kode > 99)
            hasil += "000000" + kode;
        else if (kode < 100 && kode > 9)
            hasil += "0000000" + kode;
        else
            hasil += "00000000" + kode;


        return hasil;
    }

    public static String mask(String input) {
        final Mask mask = new Mask("[000]-[000]-[000]");
        final Mask.Result result = mask.apply(
                new CaretString(
                        input,
                        input.length()
                ),
                false
        );

        return result.getFormattedText().getString();
    }


}

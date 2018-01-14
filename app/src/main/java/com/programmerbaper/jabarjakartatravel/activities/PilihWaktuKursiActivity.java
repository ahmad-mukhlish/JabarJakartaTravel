package com.programmerbaper.jabarjakartatravel.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.entities.Waktu;
import com.programmerbaper.jabarjakartatravel.networking.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PilihWaktuKursiActivity extends AppCompatActivity {

    private Trayek mChosenTrayek;
    private Waktu[] mWaktus;
    private String LOG_TAG = PilihWaktuKursiActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_waktu_kursi);

        binding();

        Bundle bundle = getIntent().getExtras();
        mChosenTrayek = bundle.getParcelable("trayek");


        new WaktuAsyncTask(this).execute(Trayek.BASE_PATH + Trayek.JSON_WAKTU + mChosenTrayek.getmIdTrayek());

        setTitle("Waktu Keberangkatan");
    }


    private class WaktuAsyncTask extends AsyncTask<String, Void, String> {


        private Context context;

        public WaktuAsyncTask(Context context) {
            this.context = context;
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

                    String waktu = object.getString("waktu");
                    int kursi_tersedia = object.getInt("kursi_tersedia");

                    mWaktus[i].setWaktu(waktu);
                    mWaktus[i].setJumlah_kursi(kursi_tersedia);

                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {


            for (Waktu waktuNow : mWaktus) {

                waktuNow.getProgressBar().setVisibility(View.GONE);
                waktuNow.getTxtKursi().setVisibility(View.VISIBLE);
                waktuNow.getTxtKursi().setText(waktuNow.getJumlah_kursi() + " Kursi Tersedia");
                waktuNow.getTxtTulisan().setText(waktuNow.getWaktu());
                waktuNow.getCardView().setOnClickListener(new CardListener(waktuNow, context));

            }
        }

    }

    private void binding() {

        mWaktus = null;
        mWaktus = new Waktu[4];

        mWaktus[0] = new Waktu((ProgressBar) findViewById(R.id.loading0),
                (CardView) findViewById(R.id.card0),
                (TextView) findViewById(R.id.kursi0), (TextView) findViewById(R.id.tulisan0));

        mWaktus[1] = new Waktu((ProgressBar) findViewById(R.id.loading1),
                (CardView) findViewById(R.id.card1),
                (TextView) findViewById(R.id.kursi1), (TextView) findViewById(R.id.tulisan1));

        mWaktus[2] = new Waktu((ProgressBar) findViewById(R.id.loading2),
                (CardView) findViewById(R.id.card2),
                (TextView) findViewById(R.id.kursi2), (TextView) findViewById(R.id.tulisan2));

        mWaktus[3] = new Waktu((ProgressBar) findViewById(R.id.loading3),
                (CardView) findViewById(R.id.card3),
                (TextView) findViewById(R.id.kursi3), (TextView) findViewById(R.id.tulisan3));


    }

    private class CardListener implements View.OnClickListener {

        private Waktu mWaktu;
        private Context mContext;

        CardListener(Waktu mWaktu, Context mContext) {
            this.mWaktu = mWaktu;
            this.mContext = mContext;
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, IsiDataActivity.class);
            intent.putExtra("trayek", mChosenTrayek);
            intent.putExtra("waktu", mWaktu.getWaktu());
            intent.putExtra("kursi", mWaktu.getJumlah_kursi());
            mContext.startActivity(intent);

        }
    }


}






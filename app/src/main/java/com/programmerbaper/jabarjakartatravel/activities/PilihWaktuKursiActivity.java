package com.programmerbaper.jabarjakartatravel.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.networking.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PilihWaktuKursiActivity extends AppCompatActivity {

    private int mIdTrayek;
    private String LOG_TAG = PilihWaktuKursiActivity.class.getName();
    private int[] mKursi = new int[4];
    private ProgressBar mLoading6, mLoading10, mLoading2, mLoading630;
    private TextView mKursi6, mKursi10, mKursi2, mKursi630;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_waktu_kursi);

        binding();

        Bundle bundle = getIntent().getExtras();
        mIdTrayek = bundle.getInt("idTrayek");
        Log.v("cik", mIdTrayek + "");
        new WaktuAsyncTask(this).execute(Trayek.BASE_PATH + Trayek.JSON_WAKTU + mIdTrayek);

        setTitle("Waktu Keberangkatan");
    }


    private class WaktuAsyncTask extends AsyncTask<String, Void, String> {


        private Context mContext;

        WaktuAsyncTask(Context mContext) {
            this.mContext = mContext;
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
                    mKursi[i] = object.getInt("kursi_tersedia");
                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {

            mLoading2.setVisibility(View.GONE);
            mLoading10.setVisibility(View.GONE);
            mLoading6.setVisibility(View.GONE);
            mLoading630.setVisibility(View.GONE);

            mKursi6.setVisibility(View.VISIBLE);
            mKursi6.setText(mKursi[0] + " Kursi Tersedia");


            mKursi10.setVisibility(View.VISIBLE);
            mKursi10.setText(mKursi[1] + " Kursi Tersedia");


            mKursi2.setVisibility(View.VISIBLE);
            mKursi2.setText(mKursi[2] + " Kursi Tersedia");


            mKursi630.setVisibility(View.VISIBLE);
            mKursi630.setText(mKursi[3] + " Kursi Tersedia");


        }

    }

    private void binding() {

        mLoading2 = findViewById(R.id.loading2);
        mLoading10 = findViewById(R.id.loading10);
        mLoading6 = findViewById(R.id.loading6);
        mLoading630 = findViewById(R.id.loading630);

        mKursi2 = findViewById(R.id.kursi2);
        mKursi10 = findViewById(R.id.kursi10);
        mKursi6 = findViewById(R.id.kursi6);
        mKursi630 = findViewById(R.id.kursi630);
    }

}

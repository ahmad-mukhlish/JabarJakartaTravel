package com.programmerbaper.jabarjakartatravel.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.TiketRecycleAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Tiket;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.networking.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TiketSayaActivity extends AppCompatActivity {

    private ArrayList<Tiket> mTikets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_saya);
        new TiketAsyncTask().execute(Trayek.BASE_PATH + Trayek.GET_TIKET + LoginActivity.user.getId() + Trayek.GET_TIKET_2);
    }


    private void updateUI(List<Tiket> tikets) {

        ProgressBar mProgress = findViewById(R.id.progressBar);
        mProgress.setVisibility(View.GONE);

        if (!tikets.isEmpty()) {
            TiketRecycleAdapter tiketRecycleAdapter =
                    new TiketRecycleAdapter(this, tikets);
            RecyclerView recyclerView = findViewById(R.id.rvTickets);
            recyclerView.setVisibility(View.VISIBLE);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(tiketRecycleAdapter);
        } else {
            TextView textView = findViewById(R.id.no_ticket);
            textView.setVisibility(View.VISIBLE);
        }


        setTitle("TIket " + LoginActivity.user.getName());

    }

    private class TiketAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            mTikets = new ArrayList<>();

            try {
                JSONObject root = new JSONObject(QueryUtils.fetchResponse(urls[0]));
                JSONArray data = root.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject tiketNow = data.getJSONObject(i);
                    Tiket tiket = new Tiket(tiketNow.getString("name"),
                            tiketNow.getInt("price"), tiketNow.getInt("detail_amount"),
                            tiketNow.getInt("transaction_secret"),
                            tiketNow.getString("ticket_token"), tiketNow.getString("departure_at"),
                            tiketNow.getInt("detail_status"));
                    mTikets.add(tiket);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String response) {

            updateUI(mTikets);

        }


    }


}


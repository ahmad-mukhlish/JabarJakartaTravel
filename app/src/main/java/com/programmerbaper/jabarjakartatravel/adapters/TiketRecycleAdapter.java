package com.programmerbaper.jabarjakartatravel.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.activities.CaraBayarActivity;
import com.programmerbaper.jabarjakartatravel.entities.Tiket;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ahmad on 1/12/2018.
 */

public class TiketRecycleAdapter extends RecyclerView.Adapter<TiketRecycleAdapter.TrayekViewHolder> {

    private final String LOG_TAG = TrayekRecycleAdapter.class.getName();

    private Context mContext;
    private List<Tiket> mTikets;
    public static String mTanggal;

    public TiketRecycleAdapter(Context mContext, List<Tiket> mTrayeks) {
        this.mContext = mContext;
        this.mTikets = mTrayeks;
    }

    @Override
    public TrayekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_ticket, parent, false);
        return new TrayekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrayekViewHolder holder, int position) {
        Tiket tiketNow = mTikets.get(position);
        holder.mNama.setText(tiketNow.getNama());

        DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(tiketNow.getTanggal());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mTanggal = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", new Locale("in", "ID")).
                format(date);
        holder.mTanggal.setText(mTanggal);

        holder.mTarif.setText(Trayek.formatter((tiketNow.getHarga() * tiketNow.getJumlah() + tiketNow.getKode()) + ""));
        if (tiketNow.getStatus() == 0) {
            holder.mStatus.setText("Belum lunas");
        } else {
            holder.mStatus.setText("Lunas");
            holder.mStatus.setTextColor(Color.parseColor("#303f9f"));
        }

        holder.mRootView.setOnClickListener(new DetailListener(position));

    }

    @Override
    public int getItemCount() {
        return mTikets.size();
    }

    public static class TrayekViewHolder extends RecyclerView.ViewHolder {
        private TextView mNama, mTarif, mTanggal, mStatus;
        private View mRootView;

        public TrayekViewHolder(View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.jam6);
            mTarif = itemView.findViewById(R.id.tarif);
            mTanggal = itemView.findViewById(R.id.tanggal);
            mStatus = itemView.findViewById(R.id.status);
            mRootView = itemView;
        }
    }


    private class DetailListener implements View.OnClickListener {

        private int mPosition;

        DetailListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View view) {

            Tiket clickedTiket = mTikets.get(mPosition);
            if (clickedTiket.getStatus() == 0) {
                Intent intent = new Intent(mContext, CaraBayarActivity.class);
                intent.putExtra("shortcut", false);
                intent.putExtra("kode", clickedTiket.getKode());
                intent.putExtra("harga", clickedTiket.getHarga());
                intent.putExtra("jumlah", clickedTiket.getJumlah());
                view.getContext().startActivity(intent);
            } else {

            }

        }
    }


}

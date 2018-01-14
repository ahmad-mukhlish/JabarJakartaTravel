package com.programmerbaper.jabarjakartatravel.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.activities.PilihWaktuKursiActivity;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ahmad on 1/12/2018.
 */

public class TrayekRecycleAdapter extends RecyclerView.Adapter<TrayekRecycleAdapter.TrayekViewHolder> {

    private final String LOG_TAG = TrayekRecycleAdapter.class.getName();

    private Context mContext;
    private List<Trayek> mTrayeks;

    public TrayekRecycleAdapter(Context mContext, List<Trayek> mTrayeks) {
        this.mContext = mContext;
        this.mTrayeks = mTrayeks;
    }

    @Override
    public TrayekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_trayek, parent, false);
        return new TrayekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrayekViewHolder holder, int position) {
        Trayek trayekNow = mTrayeks.get(position);
        holder.mNama.setText(trayekNow.getmNama());
        holder.mTanggal.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("in", "ID")).format(DateUtil(Calendar.getInstance().getTime(),1)));
        holder.mTarif.setText(Trayek.formatter(trayekNow.getmTarif()+""));
        holder.mRootView.setOnClickListener(new DetailListener(position));

    }

    @Override
    public int getItemCount() {
        return mTrayeks.size();
    }

    public static class TrayekViewHolder extends RecyclerView.ViewHolder {
        private TextView mNama, mTarif, mTanggal;
        private View mRootView;

        public TrayekViewHolder(View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.jam6);
            mTarif = itemView.findViewById(R.id.tarif);
            mTanggal = itemView.findViewById(R.id.tanggal);
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


            Trayek clickedTrayek = mTrayeks.get(mPosition);
            Intent intent = new Intent(mContext, PilihWaktuKursiActivity.class);
            intent.putExtra("idTrayek", clickedTrayek.getmIdTrayek());
            view.getContext().startActivity(intent);

        }
    }

    private Date DateUtil(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


}

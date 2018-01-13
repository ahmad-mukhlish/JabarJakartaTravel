package com.programmerbaper.jabarjakartatravel.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.fragments.TrayekFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmad on 1/13/2018.
 */

public class TrayekTabAdapter extends FragmentStatePagerAdapter {

    private final String LOG_TAG = TrayekTabAdapter.class.getName();

    public static final int TOTAL_FRAGMENT = 2;
    private String[] mTitles;
    private List<Trayek> mLists;

    public TrayekTabAdapter(FragmentManager fm, String[] titles, List<Trayek> List) {
        super(fm);
        this.mTitles = titles;
        this.mLists = List;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new TrayekFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("key", (ArrayList<Trayek>) divideProduksForFragment(mLists, position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return TOTAL_FRAGMENT;
    }

    private List<Trayek> divideProduksForFragment(List<Trayek> all, int kode) {
        List<Trayek> pergi = new ArrayList<>();
        List<Trayek> pulang = new ArrayList<>();


        for (Trayek trayekNow : all) {

            if (trayekNow.getmNama().startsWith("Dago")) {
                pergi.add(trayekNow);
            } else {
                pulang.add(trayekNow);
            }

        }


        if (kode == 0)
            return pergi;
        else
            return pulang;


    }

}

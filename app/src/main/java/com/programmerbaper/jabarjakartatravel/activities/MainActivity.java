package com.programmerbaper.jabarjakartatravel.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.TrayekTabAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.networking.TrayekLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Trayek>> {

    public static List<Trayek> mTrayek;
    private static final int LOADER_ID = 54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);

    }

    private String[] setTabTitle() {
        String titles[] = new String[TrayekTabAdapter.TOTAL_FRAGMENT];
        titles[0] = getString(R.string.tab_pergi);
        titles[1] = getString(R.string.tab_pulang);


        return titles;
    }


    @Override
    public Loader<List<Trayek>> onCreateLoader(int id, Bundle args) {
        if (mTrayek == null) {
            return new TrayekLoader(this, Trayek.BASE_PATH + Trayek.JSON_TRAYEK);
        } else
            return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Trayek>> loader, List<Trayek> data) {

        if (mTrayek == null || mTrayek.isEmpty()) {
            mTrayek = data;
        }

        updateUI(data);


    }

    @Override
    public void onLoaderReset(Loader<List<Trayek>> loader) {

    }

    private void updateUI(List<Trayek> list) {
        ViewPager viewPager = findViewById(R.id.viewpager);
        TrayekTabAdapter adapter = new TrayekTabAdapter(getSupportFragmentManager(), setTabTitle(), list);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


}

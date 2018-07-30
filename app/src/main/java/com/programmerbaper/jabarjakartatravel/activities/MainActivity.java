package com.programmerbaper.jabarjakartatravel.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.TrayekTabAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;
import com.programmerbaper.jabarjakartatravel.networking.TrayekLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Trayek>> {

    public static List<Trayek> mTrayek;
    private static final int LOADER_ID = 54;
    private LinearLayout mLoading;
    private Drawer mDrawer;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrayek = null;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        LinearLayout error = findViewById(R.id.error);
        error.setVisibility(View.GONE);

        mLoading = findViewById(R.id.loading);

        mToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setVisibility(View.GONE);

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            error.setVisibility(View.VISIBLE);
        }


        new DrawerBuilder().withActivity(this).build();
        initNavigationDrawer(savedInstanceState);


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
            return new TrayekLoader(this, Trayek.BASE_PATH + Trayek.GET_TRAYEK);
        } else
            return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Trayek>> loader, final List<Trayek> data) {

        if (mTrayek == null || mTrayek.isEmpty()) {
            mTrayek = data;
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI(data);
            }
        }, 3000);


    }

    @Override
    public void onLoaderReset(Loader<List<Trayek>> loader) {

    }

    private void updateUI(List<Trayek> list) {
        mToolBar.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        ViewPager viewPager = findViewById(R.id.viewpager);
        TrayekTabAdapter adapter = new TrayekTabAdapter(getSupportFragmentManager(), setTabTitle(), list);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void initNavigationDrawer(Bundle savedInstanceState) {

        PrimaryDrawerItem aboutUs = new PrimaryDrawerItem().
                withIdentifier(1).
                withName(R.string.drawer_about_us)
                .withIcon(R.mipmap.about_us);

        PrimaryDrawerItem help = new PrimaryDrawerItem().
                withIdentifier(1).
                withName(R.string.drawer_cara_bayar)
                .withIcon(R.mipmap.help);

        PrimaryDrawerItem ticket = new PrimaryDrawerItem().
                withIdentifier(1).
                withName(R.string.drawer_ticket)
                .withIcon(R.drawable.ic_ticket);

        PrimaryDrawerItem logout = new PrimaryDrawerItem().
                withIdentifier(1).
                withName(R.string.drawer_logout)
                .withIcon(R.drawable.ic_logout);



        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.drawer_header)
                .withDrawerGravity(Gravity.START)
                .withSavedInstance(savedInstanceState)
                .withToolbar(mToolBar)
                .withSelectedItem(-1)
                .addDrawerItems(help, aboutUs, ticket, logout, new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {

                            case 1: {
                                Intent intent = new Intent(MainActivity.this, CaraBayarActivity.class);
                                intent.putExtra("shortcut", true);
                                intent.putExtra("kode", 0);
                                intent.putExtra("harga", 0);
                                intent.putExtra("jumlah", 0);
                                startActivity(intent);
                                break;
                            }

                            case 2: {
                                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                                break;
                            }

                            case 3: {
                                startActivity(new Intent(MainActivity.this, TiketSayaActivity.class));
                                break;
                            }

                            case 4: {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                LoginActivity.user = null ;
                                break;
                            }

                        }

                        return true;
                    }
                })
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTrayek = null;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen())
            mDrawer.closeDrawer();
        else
            Toast.makeText(this, R.string.toast_main_menu, Toast.LENGTH_SHORT).show();
    }




}

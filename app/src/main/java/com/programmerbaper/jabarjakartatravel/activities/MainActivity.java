package com.programmerbaper.jabarjakartatravel.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.TrayekTabAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Trayek> list = new ArrayList<>();
        list.add(new Trayek(1, "Dago - Halim", 180000));
        list.add(new Trayek(1, "Halim Dago", 180000));

        ViewPager viewPager = findViewById(R.id.viewpager);
        TrayekTabAdapter adapter = new TrayekTabAdapter(getSupportFragmentManager(), setTabTitle(), list);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private String[] setTabTitle() {
        String titles[] = new String[TrayekTabAdapter.TOTAL_FRAGMENT];
        titles[0] = getString(R.string.tab_pergi);
        titles[1] = getString(R.string.tab_pulang);


        return titles;
    }


}

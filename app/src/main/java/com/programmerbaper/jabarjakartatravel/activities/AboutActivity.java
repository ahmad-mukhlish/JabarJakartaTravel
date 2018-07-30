package com.programmerbaper.jabarjakartatravel.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.AboutRecycleAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Personil;

import java.util.ArrayList;
import java.util.List;


public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        AboutRecycleAdapter aboutRecycleAdapter =
                new AboutRecycleAdapter(this, initPersonil());

        RecyclerView recyclerView = findViewById(R.id.rvAbout);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(aboutRecycleAdapter);

        setTitle("About Us");
    }

    private List<Personil> initPersonil() {

        List<Personil> personils = new ArrayList<>();

        personils.add(new Personil(R.drawable.raka, "Raka Suryaardi Widjaya", "Web Developer"));
        personils.add(new Personil(R.drawable.mukhlis, "Ahmad Mukhlis Saputra", "Android Developer"));
        personils.add(new Personil(R.drawable.geri, "Geri Fitrah R. R.", "Web Developer"));
        personils.add(new Personil(R.drawable.agung, "Muhamad Agung Gumelar", "Web Developer"));
        personils.add(new Personil(R.drawable.azmi, "Azmi Yudista", "UI/UX Designer"));


        return personils;
    }
}

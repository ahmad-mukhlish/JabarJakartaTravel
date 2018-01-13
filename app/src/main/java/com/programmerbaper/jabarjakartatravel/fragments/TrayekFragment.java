package com.programmerbaper.jabarjakartatravel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.adapters.TrayekRecycleAdapter;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;

import java.util.ArrayList;
import java.util.List;

public class TrayekFragment extends Fragment {
    private final String LOG_TAG = TrayekFragment.class.getName();

    public static RecyclerView recyclerView;

    public TrayekFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trayek, container, false);

        List<Trayek> listTrayek = getArguments().getParcelableArrayList("key");

        TrayekRecycleAdapter trayekRecycleAdapter =
                new TrayekRecycleAdapter(getContext(), listTrayek);

        recyclerView = rootView.findViewById(R.id.rvItems);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(trayekRecycleAdapter);


        return rootView;
    }
}

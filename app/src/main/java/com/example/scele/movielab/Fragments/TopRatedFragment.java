package com.example.scele.movielab.Fragments;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.scele.movielab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedFragment extends PreferenceFragment  {


    public TopRatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_top_rated_up,container,false);
    }

}

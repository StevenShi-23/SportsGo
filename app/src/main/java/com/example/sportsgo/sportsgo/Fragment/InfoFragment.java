package com.example.sportsgo.sportsgo.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportsgo.sportsgo.R;

/**
 * Created by Long on 4/6/2017.
 */

public class InfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.info_fragment, container, false);

        return rootView;
    }
}
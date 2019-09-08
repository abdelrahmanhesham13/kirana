package com.softclads.Gagron.WelcomeFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softclads.Gagron.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondSliderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.slider2, container, false);
        return view;
    }
}

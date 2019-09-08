package com.softclads.Gagron.HomeFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softclads.Gagron.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment {


    public SupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_support, container, false);
        return view;
    }

}

package com.unitins.quadro.quadrodehorarios.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unitins.quadro.quadrodehorarios.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSobre extends Fragment {


    public FragmentSobre() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_sobre, container, false);
        return result;
    }

}

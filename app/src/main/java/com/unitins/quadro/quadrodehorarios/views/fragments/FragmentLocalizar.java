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
public class FragmentLocalizar extends Fragment {


    public FragmentLocalizar() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_localizar, container, false);
        return result;
    }

}

/*

    <fragment
        android:id="@+id/mapa_fragmapa"
        android:name="com.google.android.gms.maps.MapFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@+id/mapa_select"
        android:layout_marginTop="10dp" />


 */

package com.unitins.quadro.quadrodehorarios.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unitins.quadro.quadrodehorarios.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSair extends Fragment implements View.OnClickListener{

    private Button sair = null;


    public FragmentSair() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_sair, container, false);

        sair = (Button) result.findViewById(R.id.sair_btnsair);
        sair.setOnClickListener(this);
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sair_btnsair:
                this.sair();
                break;
        }
    }

    private void sair(){
        //sai do aplicativo
        this.getActivity().finish();
        System.exit(0);
    }
}

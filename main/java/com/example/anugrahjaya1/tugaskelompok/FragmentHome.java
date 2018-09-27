package com.example.anugrahjaya1.tugaskelompok;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    private TextView tvHello;
    private MainActivity activity;

    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance(MainActivity activity){
        FragmentHome result = new FragmentHome();
        result.activity = activity ;
        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        this.tvHello = result.findViewById(R.id.tvHello);
        this.tvHello.setText("TES HELLO");
        return result;

    }

}

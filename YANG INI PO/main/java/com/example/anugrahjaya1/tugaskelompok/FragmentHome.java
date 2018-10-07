package com.example.anugrahjaya1.tugaskelompok;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    private MainActivity activity;

    protected ListView lst_Score;
    protected EditText et_Nama;
    protected AdapterScore adapterScore;

    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance(MainActivity activity){
        FragmentHome result = new FragmentHome();
        result.activity = activity;
        result.adapterScore = new AdapterScore(result);
        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        this.lst_Score = result.findViewById(R.id.lst_Score);
        this.lst_Score.setAdapter(adapterScore);

        this.et_Nama = result.findViewById(R.id.etNama);
        return result;

    }

}
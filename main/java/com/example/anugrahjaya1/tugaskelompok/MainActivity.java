package com.example.anugrahjaya1.tugaskelompok;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    protected FragmentGame fragmentGame;
    protected FragmentHome fragmentHome ;

    protected FragmentManager fragmentManager;

    protected Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnStart = this.findViewById(R.id.startGame);
        this.btnStart.setOnClickListener(this);

        this.fragmentGame = FragmentGame.newInstance(this);
        this.fragmentHome = FragmentHome.newInstance(this);

        this.fragmentManager = this.getSupportFragmentManager() ;

        FragmentTransaction fragmentTransaction  = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer , fragmentHome);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(this.btnStart == v){
            if(this.btnStart.getText().toString().equals("START")){
                FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer , fragmentGame);
                fragmentTransaction.commit();

                this.btnStart.setText("FINISH");
            }else{
                FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer , fragmentHome);
                fragmentTransaction.commit();

                this.btnStart.setText("START");
            }
        }
    }
}

package com.example.teukuhashrul.tugaskelompok;

import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    protected FragmentGame fragmentGame;
    protected FragmentHome fragmentHome ;

    protected static final long START_TIME=60000;

    protected TextView tv_timer;

    protected CountDownTimer timer;
    private long timeLeft=START_TIME;

    protected FragmentManager fragmentManager;

    protected Button btnStart;
    
    protected boolean inGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnStart = this.findViewById(R.id.startGame);
        this.btnStart.setOnClickListener(this);
        this.tv_timer=this.findViewById(R.id.timer);

        this.fragmentGame = FragmentGame.newInstance(this);
        this.fragmentHome = FragmentHome.newInstance(this);

        this.fragmentManager = this.getSupportFragmentManager() ;
        
        this.inGame=false;

        FragmentTransaction fragmentTransaction  = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer , fragmentHome);
        fragmentTransaction.commit();
        updateCountDownText();
        this.tv_timer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(this.btnStart == v){

            if(!this.inGame){
                Log.d("masuk" , "ulala");
                FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer , fragmentGame);
                fragmentTransaction.commit();

                this.btnStart.setText("FINISH");
                this.inGame=!this.inGame;
//                this.startTimer();
//                this.tv_timer.setVisibility(View.VISIBLE);

            }else{
                FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer , fragmentHome);
                fragmentTransaction.commit();

                this.btnStart.setText("START");
                this.inGame=!this.inGame;
                this.resetTimer();
            }
        }
    }

    public void startTimer(){
        timer=new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timer.cancel();
                //btnStart.setText("Start");
                timeLeft=START_TIME;
                fragmentGame.setIsPlay(false);
                Log.d("isPlay" , fragmentGame.getIsPlay()+"");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer , fragmentHome );
                fragmentTransaction.commit();

                btnStart.setText(R.string.btn_start);
                tv_timer.setVisibility(View.INVISIBLE);
            }
        }.start();
        //btnStart.setText("Finish");
    }
    public void resetTimer(){
        timeLeft=START_TIME;
        timer.cancel();
        //btnStart.setText("Start");
        updateCountDownText();
    }
    private void updateCountDownText(){
        int minutes=(int) (timeLeft/1000)/60;
        int seconds=(int) (timeLeft/1000)%60;

        String timeLeftFormat=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        this.tv_timer.setText(timeLeftFormat);
    }

    private void backToStartFragment(){

    }
}

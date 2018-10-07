package com.example.anugrahjaya1.tugaskelompok;

import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class Timer {
    protected CountDownTimer timer;
    protected static final long START_TIME=60000;
    private long timeLeft=START_TIME;
    protected MainActivity activity;


    public Timer(MainActivity activity){
        this.activity=activity;

        //this.timer=new CountDownTimer(timeLeft,1000);
    }

    public void startTimer(){
        this.timer=new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                activity.addPlayerToTheScoreboard();
                timer.cancel();
                //btnStart.setText("Start");
                timeLeft=START_TIME;
                activity.fragmentGame.setIsPlay(!activity.fragmentGame.isPlay);
                //Log.d("isPlay" , fragmentGame.getIsPlay()+"");
                FragmentTransaction fragmentTransaction = activity.fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer , activity.fragmentHome );
                fragmentTransaction.commit();
                activity.setInGame(!activity.isInGame());
                //Log.d("CekInGame",activity.inGame+"");

                activity.btnStart.setText(R.string.btn_start);
                activity.tv_timer.setVisibility(View.INVISIBLE);
            }
        }.start();
        //btnStart.setText("Finish");
    }
    public void resetTimer(){
        timeLeft=START_TIME;
        timer.cancel();
        activity.fragmentGame.setIsPlay(!activity.fragmentGame.isPlay);
        //btnStart.setText("Start");
        updateCountDownText();
    }
    public void updateCountDownText(){
        int minutes=(int) (timeLeft/1000)/60;
        int seconds=(int) (timeLeft/1000)%60;

        String timeLeftFormat=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        activity.tv_timer.setText(timeLeftFormat);
    }

}

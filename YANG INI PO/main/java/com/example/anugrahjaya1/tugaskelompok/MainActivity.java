package com.example.anugrahjaya1.tugaskelompok;

import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    protected FragmentGame fragmentGame;
    protected FragmentHome fragmentHome ;



    protected TextView tv_timer;
    protected TextView tv_HighScore;



    protected FragmentManager fragmentManager;

    protected Button btnStart;

    protected boolean inGame;

    protected Timer timer;

    // Mennyimpan info player yang sedang main NameNow , scoreNow , Highscore
    protected String nameNow , scoreNow ;
    protected int highscore ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisasi nameNow , scoreNow
        this.nameNow = "";
        this.scoreNow = "" ;
        this.highscore = 0 ;

        this.btnStart = this.findViewById(R.id.startGame);
        this.btnStart.setOnClickListener(this);
        this.tv_timer=this.findViewById(R.id.timer);

        this.tv_HighScore = this.findViewById(R.id.tv_HighScore);
        this.tv_HighScore.setText("Highscore : 0");


        this.fragmentGame = FragmentGame.newInstance(this);
        this.fragmentHome = FragmentHome.newInstance(this);

        this.fragmentManager = this.getSupportFragmentManager() ;

        this.inGame=false;

        FragmentTransaction fragmentTransaction  = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer , fragmentHome);
        fragmentTransaction.commit();
//        this.timer=new Timer(this,fragmentGame,fragmentManager,fragmentHome,btnStart,tv_timer);
        this.timer=fragmentGame.timer;
        timer.updateCountDownText();
        this.tv_timer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(this.btnStart == v){

            if(!this.inGame){

                if(TextUtils.isEmpty(this.fragmentHome.et_Nama.getText().toString())){
                   nameNow = "player";
                }else{
                    nameNow = this.fragmentHome.et_Nama.getText().toString();
                }
                FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer , fragmentGame);
                fragmentTransaction.commit();

                this.btnStart.setText("FINISH");
                this.inGame=!this.inGame;
//                this.startTimer();
//                this.tv_timer.setVisibility(View.VISIBLE);

            }else{

                addPlayerToTheScoreboard();

                FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer , fragmentHome);
                fragmentTransaction.commit();

                this.btnStart.setText("START");
                this.inGame=!this.inGame;
                fragmentGame.timer.resetTimer();
                this.tv_timer.setVisibility(View.INVISIBLE);
            }
        }
    }



    public void addPlayerToTheScoreboard(){
        //ambil score setelah selesai main
        scoreNow  = fragmentGame.score.getText().toString();
        int scoreNowInt = Integer.parseInt(scoreNow);
        if(scoreNowInt > highscore){
            highscore = scoreNowInt;
            this.tv_HighScore.setText("Highscore : "+highscore);
        }

        //masukan ke scoreboard(adapter) yang baru selesai main
        fragmentHome.adapterScore.addPlayer(nameNow , scoreNow);


        //kosongkan edit text nama player
        fragmentHome.et_Nama.setText("");

        //set Ulang seperti awal
        nameNow = "";
        scoreNow = "";
    }
    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}

package com.example.anugrahjaya1.tugaskelompok1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{
    protected TextView score;
    protected Button btnAction;
    protected Bitmap bitmap;
    protected ImageView iv;
    protected Canvas canvas;
    protected ArrayList<Rect> arrRect;
    protected Paint myPaint;
    protected Random random;
    protected int s;
    protected GestureDetector myDetector;
    protected CustomListener listener;

    protected boolean isPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.score=this.findViewById(R.id.score);
        this.btnAction=this.findViewById(R.id.btn_start);
        this.iv=this.findViewById(R.id.iv);

        this.myPaint=new Paint();
        this.random=new Random();

        this.isPlay=false;

        this.btnAction.setOnClickListener(this);
        this.iv.setOnTouchListener(this);

        this.listener=new CustomListener();
        this.myDetector=new GestureDetector(this,this.listener);

        this.arrRect=new ArrayList<>();
        this.s=0;
    }

    @Override
    public void onClick(View v) {
        if(v==this.btnAction){
            if(!this.isPlay){
                this.create();
                this.isPlay=!this.isPlay;
                this.btnAction.setText("Finish");
            }else{
                this.isPlay=!this.isPlay;
                this.btnAction.setText("Start");
                this.s=0;
                this.score.setText(s+"");
            }

        }
    }

    public void create(){
        this.bitmap=Bitmap.createBitmap(this.iv.getWidth(),this.iv.getHeight(),Bitmap.Config.ARGB_8888);

        this.iv.setImageBitmap(this.bitmap);

        this.canvas=new Canvas(this.bitmap);

        int mColorBackground= ResourcesCompat.getColor(getResources(),R.color.background_color,null);
        this.canvas.drawColor(mColorBackground);

        // create style n color
        int mColorTest=ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        this.myPaint.setColor(mColorTest);

        this.generateRect();


    }

    private void generateRect(){
        int left  = 0 , top = 0 , right = 0 , bottom = 0 ;
        for(int i=0;i<5;i++){

            boolean cek=false;
            Rect newR = new Rect();
            while(!cek){
                 left = generateLeft();
                 top  = generateTop();
                 right = left + 100 ;
                 bottom = top + 100;
                newR = new Rect(left , top , right , bottom);
                if(!checkContainsAll(newR)){
                    cek = !cek;
                }
            }
            Log.d("rect Post" , left+" " + top + " " + right +" " + bottom);
            this.createRect(left,top,right,bottom);



        }

    }

    private void createRect(int left,int top,int right,int bottom){
        Rect rect=new Rect(left,top,right,bottom);
        Log.d("created Rect"  , rect.left + " " + rect.top + " " + rect.right + " " + rect.bottom);
        this.canvas.drawRect(rect,this.myPaint);

        this.arrRect.add(rect);
        this.iv.invalidate();
    }

    public boolean cek(float x,float y){



        for(int i=0;i<this.arrRect.size();i++){
            boolean a=x>=this.arrRect.get(i).left && x<=this.arrRect.get(i).right;
            boolean b=y>=this.arrRect.get(i).top && y<=this.arrRect.get(i).bottom;
            Log.d("koor",x+" "+y);
            if((a && b) && isPlay ){
                //timbpah gambar
                Paint paint=new Paint();
                int mColorTest=ResourcesCompat.getColor(getResources(),R.color.background_color,null);
                paint.setColor(mColorTest);

                int left=this.arrRect.get(i).left;
                int top=this.arrRect.get(i).top;
                int right=this.arrRect.get(i).right;
                int bottom=this.arrRect.get(i).bottom;
                Rect rect=new Rect(left,top,right,bottom);//top y bottom x

                canvas.drawRect(rect,paint);
                this.iv.invalidate();
                //hapus dari arr
                this.arrRect.remove(i);

//                boolean cek=false;
//                Rect newR = null;
//                while(!cek){
//                    left = generateLeft();
//                    top  = generateTop();
//                    right = left + 100 ;
//                    bottom = top + 100;
//                    newR = new Rect(left , top , right , bottom);
//                    if(!checkContainsAll(newR)){
//                        cek = !cek;
//                    }
//                }
//                canvas.drawRect(newR,this.myPaint);
//                this.arrRect.add(newR);
//                this.iv.invalidate();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return this.myDetector.onTouchEvent(event);
    }

    private class CustomListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            float x=e.getX();
            float y=e.getY();
            Log.d("onDown" , x + " " + y);
            if(cek(x,y)  && isPlay ){
                Log.d("tes tekan","PAS");
                Paint paint=new Paint();
                    int mColorTest=ResourcesCompat.getColor(getResources(),R.color.background_color,null);
                    paint.setColor(mColorTest);

//                    int left=10+random.nextInt(iv.getWidth());
//                    int top=10+random.nextInt(iv.getHeight());
//                    int right=left+100;
//                    int bottom=top+100;
//                    createRect(left,top,right,bottom);


                    s+=10;
                    score.setText(s+"");
            }

            return true;
        }
        

    }


    private int generateLeft(){
        Random r =  new Random();
        int left = 10 + r.nextInt(canvas.getWidth());
        while(left + 100 >= canvas.getWidth()){
            left =10+ r.nextInt(canvas.getWidth());
        }
        return left;
    }

    private int generateTop(){
        Random r =  new Random();
        int top =10+ r.nextInt(canvas.getHeight());
        while(top + 100 >= canvas.getHeight()){
            top =10+ r.nextInt(canvas.getHeight());
        }
        return top;
    }

    /**
     * Method buat cek apakah rect yang baru mau dimasukin
     * nabrak
     * @param newR
     * @return true jika newRect nabrak dengan kumpulan rect yang ada di arrRect
     */
    private boolean checkContainsAll(Rect newR){
        for (int i = 0; i < arrRect.size(); i++) {
            if(arrRect.get(i).intersect(newR)){
                return true;
            }
        }
        return false;
    }



}

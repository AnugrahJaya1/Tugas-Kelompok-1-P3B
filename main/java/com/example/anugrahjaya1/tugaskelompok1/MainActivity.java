package com.example.anugrahjaya1.tugaskelompok1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
            }

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                int x=(int)event.getX();
                int y=(int)event.getY();
                if(this.cek(x,y) && this.isPlay){
//                    Paint paint=new Paint();
//                    int mColorTest=ResourcesCompat.getColor(getResources(),R.color.background_color,null);
//                    paint.setColor(mColorTest);
//
//                    Rect rect=new Rect(10,10,100,100);//top y bottom x
//
//                    canvas.drawRect(rect,paint);
                    this.s+=10;
                    this.score.setText(s+"");
                }
                Log.d("touch_listener",x +" "+y);
                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                Log.d("touch_listener","pointer_down");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d("touch_listener","up");
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                Log.d("touch_listener","pointer_up");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d("touch_listener","move");
//                break;
        }

        return true;

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
        for(int i=0;i<5;i++){
            int leftTop=10+this.random.nextInt(301);
            int bottomRight=100+this.random.nextInt(801);
//            if(this.arrRect.size()==0){
                this.createRect(leftTop,bottomRight);
//            }else{
//                int j=0;
//                while(j<this.arrRect.size()-1){
//                    if(a>this.arrRect.get(j) && b>this.arrRect.get(j+1)){
//                        this.createRect(a,b);
//                        j+=2;
//                    }else {
//                        a=10+this.random.nextInt(101);
//                        b=100+this.random.nextInt(501);
//                    }
//                }
//            }
        }

    }

    private void createRect(int a,int b){
        Rect rect=new Rect(a,a,b,b);
        this.canvas.drawRect(rect,this.myPaint);
        this.arrRect.add(rect);
        this.iv.invalidate();
    }

    public boolean cek(int x,int y){
        for(int i=0;i<this.arrRect.size()-1;i++){
            if(x>this.arrRect.get(i).left && y<this.arrRect.get(i).right){
                //timbpah gambar
                Paint paint=new Paint();
                int mColorTest=ResourcesCompat.getColor(getResources(),R.color.background_color,null);
                paint.setColor(mColorTest);

                int x1=this.arrRect.get(i).left;
                int y1=this.arrRect.get(i).right;
                Rect rect=new Rect(x1,x1,y1,y1);//top y bottom x

                canvas.drawRect(rect,paint);
                //hapus dari arr
                this.arrRect.remove(i);

                //random baru
                int a=10+this.random.nextInt(( iv.getWidth()));
                int b=100+this.random.nextInt(iv.getHeight());
                rect=new Rect(a,a,b,b);
                canvas.drawRect(rect,this.myPaint);
                this.arrRect.add(rect);
                this.iv.invalidate();
                return true;
            }
        }
        return false;
    }

}

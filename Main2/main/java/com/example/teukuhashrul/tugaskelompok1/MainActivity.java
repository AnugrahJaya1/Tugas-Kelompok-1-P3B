package com.example.teukuhashrul.tugaskelompok1;

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

import com.example.teukuhashrul.tugaskelompok.R;

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
    }

    @Override
    public void onClick(View v) {
        if(v==this.btnAction){
            if(!this.isPlay){
                this.btnAction.setText("Finish");
                this.s=0;
                this.score.setText(s+"");

                this.arrRect=new ArrayList<>();
                this.create();
                this.isPlay=!this.isPlay;
            }else{
                this.isPlay=!this.isPlay;
                this.btnAction.setText("Start");
            }
        }
    }

    /**
     * create Bitmat
     * setBitmap to ImageView
     * create Canvas + set backgroud
     * panggil generateRect
     */
    public void create(){
        this.bitmap=Bitmap.createBitmap(this.iv.getWidth(),this.iv.getHeight(),Bitmap.Config.ARGB_8888);

        Log.d("debug",this.iv.getWidth()+" "+this.iv.getHeight());

        this.iv.setImageBitmap(this.bitmap);

        this.canvas=new Canvas(this.bitmap);

        int mColorBackground= ResourcesCompat.getColor(getResources(),R.color.background_color,null);
        this.canvas.drawColor(mColorBackground);

        // create style n color
        int mColorTest=ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        this.myPaint.setColor(mColorTest);
        this.iv.invalidate();

        this.generateRect();
    }

    /**
     * looping sebanyak 5x
     * panggil cekKoor
     */
    private void generateRect(){
        for(int i=0;i<5;i++){
            this.cekKoor();
        }
    }

    /**
     * random posisi baru,kalau bisa panggil createRect
     */
    private void cekKoor(){
        int left  = 0 , top = 0 , right = 0 , bottom = 0 ;
        boolean cek=false;
        Rect newR = null;

        while(!cek){
            left = generateLeft();
            top  = generateTop();
            right = left + 100 ;
            bottom = top + 100;
            newR = new Rect(left , top , right , bottom);
            if(!checkContainsAll(newR)){
                cek = !cek;
                this.createRect(newR);
            }
            Log.d("rect Post" , left+" " + top + " " + right +" " + bottom);
        }
    }

    /**
     * gambar rect pake canvas
     * @param newR rect yang di gambar
     * invalidate
     * masukin ke arrayList
     */
    private void createRect(Rect newR){
        this.canvas.drawRect(newR,this.myPaint);
        this.iv.invalidate();
        this.arrRect.add(newR);
    }

    /**
     * cek bagaian yang di sentuh ada di dalem rect atau engga
     * jika benar hapus rect yang di tekan dari arrayList
     * @param x posisi jari di koor x
     * @param y posisi jari di koor y
     * @return true jika benar,false jika salah
     */
    public boolean cek(float x,float y){
        Log.d("debug",x +" "+y);
        for(int i=0;i<this.arrRect.size();i++){
            boolean a=x>=this.arrRect.get(i).left && x<=this.arrRect.get(i).right;
            boolean b=y>=this.arrRect.get(i).top && y<=this.arrRect.get(i).bottom;
            if(a && b){
                this.arrRect.remove(i);

                this.resetCanvas();

                this.reDraw();

                this.cekKoor();

                return true;
            }
        }
        return false;
    }

    /**
     * reset canvas jadi putih
     */
    private void resetCanvas(){
        this.canvas=new Canvas(this.bitmap);

        int mColorBackground= ResourcesCompat.getColor(getResources(),R.color.background_color,null);
        this.canvas.drawColor(mColorBackground);
        this.iv.invalidate();
    }

    /**
     * gambar ulang rect yang ada di dalam arrayList
     */
    private void reDraw(){
        for(int i=0;i<this.arrRect.size();i++){
            this.canvas.drawRect(this.arrRect.get(i),this.myPaint);
            this.iv.invalidate();
        }
    }

    /**
     * generate Koor left untuk rect
     * @return koor left
     */
    private int generateLeft(){
        Random r =  new Random();
        int left = 10 + r.nextInt(canvas.getWidth());
        while(left + 100 >= canvas.getWidth()){
            left =10+ r.nextInt(canvas.getWidth());
        }
        return left;
    }

    /**
     * generate Koor top untuk rect
     * @return koor top
     */
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
        for (int i = 0; i < this.arrRect.size(); i++) {
            if(arrRect.get(i).intersect(newR)){
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
            Log.d("debug" , x + " " + y);
            if(cek(x,y)  && isPlay ){
                Log.d("tes tekan","PAS");
                s+=10;
                score.setText(s+"");
            }
            return true;
        }
    }
}

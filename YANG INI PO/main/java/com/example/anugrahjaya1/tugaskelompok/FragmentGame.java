package com.example.anugrahjaya1.tugaskelompok;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGame extends Fragment implements View.OnClickListener , View.OnTouchListener{
    protected ImageView iv;
    protected Canvas canvas;
    protected Button btnAction;
    protected TextView score,target;
    protected ArrayList<Rect> arrRect;
    protected ArrayList<Integer> arrCir;
    protected ArrayList<Integer> arrNumber;
    protected Bitmap bitmap;
    protected Paint myPaint;
    protected Random random ;
    protected int s ;
    protected GestureDetector myDetector ;
    protected CustomListener listener ;

    protected boolean isPlay;

    protected MainActivity activity;
    protected Timer timer;

    protected Kotak kotak;

    public FragmentGame() {
        // Required empty public constructor
    }


    public static FragmentGame newInstance(MainActivity activity){
        FragmentGame result = new FragmentGame();
        result.activity = activity;
        result.timer = new Timer(activity);
//        result.kotak=new Kotak(result);
        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View res=inflater.inflate(R.layout.fragment_fragment_game, null, false);
        // daftarkan imageview , btn Start / Finish , score
        this.iv = res.findViewById(R.id.iv);
        this.btnAction = res.findViewById(R.id.btn_start);
        this.btnAction.setOnClickListener(this);
        this.score = res.findViewById(R.id.score);
        this.target=res.findViewById(R.id.target);

        this.myPaint = new Paint();
        this.random = new Random();
        this.kotak=new Kotak(this);

        this.isPlay = false;

        this.listener = new CustomListener();
        this.myDetector = new GestureDetector(activity , this.listener);


        return res;
    }

    public boolean getIsPlay(){
        return isPlay;
    }
    public void setIsPlay(boolean status){
        this.isPlay=status;
    }

    @Override
    public void onClick(View v) {
        if(v==this.btnAction){
            //Log.d("CekInGame",isPlay+"");
            if(!this.isPlay){
                this.btnAction.setVisibility(View.INVISIBLE);
                //this.btnAction.setText("Finish");
                this.s=0;
                this.score.setText(s+"");

                this.arrRect=kotak.arrRect;
                //this.arrCir=new ArrayList<>();
                this.arrNumber=kotak.arrNumber;
                this.create();
                this.isPlay=!this.isPlay;
                this.iv.setOnTouchListener(this);
                timer.startTimer();
                //Log.d("CekInGame",isPlay+"");
                activity.tv_timer.setVisibility(View.VISIBLE);

                int t=1+this.random.nextInt(5);
                this.target.setText(t+"");
            }else{
                this.isPlay=!this.isPlay;
                this.btnAction.setText("Start");
                timer.resetTimer();
                activity.tv_timer.setVisibility(View.INVISIBLE);
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
//        this.iv.invalidate();

        kotak.generateRect();
        //this.generetCircle();
        this.iv.invalidate();
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

            int t=Integer.parseInt(this.target.getText().toString());
            if((a && b) && (i+1==t)){
                Log.d("tolong","Kepencet"+" "+i);
                this.kotak.setArrayRect(i);

                this.resetCanvas();

                this.reDraw();

                this.iv.invalidate();

                return true;
            }
        }
        return false;
    }

    /**
     * reset canvas jadi putih
     */
    public void resetCanvas(){
        this.canvas=new Canvas(this.bitmap);

        int mColorBackground= ResourcesCompat.getColor(getResources(),R.color.background_color,null);
        this.canvas.drawColor(mColorBackground);
//        this.iv.invalidate();
    }

    /**
     * gambar ulang rect yang ada di dalam arrayList
     */
    public void reDraw(){
        for(int i=0;i<this.arrRect.size();i++){
            this.canvas.drawRect(this.arrRect.get(i),this.myPaint);
            Paint p=new Paint();
            p.setColor(Color.YELLOW);
            p.setTextSize(75);
            this.canvas.drawText(this.arrNumber.get(i)+"",this.arrRect.get(i).left+30,this.arrRect.get(i).top+60,p);

        }
    }
    
    /**
//     * metod untuk menutupi rect dengan warna putih
//     * @param left koor left rect
//     * @param top koor top rect
//     * @param rigth koor rigth rect
//     * @param bottom koor bottom rect
//     */
//    private void removeRect(int left,int top,int rigth,int bottom){
//        Rect r=new Rect(left,top,rigth,bottom);
//        Paint p=new Paint();
//        int c= ResourcesCompat.getColor(getResources(),R.color.background_color,null);
//        p.setColor(c);
//        this.canvas.drawRect(r,p);
//    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return this.myDetector.onTouchEvent(event);
    }

    private class CustomListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            if(isPlay){
                float x=e.getX();
                float y=e.getY();
                Log.d("debug" , x + " " + y);
                if(cek(x,y)){
                    Log.d("tes tekan","PAS");
                    s+=10;
                    score.setText(s+"");
                    int t=1+random.nextInt(5);
                    target.setText(t+"");
                }
            }
            return true;
        }
    }
}
//
//    private void generetCircle(){
//        for(int i=0;i<5;i++){
//            this.cekKoorC();
//        }
//    }
//
//    private void cekKoorC(){
//        int x=0,y=0,t1=0,t2=0;
//        boolean cek=false;
//
//        while(true) {
//            x=this.generateX();
//            y=this.generateY();
//            t1=x+100;
//            t2=y+100;
//            Rect r=new Rect(x,y,t1,t2);
//            if(!checkContainsAll(r)){
//                //this.createCircle(x+50,y+50);
//                while(this.checkPosition(x,y)){
//                    //createCircle
//                    this.createCircle(x,y);
//                    break;
//                }
//                cek=true;
//                //break;
//            }
//            if(cek==true){
//                break;
//            }else{
////                while(this.checkPosition(x,y)){
////                    //createCircle
////                    this.createCircle(x,y);
////                    break;
////                }
////                break;
//            }
//        }
//    }
//
//    private int generateX(){
//        int x=10+this.random.nextInt(this.canvas.getWidth());
//        while(x+100>this.canvas.getWidth()){
//            x=10+random.nextInt(this.canvas.getWidth());
//        }
//        return x;
//    }
//
//    private int generateY(){
//        int y=10+this.random.nextInt(this.canvas.getHeight());
//        while(y+100>this.canvas.getWidth()){
//            y=10+random.nextInt(this.canvas.getHeight());
//        }
//        return y;
//    }
//
//    private boolean checkPosition(int x,int y){
//        for(int i=0;i<this.arrCir.size()-2;i+=2){
//            int btsXMin=this.arrCir.get(i)-50;
//            int btsXMax=this.arrCir.get(i)+50;
//
//            int btsYMin=this.arrCir.get(i+1)-50;
//            int btsYMax=this.arrCir.get(i+1)+50;
//
//            boolean a= x>=btsXMin && x<=btsXMax;
//            boolean b= y>=btsYMin && y<=btsYMax;
//            if(a && b){
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void createCircle(int x,int y){
//        int color=ResourcesCompat.getColor(getResources(),R.color.colorAccent,null);
//        Paint p=new Paint();
//        p.setColor(color);
//
//        this.canvas.drawCircle(x,y,50,p);
//        this.arrCir.add(x);
//        this.arrCir.add(y);
//    }

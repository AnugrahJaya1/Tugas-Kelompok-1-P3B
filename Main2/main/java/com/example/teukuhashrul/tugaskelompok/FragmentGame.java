package com.example.teukuhashrul.tugaskelompok;


import android.graphics.Bitmap;
import android.graphics.Canvas;
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
    protected TextView score;
    protected ArrayList<Rect> arrRect;
    protected Bitmap bitmap;
    protected Paint myPaint;
    protected Random random ;
    protected int s ;
    protected GestureDetector myDetector ;
    protected CustomListener listener ;

    protected boolean isPlay;

    protected MainActivity activity;

    public FragmentGame() {
        // Required empty public constructor
    }


    public static FragmentGame newInstance(MainActivity activity){
        FragmentGame result = new FragmentGame();
        result.activity = activity;
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

        this.myPaint = new Paint();
        this.random = new Random();

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
            if(!this.isPlay){
                this.btnAction.setText("Finish");
                this.s=0;
                this.score.setText(s+"");

                this.arrRect=new ArrayList<>();
                this.create();
                this.isPlay=!this.isPlay;
                this.iv.setOnTouchListener(this);
                activity.startTimer();
                activity.tv_timer.setVisibility(View.VISIBLE);
            }else{
                this.isPlay=!this.isPlay;
                this.btnAction.setText("Start");
                activity.resetTimer();
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

        this.generateRect();
        this.generetCircle();
        this.iv.invalidate();
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
                Log.d("ceking_R",newR.left+" "+newR.top+" "+newR.right+" "+newR.bottom);
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
        Log.d("debug_rect","Create rect terpanggil");
        this.canvas.drawRect(newR,this.myPaint);
//        this.iv.invalidate();
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
                Log.d("tolong","Kepencet"+" "+i);
                int l=this.arrRect.get(i).left;
                int t=this.arrRect.get(i).top;
                int r=this.arrRect.get(i).right;
                int bt=this.arrRect.get(i).bottom;

                this.removeRect(l,t,r,bt);
                
                this.arrRect.remove(i);

//                 this.resetCanvas();

//                 this.reDraw();

                this.cekKoor();

                this.iv.invalidate();

                return true;
            }
        }
        return false;
    }
    
    /**
     * metod untuk menutupi rect dengan warna putih
     * @param left koor left rect
     * @param top koor top rect
     * @param rigth koor rigth rect
     * @param bottom koor bottom rect
     */
    private void removeRect(int left,int top,int rigth,int bottom){
        Rect r=new Rect(left,top,rigth,bottom);
        Paint p=new Paint();
        int c= ResourcesCompat.getColor(getResources(),R.color.background_color,null);
        p.setColor(c);
        this.canvas.drawRect(r,p);
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

        }
//        this.iv.invalidate();

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
            Rect checkRect=this.arrRect.get(i);
//            if(newR.left<checkRect.right || newR.top<checkRect.bottom || newR.right>checkRect.left || newR.bottom>checkRect.top){
//                return true;
//            }
            if(this.arrRect.get(i).intersects(newR.left , newR.top , newR.right , newR.bottom)){
                return true;
            }
        }
        return false;
    }
    
    private void generetCircle(){
        for(int i=0;i<5;i++){
            this.cekKoorC();
        }
    }

    private void cekKoorC(){
        int x=0,y=0,t1=0,t2=0;
        boolean cek=false;

        while(true) {
            x=this.generateX();
            y=this.generateY();
            t1=x+100;
            t2=y+100;
            Rect r=new Rect(x,y,t1,t2);
            if(!checkContainsAll(r)){
                this.createCircle(x+50,y+50);
                cek=true;
                //break;
            }
            if(cek==true){
                break;
            }else{
                while(this.checkPosition(x,y)){
                    //createCircle
                    this.createCircle(x,y);
                    break;
                }
                break;
            }
        }
    }

    private int generateX(){
        int x=10+this.random.nextInt(this.canvas.getWidth());
        while(x+100>this.canvas.getWidth()){
            x=10+random.nextInt(this.canvas.getWidth());
        }
        return x;
    }

    private int generateY(){
        int y=10+this.random.nextInt(this.canvas.getHeight());
        while(y+100>this.canvas.getWidth()){
            y=10+random.nextInt(this.canvas.getHeight());
        }
        return y;
    }

    private boolean checkPosition(int x,int y){
        for(int i=0;i<this.arrCir.size()-2;i+=2){
            int btsXMin=this.arrCir.get(i)-50;
            int btsXMax=this.arrCir.get(i)+50;

            int btsYMin=this.arrCir.get(i+1)-50;
            int btsYMax=this.arrCir.get(i+1)+50;

            boolean a= x>=btsXMin && x<=btsXMax;
            boolean b= y>=btsYMin && y<=btsYMax;
            if(a && b){
                return false;
            }
        }
        return true;
    }

    private void createCircle(int x,int y){
        int color=ResourcesCompat.getColor(getResources(),R.color.colorAccent,null);
        Paint p=new Paint();
        p.setColor(color);

        this.canvas.drawCircle(x,y,50,p);
        this.arrCir.add(x);
        this.arrCir.add(y);
    }



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
                }
            }
            return true;
        }
    }
}

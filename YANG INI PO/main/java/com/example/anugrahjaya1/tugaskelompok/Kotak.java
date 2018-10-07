package com.example.anugrahjaya1.tugaskelompok;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Kotak {

    protected ArrayList<Rect> arrRect;
    protected ArrayList<Integer> arrNumber;
    protected FragmentGame fragmentGame;
    protected Random random;

    public Kotak(FragmentGame fragmentGame) {
        this.fragmentGame=fragmentGame;
        this.arrRect=new ArrayList<>();
        this.arrNumber=new ArrayList<>();
        this.random=new Random();
    }

    public void generateRect(){
        for(int i=0;i<5;i++){
            arrNumber.add(i+1);
            this.cekKoor(i);
        }
    }

    /**
     * random posisi baru,kalau bisa panggil createRect
     */
    private void cekKoor(int posisi){
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
                this.createRect(newR,posisi);
                //return rect
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
    private void createRect(Rect newR,int posisi){
        Log.d("debug_rect","Create rect terpanggil");
        fragmentGame.canvas.drawRect(newR,fragmentGame.myPaint);

        Paint p=new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(75);
        fragmentGame.canvas.drawText(this.arrNumber.get(posisi)+"",newR.left+30,newR.top+60,p);

//        this.iv.invalidate();,
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

            int t=Integer.parseInt(fragmentGame.target.getText().toString());
            if((a && b) && (i+1==t)){
                Log.d("tolong","Kepencet"+" "+i);
                this.setArrayRect(i);

                fragmentGame.resetCanvas();

                fragmentGame.reDraw();

                fragmentGame.iv.invalidate();

                return true;
            }
        }
        return false;
    }

    /**
     * melakukan set pada ArrayRect
     * @param posisi posisi yang akan diset
     */
    public void setArrayRect(int posisi){
        Rect rect=null;

        int left  = 0 , top = 0 , right = 0 , bottom = 0 ;
        boolean cek=false;

        while(!cek){
            left = generateLeft();
            top  = generateTop();
            right = left + 100 ;
            bottom = top + 100;
            rect = new Rect(left , top , right , bottom);
            if(!checkContainsAll(rect)){
                cek = !cek;
//                        Log.d("ceking_R",newR.left+" "+newR.top+" "+newR.right+" "+newR.bottom);
                this.arrRect.set(posisi,rect);
            }
            Log.d("rect Post" , left+" " + top + " " + right +" " + bottom);
        }
    }
    private int generateLeft(){
//        Random r =  new Random();
        int left = 10 + random.nextInt(fragmentGame.canvas.getWidth());
        while(left + 100 >= fragmentGame.canvas.getWidth()){
            left =10+ random.nextInt(fragmentGame.canvas.getWidth());
        }
        return left;
    }

    /**
     * generate Koor top untuk rect
     * @return koor top
     */
    private int generateTop(){
//        Random r =  new Random();
        int top =10+ random.nextInt(fragmentGame.canvas.getHeight());
        while(top + 100 >= fragmentGame.canvas.getHeight()){
            top =10+ random.nextInt(fragmentGame.canvas.getHeight());
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
            if(this.arrRect.get(i).intersects(newR.left , newR.top , newR.right , newR.bottom)){
                return true;
            }
        }
        return false;
    }

}

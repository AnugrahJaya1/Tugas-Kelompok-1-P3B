package com.example.anugrahjaya1.tugaskelompok;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Kelas adapter menyimpan semua properties dan capabilities adapter
 */
public class AdapterScore extends BaseAdapter{
    // Menyimpan array list yang menyimpan daftar player yang telah bermain
    protected ArrayList<Player> list;
    protected FragmentHome fragmentHome;

    // Textview nama player dan score yang didapat setelah bermain dalam satu baris listview
    protected TextView tv_PlayerName , tv_PlayerScore;

    /**
     * Constructor untuk inisialisasi objek adapter
     * @param fragmentHome Objek fragment home
     */
    public AdapterScore(FragmentHome fragmentHome){
        this.fragmentHome = fragmentHome;
        this.list = new ArrayList<Player>();
    }

    /**
     * Method untuk menambah ke listview
     * @param name nama player yang akan dimasukan
     * @param score score player yang akan dimasukan
     */
    public void addPlayer(String name , String score){
        Player newP = new Player(name , score);
        this.list.add(newP);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate (mengubah xml ke java) layout perbaris listview yaitu lst_score)layout.xml
        View res = this.fragmentHome.getLayoutInflater().inflate(R.layout.lst_score_layout , null);
        // set id textview player name dan player score
        this.tv_PlayerName = res.findViewById(R.id.tv_PlayerName);
        this.tv_PlayerScore = res.findViewById(R.id.tv_PlayerScore);

        //mengambil objek pada list
        Player pNow = this.list.get(position);
        //set player name dan player score
        this.tv_PlayerName.setText(pNow.getName());
        this.tv_PlayerScore.setText(pNow.getScore());

        return res;

    }



}

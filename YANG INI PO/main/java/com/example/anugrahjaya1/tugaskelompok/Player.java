package com.example.anugrahjaya1.tugaskelompok;

public class Player {
    // atribut menyimpan nama player dan score player setelah bermain satu putaran game
    private String name  , score;

    /**
     * Constructor untuk menginisialisasi objek player
     * @param name
     * @param score
     */
    public Player(String name , String score){
        this.name = name ;
        this.score = score;
    }

    /**
     * getter untuk atribut name
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * getter untuk atribut score
     * @return
     */
    public String getScore(){
        return this.score;
    }

    /**
     * setter untuk atribut score
     * @param score
     */
    public void setScore(String score){
        this.score = score;
    }

}

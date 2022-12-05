package com.khanfar.FrontEnd;
public class Coins {
    public int min , max , currentCoin;
    public  int currentI , currentJ ;
    public Coins(int max, int min , int currentCoin , int i , int j) {
        this.min = min;
        this.max = max;
        this.currentCoin = currentCoin;
        this .currentI = i ;
        this .currentJ = j ;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d , %d , %d , %d)" , max , min , currentCoin , currentI , currentJ);
    }

    public Coins() {
    }


}

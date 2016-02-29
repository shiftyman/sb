package com.windlike.quick.ioc;

public class Minnor {
    private int life = 2;
    
    public String name = "minnor-1";
    
    public int killOne(){
        return --life;
    }
    
    private Minnor(){
        
    }
}

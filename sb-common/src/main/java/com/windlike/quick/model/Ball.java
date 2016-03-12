package com.windlike.quick.model;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

public class Ball extends Base{
    @Protobuf
    private String name;
    
    @Protobuf
    private int size;
    
    public Ball(String name, int size){
        this.name = name;
        this.size = size;
    }
    
    public Ball(){
        
    }
}

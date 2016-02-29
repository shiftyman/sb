package com.windlike.quick.model;

import org.msgpack.annotation.Message;

@Message
public class Car{
    private String name;
    
    private int cost;
    
    public Car(String name, int cost){
        this.name = name;
        this.cost = cost;
    }
    
    //MessagePack序列化组件需要无参构造方法
    public Car(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Car[name:" + name + ", cost:" + cost + "]";
    }
    
    
}

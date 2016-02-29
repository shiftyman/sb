package com.windlike.quick.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Injector {
    
    public Injector() throws ClassNotFoundException{
        Class mic = Class.forName("ioc.Minnor");
        Field[] fields = mic.getDeclaredFields();
        Field[] fields2 = mic.getFields();
        Method[] methods = mic.getDeclaredMethods();
        Method[] methods2 = mic.getMethods();
        Class superClass = mic.getSuperclass();
    }
    
    public static void main(String[] args) throws ClassNotFoundException{
        Injector injector = new Injector();
    }
}

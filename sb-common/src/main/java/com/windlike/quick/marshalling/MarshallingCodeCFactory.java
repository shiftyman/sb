package com.windlike.quick.marshalling;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class MarshallingCodeCFactory {
	
	public static MarshallingDecoder buildMarshallingDecoder(){
		 /*
         * 通过 Marshalling 工具类的 getProvidedMarshallerFactory
         * 静态方法获取MarshallerFactory 实例, , 参数 serial 表示创建的是 Java 序列化工厂对象.它是由
         * jboss-marshalling-serial 包提供
         */
		MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		
		MarshallingConfiguration configure = new MarshallingConfiguration();
		configure.setVersion(5);
		
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configure);
		MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
		
		return decoder;
	}
	
	public static MarshallingEncoder buildMarshallingEncoder(){
		/*
         * 通过 Marshalling 工具类的 getProvidedMarshallerFactory
         * 静态方法获取MarshallerFactory 实例, , 参数 serial 表示创建的是 Java 序列化工厂对象.它是由
         * jboss-marshalling-serial 包提供
         */
		MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		
		MarshallingConfiguration configure = new MarshallingConfiguration();
		configure.setVersion(5);
		
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configure);
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		
		return encoder;
	}
	
	public static Marshaller buildMarshaller() throws IOException{
	    MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
	    MarshallingConfiguration configure = new MarshallingConfiguration();
        configure.setVersion(5);
	    return marshallerFactory.createMarshaller(configure);
	}
	
	public static Unmarshaller buildUnMarshaller() throws IOException{
	    MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configure = new MarshallingConfiguration();
        configure.setVersion(5);
        return marshallerFactory.createUnmarshaller(configure);
	}
}

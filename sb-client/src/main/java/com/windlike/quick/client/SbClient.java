package com.windlike.quick.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.io.IOException;

import com.windlike.quick.decoder.MsgPackDecoder;
import com.windlike.quick.encoder.MsgPackEncoder;
import com.windlike.quick.handler.CarClientHandler;
import com.windlike.quick.handler.PersonClientHandler;
import com.windlike.quick.marshalling.MarshallingCodeCFactory;

public class SbClient {
	private String host = "localhost";
	
	private int port = 22520;
	
	public SbClient connect(String host, int port){
		this.host = host;
		this.port = port;
		return this;
	}
	
	public void start(){
	    System.out.println("client start.");
	    
		EventLoopGroup loopGroup = new NioEventLoopGroup();
		Bootstrap bootStrap = new Bootstrap();
		bootStrap.group(loopGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
				    //测试换行符粘包拆包分包
//					ch.pipeline().addLast(new LineBasedFrameDecoder(1024))
//						.addLast(new TimeClientHandler());
					//测试msgpack编解码
//				    ch.pipeline()
//				        .addLast("frame-decoder", new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2))
//				        .addLast("msgpack-decoder", new MsgPackDecoder())
//				        .addLast("frame-encoder", new LengthFieldPrepender(2))
//                        .addLast("msgpack-encoder", new MsgPackEncoder())
//				        .addLast("client-handler", new CarClientHandler());
					//测试marshalling
					ch.pipeline()
						.addLast(MarshallingCodeCFactory.buildMarshallingDecoder())
						.addLast(MarshallingCodeCFactory.buildMarshallingEncoder())
						.addLast(new PersonClientHandler());
				        
				}
			});
		try {
			ChannelFuture future = bootStrap.connect(host, port).sync();
			
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			loopGroup.shutdownGracefully();
			System.out.println("client stop.");
		}
	}
	
	public static void main(String[] args) throws IOException{
		if(args.length >= 2){
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			new SbClient().connect(host, port).start();
		}else{
			new SbClient().start();
			
		}
	}
}

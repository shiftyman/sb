package com.windlike.quick.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.windlike.quick.handler.ServerChannelHandlerInitializer;

public class SbServer {

    private int port = 22520;

    public SbServer bind(int port) {
        this.port = port;
        return this;
    }

    public void start() {
        System.out.println("sbServer start.");
        
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap sbootstrap = new ServerBootstrap();
        sbootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 256)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new LoggingHandler(LogLevel.DEBUG))
            .childHandler(new ServerChannelHandlerInitializer());
        try {
            ChannelFuture future = sbootstrap.bind(port).sync();
            
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("sbServer stop.");
        }
    }
    
    public static void main(String[] args){
    	if(args.length > 0){
    		int port = Integer.parseInt(args[0]);
    		new SbServer().bind(port).start();
    	}else{
    		new SbServer().start();
    	}
    	
    }
}

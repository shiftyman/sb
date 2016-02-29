package com.windlike.quick.handler;


import java.util.Date;

import com.windlike.quick.constant.SystemConstant;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimerServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
    	ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // TODO Auto-generated method stub
    	System.out.println("in channelRead");
        ByteBuf buf = (ByteBuf) msg;
        byte[] readMsg = new byte[buf.readableBytes()];
        buf.readBytes(readMsg);
//        String body = new String(readMsg, 0, readMsg.length - SystemConstant.LINE_SEPARATOR.length(), "UTF-8");
        String body = new String(readMsg, "UTF-8");
        System.out.println("Server recv msg:" + body);
        String respMsg = body.equalsIgnoreCase("query time order") ? new Date().toString() : "BAD REQ.";
        respMsg += SystemConstant.LINE_SEPARATOR;
        ByteBuf resp = Unpooled.copiedBuffer(respMsg.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
    	System.out.println("in channelReadComplete");
        ctx.flush();
    }
    
}

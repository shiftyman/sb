package com.windlike.quick.handler;

import java.util.concurrent.TimeUnit;

import com.windlike.quick.constant.EsqikMsgType;
import com.windlike.quick.protocol.esqik.EsqikHeader;
import com.windlike.quick.protocol.esqik.EsqikMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class HandshakeClientHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i = 0; i < 10; i++){
            EsqikHeader msgHeader = new EsqikHeader();
            msgHeader.setType(EsqikMsgType.HANDSHAKE_REQ);
            EsqikMessage msg = new EsqikMessage(msgHeader);
            ctx.writeAndFlush(msg);
            
            System.out.println("client sends handshark req.");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        EsqikMessage esqikMsg = (EsqikMessage) msg;
        if(esqikMsg.getHeader().getType() == EsqikMsgType.HANDSHAKE_RESP){
            System.out.println("client received handshark resp.");
            
            //启动心跳
            ctx.executor().scheduleAtFixedRate(new Runnable() {
                public void run() {
                    
                }
            }, 5, 5, TimeUnit.SECONDS);
        }
    }
    
}

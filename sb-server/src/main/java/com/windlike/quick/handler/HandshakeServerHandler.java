package com.windlike.quick.handler;

import com.windlike.quick.constant.EsqikMsgType;
import com.windlike.quick.protocol.esqik.EsqikHeader;
import com.windlike.quick.protocol.esqik.EsqikMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class HandshakeServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        EsqikMessage handshakeReq = (EsqikMessage) msg;
        if(handshakeReq.getHeader().getType() == EsqikMsgType.HANDSHAKE_REQ){
            EsqikHeader header = new EsqikHeader();
            header.setType(EsqikMsgType.HANDSHAKE_RESP);
            EsqikMessage handshakeResp = new EsqikMessage(header);
            ctx.writeAndFlush(handshakeResp);
            
            System.out.println("server send handshake resp to " + ctx.channel().remoteAddress() + ".");
        }else{
            ctx.fireChannelRead(msg);
        }
        
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelReadComplete(ctx);
    }
    
}

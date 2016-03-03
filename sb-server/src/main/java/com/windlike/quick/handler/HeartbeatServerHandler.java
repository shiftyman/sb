package com.windlike.quick.handler;

import com.windlike.quick.constant.EsqikMsgType;
import com.windlike.quick.protocol.esqik.EsqikHeader;
import com.windlike.quick.protocol.esqik.EsqikMessage;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class HeartbeatServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        EsqikMessage esqikMsg = (EsqikMessage) msg;
        if(esqikMsg.getHeader().getType() == EsqikMsgType.HEARTBEAT_REQ){
            System.out.println("heartbeat req from:" + ctx.channel().remoteAddress());
            
//            EsqikHeader header = new EsqikHeader();
//            header.setType(EsqikMsgType.HEARTBEAT_RESP);
//            EsqikMessage resp = new EsqikMessage(header);
//            ctx.writeAndFlush(resp);
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

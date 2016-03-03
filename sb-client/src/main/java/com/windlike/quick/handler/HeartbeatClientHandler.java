package com.windlike.quick.handler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.windlike.quick.constant.EsqikMsgType;
import com.windlike.quick.protocol.esqik.EsqikMessage;
import com.windlike.quick.task.HeartbeatTask;

public class HeartbeatClientHandler extends ChannelHandlerAdapter{

    private HeartbeatTask heartbeatTask;
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        EsqikMessage esqikMsg = (EsqikMessage) msg;
        if(esqikMsg.getHeader().getType() == EsqikMsgType.HEARTBEAT_RESP){//心跳应答
            System.out.println("client received heartbeat resp.");
            if(heartbeatTask != null){
                heartbeatTask.receiveResp();
            }
        }else if(esqikMsg.getHeader().getType() == EsqikMsgType.HANDSHAKE_RESP){//握手应答，启动心跳
            //启动心跳
            if(heartbeatTask == null){
                heartbeatTask = new HeartbeatTask(ctx);
                ctx.executor().scheduleAtFixedRate(heartbeatTask, 1, 1, TimeUnit.MINUTES);
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }
    
    
}

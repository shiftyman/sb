package com.windlike.quick.task;

import io.netty.channel.ChannelHandlerContext;

import com.windlike.quick.constant.EsqikMsgType;
import com.windlike.quick.protocol.esqik.EsqikHeader;
import com.windlike.quick.protocol.esqik.EsqikMessage;

public class HeartbeatTask implements Runnable{

    private ChannelHandlerContext ctx;
    
    public HeartbeatTask(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }
    
    public void run() {
        EsqikHeader header = new EsqikHeader();
        header.setType(EsqikMsgType.HEARTBEAT_REQ);
        EsqikMessage msg = new EsqikMessage(header);
        ctx.writeAndFlush(msg);
    }

}

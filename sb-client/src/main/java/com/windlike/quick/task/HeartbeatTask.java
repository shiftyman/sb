package com.windlike.quick.task;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;

import com.windlike.quick.constant.EsqikMsgType;
import com.windlike.quick.protocol.esqik.EsqikHeader;
import com.windlike.quick.protocol.esqik.EsqikMessage;

public class HeartbeatTask implements Runnable{
    
    private final int RESP_WAIT_TIME = 5;//心跳应答等待时间

    private ChannelHandlerContext ctx;
    
    private boolean ifResponse = false;//请求是否已经收到应答
    
    /**
     * 应答通知。当心跳处理handler收到心跳应答时调用，以通知任务心跳已经答复。
     */
    public void receiveResp(){
        ifResponse = true;
    }
    
    public HeartbeatTask(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }
    
    public void run() {
        //发送心跳请求
        sendHeartBeatReq();
        //等待心跳回复
        ctx.executor().schedule(new HeartbeatWaitRespTask(), RESP_WAIT_TIME, TimeUnit.SECONDS);
    }
    
    public void sendHeartBeatReq(){
        ifResponse = false;
        System.out.println("client sends heartbeat req.");
        
        EsqikHeader header = new EsqikHeader();
        header.setType(EsqikMsgType.HEARTBEAT_REQ);
        EsqikMessage msg = new EsqikMessage(header);
        ctx.writeAndFlush(msg);
    }
    
    private final class HeartbeatWaitRespTask implements Runnable{

        private int resendTime = 0;//重新发送心跳次数
        
        public void run() {
            if (!ctx.channel().isOpen()) {
                return;
            }
            
            //判断是否未回复
            if(!ifResponse){
              //超时重新发送心跳,继续等待60秒
                if(resendTime >= 3){//超时太多，断开连接
                    ctx.close();
                    ctx.fireExceptionCaught(new Throwable("heartbeat resend has been timeout for 3 times."));
                }else{
                    sendHeartBeatReq();//重新发送心跳请求
                    resendTime++;
                    ctx.executor().schedule(this, RESP_WAIT_TIME, TimeUnit.SECONDS);
                }
            }
        }
        
    }

}

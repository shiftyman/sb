package com.windlike.quick.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.io.IOException;
import java.util.Map;

import com.windlike.quick.protocol.esqik.EsqikMessage;

public class EsqikEncoder extends MarshallingEncoder{
    
    public EsqikEncoder(MarshallerProvider provider) throws IOException {
        super(provider);
    }
    
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        EsqikMessage esqikMsg = (EsqikMessage) msg;
        out.writeInt(esqikMsg.getHeader().getMagic());
        out.writeInt(esqikMsg.getHeader().getLength());
        out.writeLong(esqikMsg.getHeader().getSessionId());
        out.writeByte(esqikMsg.getHeader().getType());
        out.writeByte(esqikMsg.getHeader().getPriority());
        
        //编码attachement对象
        int attachSize = esqikMsg.getHeader().getAttachment().size();
        out.writeInt(attachSize);
        for(Map.Entry<String, Object> entry : esqikMsg.getHeader().getAttachment().entrySet()){
            String key = entry.getKey();
            byte[] keyBytes = key.getBytes("utf-8");
            out.writeInt(keyBytes.length);//写入key长度
            out.writeBytes(keyBytes);//写入key
            super.encode(ctx, entry.getValue(), out);//写入val
        }
        
        //编码body对象
        if(esqikMsg.getBody() == null){
            out.writeInt(0);//写入body长度为0，结束
        }else{
            super.encode(ctx, esqikMsg.getBody(), out);//编码body对象
        }
        
        //替换length字段值为实际写入字节数
        out.setInt(4, out.readableBytes());
    }
}

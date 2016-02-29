package com.windlike.quick.decoder;

import java.util.List;

import org.msgpack.MessagePack;

import com.windlike.quick.model.Car;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf>{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // TODO Auto-generated method stub
        MessagePack msgPack = new MessagePack();
        byte[] read = new byte[msg.readableBytes()];
        int readIndex = msg.readerIndex();
        msg.getBytes(readIndex, read);
//        msg.getBytes(msg.readerIndex() + 2, read, 0, length - 2);
        out.add(msgPack.read(read, Car.class));
    }
    
}

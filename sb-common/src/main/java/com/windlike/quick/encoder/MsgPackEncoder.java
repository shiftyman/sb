package com.windlike.quick.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.msgpack.MessagePack;

import com.windlike.quick.model.Car;

public class MsgPackEncoder extends MessageToByteEncoder<Car>{

    @Override
    protected void encode(ChannelHandlerContext ctx, Car msg, ByteBuf out) throws Exception {
        // TODO Auto-generated method stub
        MessagePack msgPack = new MessagePack();
        byte[] bytes = msgPack.write(msg);
        out.writeBytes(bytes);
    }

}

package com.windlike.quick.handler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;

import com.windlike.quick.decoder.EsqikDecoder;
import com.windlike.quick.decoder.MsgPackDecoder;
import com.windlike.quick.encoder.EsqikEncoder;
import com.windlike.quick.encoder.MsgPackEncoder;
import com.windlike.quick.marshalling.MarshallingCodeCFactory;

public class ServerChannelHandlerInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //测试分隔符解决粘包拆包
//    	ByteBuf delimiter = Unpooled.copiedBuffer(SystemConstant.LINE_SEPARATOR.getBytes());
//        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter))
//        	.addLast(new TimerServerHandler());
    	
    	//测试msgpack编解码
//    	ch.pipeline().addLast("frame-decoder", new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2))
//    	    .addLast("msgpack-decoder", new MsgPackDecoder())
//    	    .addLast("frame-encode", new LengthFieldPrepender(2))
//    	    .addLast("msgpack-encoder", new MsgPackEncoder())
//    	    .addLast("car-handler", new CarServerHandler());
    	
    	//测试marshalling
//    	ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder())
//    		.addLast(MarshallingCodeCFactory.buildMarshallingEncoder())
//    		.addLast(new PersonServerHandler());
        
        //esqik协议
        ch.pipeline().addLast(new EsqikDecoder(1048576))
            .addLast(new EsqikEncoder(MarshallingCodeCFactory.buildProvider()))
            .addLast(new ReadTimeoutHandler(2, TimeUnit.MINUTES))
            .addLast(new HandshakeServerHandler())
            .addLast(new HeartbeatServerHandler());
    }


}

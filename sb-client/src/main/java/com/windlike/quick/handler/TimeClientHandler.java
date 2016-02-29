package com.windlike.quick.handler;

import com.windlike.quick.constant.SystemConstant;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter{
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		String req = "QUERY TIME ORDER" + SystemConstant.LINE_SEPARATOR;
		byte[] reqByte = req.getBytes();
		for(int i = 0; i < 10; i ++){
			ByteBuf buf = Unpooled.copiedBuffer(reqByte);
			ctx.writeAndFlush(buf);
		}
//		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
//		String resp = new String(bytes, 0, bytes.length - SystemConstant.LINE_SEPARATOR.length(), "utf-8");
		String resp = new String(bytes, "utf-8");
		System.out.println("Now is:" + resp);
	}

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }
	
	

}

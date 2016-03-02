package com.windlike.quick.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.jboss.marshalling.ByteBufferInput;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import com.windlike.quick.marshalling.MarshallingCodeCFactory;
import com.windlike.quick.protocol.esqik.EsqikHeader;
import com.windlike.quick.protocol.esqik.EsqikMessage;

public class EsqikDecoder extends LengthFieldBasedFrameDecoder{
    
    private Unmarshaller unmarshaller;
    
    public EsqikDecoder(int maxFrameLength) throws IOException {
        super(maxFrameLength, 4, 4, -8, 0);
        this.unmarshaller = MarshallingCodeCFactory.buildUnMarshaller();
    }
    
    public EsqikDecoder() throws IOException {
        this(1048576);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if(frame == null){
            return null;
        }
        
        EsqikMessage msg = new EsqikMessage();
        EsqikHeader header = new EsqikHeader();
        header.setMagic(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionId(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());
        
        //读取attachment
        int attachSize = frame.readInt();
        if(attachSize > 0){
            Map<String, Object> attachment = new HashMap<String, Object>(attachSize);
            for(int i =0; i < attachSize; i++){
                int keyLen = frame.readInt();
                byte[] keyByte = new byte[keyLen];
                frame.readBytes(keyByte);
                attachment.put(new String(keyByte, "utf-8"), decodeObj(in));
            }
        }
        msg.setHeader(header);
        
        //读取消息body
        int bodySize = frame.readInt();
        if(bodySize > 0){
            msg.setBody(decodeObj(in));
        }
        
        return msg;
    }
    
    private Object decodeObj(ByteBuf buf) throws IOException, ClassNotFoundException{
        int objSize = buf.readInt();
        
        ByteBuffer slice = buf.nioBuffer(buf.readerIndex(), objSize);
        ByteInput input = new ByteBufferInput(slice);
        
        unmarshaller.start(input);
        Object obj = unmarshaller.readObject();
        unmarshaller.finish();
        
        buf.readerIndex(buf.readerIndex() + objSize);
        
        return obj;
    }

}

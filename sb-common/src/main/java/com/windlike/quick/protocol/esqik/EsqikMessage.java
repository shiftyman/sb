package com.windlike.quick.protocol.esqik;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

public class EsqikMessage {

    @Protobuf
    private EsqikHeader header;
    
    @Protobuf
    private Object body;
    
    public EsqikMessage(){
    }
    
    public EsqikMessage(EsqikHeader header){
        this.header = header;
    }
    
    public EsqikHeader getHeader() {
        return header;
    }

    public void setHeader(EsqikHeader header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}

package com.windlike.quick.protocol.esqik;

public class EsqikMessage {

    private EsqikHeader header;
    
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

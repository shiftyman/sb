package com.windlike.quick.protocol.esqik;

import java.util.Map;

public class EsqikHeader {
    
    private static final short MAGIC_CODE = 0XA0;
    
    /**
     * 协议魔数：协议号(2) << 16 | 版本号(1) << 8 | 次版本号(1)
     */
    private int magic = MAGIC_CODE << 16 | 1 << 8 | 1;
    
    /**
     * 消息总长度: header + body
     */
    private int length;
    
    /**
     * 会话ID：全局唯一标识
     */
    private long sessionId;
    
    /**
     * 消息类型:
     * 0 业务请求，
     * 1 业务响应，
     * 2 握手请求，
     * 3 握手响应，
     * 4 心跳请求，
     * 5 心跳响应
     */
    private byte type;
    
    /**
     * 优先级(保留)
     */
    private byte priority;
    
    /**
     * 扩展
     */
    private Map<String, Object> attachment;

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }
    
    public void setMagic(byte version, byte subVersion){
        this.magic = MAGIC_CODE << 16 | version << 8 | subVersion;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 获取消息类型{@link EsqikHeader#type}
     * 
     * @return
     */
    public byte getType() {
        return type;
    }

    /**
     * 设置消息类型{@link EsqikHeader#type}
     * 
     * @return
     */
    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
    
}

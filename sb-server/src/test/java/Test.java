import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.windlike.quick.constant.EsqikMsgType;
import com.windlike.quick.model.Ball;
import com.windlike.quick.model.Base;
import com.windlike.quick.model.Car;
import com.windlike.quick.protocol.esqik.EsqikHeader;
import com.windlike.quick.protocol.esqik.EsqikMessage;


public class Test {
    public static void main(String[] args){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3); 
        
        Base ball = new Ball("football", 20);
        
        //test serialization
        Codec simpleTypeCodec = ProtobufProxy.create(Ball.class);
        try {
            byte[] bytes = simpleTypeCodec.encode(ball);
            Ball ball2 = (Ball) simpleTypeCodec.decode(bytes);
            if(ball2 == ball){
                System.out.println("??");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
    }
}

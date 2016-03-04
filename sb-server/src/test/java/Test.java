import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Test {
    public static void main(String[] args){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3); 
    }
}

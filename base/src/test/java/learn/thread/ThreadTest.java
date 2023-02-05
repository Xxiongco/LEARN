package learn.thread;


import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 *  线程测试
 */
public class ThreadTest {


    // 两个线程，一个线程打印1-52，另一个线程打印A-Z，打印结果为12A34B...5152Z
    @Test
    public void test() {

        MyPrint print = new MyPrint();

        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                print.printChartSet();
            }
        }).start();


        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                print.printNum();
            }
        }).start();

    }


}

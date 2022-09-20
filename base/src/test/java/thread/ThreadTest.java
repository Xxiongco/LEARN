package thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadTest {

    @Test
    public void test() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Runnable runnable = () -> {
            int x = 50;
            for (int i = 0; i < x; i++) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };
        executorService.execute(runnable);
        executorService.execute(runnable);

        while (true){}
    }
}

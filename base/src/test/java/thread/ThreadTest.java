package thread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BiConsumer;
import java.util.function.Function;

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

        while (true) {
        }
    }

    /**
     * 结果是
     * [main] 000
     * [ForkJoinPool.commonPool-worker-1] 111
     * 也就是说，如果supply任务已经执行完毕，那么就需要当前main线程来执行s.length()逻辑；
     * 如果supply任务还未执行完毕，显然这时候main线程需要将Function记录到CompletableFuture中，
     * 这样当ForkJoin线程执行完supply任务时可以执行s.length()逻辑
     */

    @Test
    public void testParallel() {

        CompletableFuture.completedFuture("000")
                .thenApply(r -> r)
                .whenComplete((r, e) -> System.out.println(format(r)));

        CompletableFuture.supplyAsync(() -> {
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(10));
                    return "111";
                })
                .thenApply(r -> r)
                .whenComplete((r, e) -> System.out.println(format(r)));
    }

    private static String format(String msg) {
        return String.format("[%s] %s", Thread.currentThread().getName(), msg);
    }

    /**
     * 用于debug
     */
    @Test
    public void testCompletableDebug() throws InterruptedException {

        CompletableFuture.supplyAsync(() -> {
            // random n millisecond
            int ms = new Random().nextInt(100);
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(ms));

            String msg = String.format("supplyAsync %s ms", ms);
            System.out.println(format(msg));
            return msg;
        }).thenApply(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println(format("thenApply apply s.length()"));
                return s.length();
            }
        }).whenComplete(new BiConsumer<Integer, Throwable>() {
            @Override
            public void accept(Integer s, Throwable throwable) {
                System.out.println(format("done " + s));
            }
        });

        Thread.sleep(1000);
        System.out.println("finish");
    }

    @Test
    public void testString() {
        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);

        StringBuilder stringBuilder = new StringBuilder();

        int sizeCount = 0;

        for (Integer integer : list) {
            StringBuilder append = stringBuilder.append("[" + integer + "]");
            sizeCount ++;

            if (sizeCount < list.size()) {
                stringBuilder.append(",");
            }

        }

        System.out.println(stringBuilder.toString());


    }

    @Test
    public void testMutex() {
        Integer a = 0;
        Integer b = 0;

        boolean allOne = Objects.equals(1,a) && Objects.equals(1,b);
        boolean allNotOne = !Objects.equals(1,a) && !Objects.equals(1,b);

        if (allOne || allNotOne) {
            System.out.println(1);
        }
        else {
            System.out.println(0);
        }
    }
}

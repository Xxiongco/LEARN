package learn.thread;

import sun.misc.Unsafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.http.annotation.ThreadingBehavior.UNSAFE;

/**
 *  用于测试线程相关
 */
public class MainTest {


    public static void main(String[] args) {

        Thread mainThread = Thread.currentThread();
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("haha: " + Thread.currentThread().getName());
                    Thread.sleep(5000L);

                    // 阻塞过后，查看main线程是否中断
                    System.out.println("阻塞前: " + mainThread.isInterrupted()); //false
                    LockSupport.unpark(mainThread);
                    System.out.println("阻塞后: " + mainThread.isInterrupted()); //false

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();

        System.out.println("main: " + Thread.currentThread().getName());
        System.out.println("阻塞前状态: " + Thread.currentThread().isInterrupted()); // false
        LockSupport.park();
        System.out.println("阻塞后状态: " + Thread.currentThread().isInterrupted()); // false


        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted()); // true
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted()); // true

        System.out.println(Thread.interrupted()); // true
        System.out.println(Thread.currentThread().isInterrupted()); // false
        System.out.println(Thread.interrupted()); //false
        System.out.println(Thread.currentThread().isInterrupted()); // false
    }

    public void test1() {
        Lock lock = new ReentrantLock();
        try{
            lock.lock();//加锁操作
        }finally{
            lock.unlock();
        }
    }
}

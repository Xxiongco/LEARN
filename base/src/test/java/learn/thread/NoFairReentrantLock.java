package learn.thread;

import lombok.Data;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class NoFairReentrantLock {

    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        NoFairReentrantLock noFairReentrantLock = new NoFairReentrantLock();

        for (int i = 0; i < 10;  i++) {
            new Thread("线程： "+i){
                @Override
                public void run() {
                    noFairReentrantLock.test();
                }
            }.start();
        }
        // 主线程阻塞
        LockSupport.park();
    }


    public void test() {

        try {
            this.getLock().lock();
            String name = Thread.currentThread().getName();
            for(int x = 0; x < 20; x ++ ) {
                Thread.sleep(1000);
                System.out.println(name + ": " + x);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.getLock().unlock();
        }
    }

}

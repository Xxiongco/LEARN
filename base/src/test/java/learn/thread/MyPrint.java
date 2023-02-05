package learn.thread;

public class MyPrint {

    private int numberFlag = 1;

    private int count = 1;


    public synchronized void printNum() {
        if (numberFlag != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print((count << 1) - 1);
        System.out.print(" ");
        System.out.print(count << 1);
        System.out.print(" ");

        numberFlag = 0;
        notify();
    }

    public synchronized void printChartSet() {
        if (numberFlag == 1) {
            try {
                // 释放锁
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // 执行到这里，标识获取到锁了
        System.out.print((char) (count - 1 + 'A'));
        System.out.print(" ");
        count++;
        numberFlag = 1;

        // 唤醒其他持有该锁的线程
        notify();
    }

}

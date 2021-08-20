import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    private Lock mLock = new ReentrantLock();

    List<Integer> mQueue = new ArrayList<Integer>();
    boolean mStop = false;

    private Runnable producer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 10; i++) {
                mLock.lock();
                try {
                    mQueue.add(i);
                    System.out.println(name + " 生產 " + i);
                } finally {
                    mLock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mLock.lock();
            try {
                mStop = true;
            } finally {
                mLock.unlock();
            }
            System.out.println(name + "停止生產");
        }
    };

    private Runnable consumer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                mLock.lock();
                try {
                    if (mQueue.size() > 0) {
                        Integer i = mQueue.remove(0);
                        System.out.println(name + " 消費 " + i);
                    }
                } finally {
                    mLock.unlock();
                }
                mLock.lock();
                try {
                    if (mStop) {
                        break;
                    }
                } finally {
                    mLock.unlock();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + "停止消費");
        }
    };

    Thread thread1 = new Thread(producer, "A");
    Thread thread2 = new Thread(consumer, "B");
    Thread thread3 = new Thread(consumer, "C");

    public void test() {
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void main(String[] args) {
        ReentrantLockTest testObj = new ReentrantLockTest();
        testObj.test();
    }
}

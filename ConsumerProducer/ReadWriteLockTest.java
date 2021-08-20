import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    private ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    
    List<Integer> mQueue = new ArrayList<Integer>();
    boolean mStop = false;

    private Runnable producer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 10; i++) {
                mLock.writeLock().lock();
                try {
                    mQueue.add(i);
                    System.out.println(name + " 生產 " + i);
                } finally {
                    mLock.writeLock().unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mLock.writeLock().lock();
            try {
                mStop = true;
            } finally {
                mLock.writeLock().unlock();
            }
            System.out.println(name + "停止生產");
        }
    };

    private Runnable consumer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                mLock.readLock().lock();
                try {
                    String contents = Arrays.toString(mQueue.toArray());
                    System.out.println(name + " 讀取 " + contents);
                } finally {
                    mLock.readLock().unlock();
                }
                mLock.readLock().lock();
                try {
                    if (mStop) {
                        break;
                    }
                } finally {
                    mLock.readLock().unlock();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + "停止讀取");
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
        ReadWriteLockTest testObj = new ReadWriteLockTest();
        testObj.test();
    }
}

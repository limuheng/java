import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BasicLockTest {
    private Lock lock = new ReentrantLock();

    private int count = 0;

    private Runnable addTask = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                if (lock.tryLock()) {
                    try {
                        count++;
                        System.out.println(name + " adds 1, count: " + count);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(name + " skips add");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Runnable subtractTask = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                if (lock.tryLock()) {
                    try {
                        count -= 2;
                        System.out.println(name + " subtracts 2, count: " + count);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(name + " skips subtract");
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Thread thread1 = new Thread(addTask, "A");
    private Thread thread2 = new Thread(addTask, "B");
    private Thread thread3 = new Thread(subtractTask, "C");

    public void test() {
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void main(String[] args) {
        BasicLockTest testObj = new BasicLockTest();
        testObj.test();
    }
}

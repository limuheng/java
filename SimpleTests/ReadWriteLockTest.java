import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock ();

    String text;

    private Runnable readTask = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                System.out.println(name + " is going to read...");
                lock.readLock().lock();
                try {
                    System.out.println(name + " read text: " + text);
                } finally {
                    System.out.println(name + " finishes reading...");
                    lock.readLock().unlock();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Runnable writeTask = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                System.out.println(name + " is going to write...");
                lock.writeLock().lock();
                try {
                    int randomNum = (int)(Math.random() * 100) + 1;
                    text = name + " 產生隨機數字: " + randomNum;
                    System.out.println(name + " write text: " + text);
                } finally {
                    System.out.println(name + " finishes writing...");
                    lock.writeLock().unlock();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Thread thread1 = new Thread(readTask, "A");
    Thread thread2 = new Thread(readTask, "B");
    Thread thread3 = new Thread(readTask, "C");
    Thread thread4 = new Thread(writeTask, "D");

    public void test() {
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    public static void main(String[] args) {
        ReadWriteLockTest testObj = new ReadWriteLockTest();
        testObj.test();
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ObjectWaitNotifyTest {
    Object mObj = new Object();

    List<Integer> mQueue = new ArrayList<Integer>();
    AtomicBoolean mStop = new AtomicBoolean(false);

    Runnable producer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 10; i++) {
                try {
                    synchronized (mObj) {
                        mQueue.add(i);
                        System.out.println(name + " 生產 " + i);
                        mObj.notify();
                      }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mStop.set(true);
            System.out.println(name + "停止生產");
            synchronized (mObj) {
                mObj.notifyAll();
            }
        }
    };

    Runnable consumer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    synchronized (mObj) {
                        if (mQueue.size() == 0) {
                            System.out.println(name + " 等待中");
                            mObj.wait();
                        }
                        if (mStop.get()) {
                            break;
                        }
                        Integer i = mQueue.remove(0);                        
                        System.out.println(name + " 消費 " + i);
                    }
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
    Thread thread4 = new Thread(consumer, "D");

    public void test() {
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    public static void main(String[] args) {
        ObjectWaitNotifyTest tester = new ObjectWaitNotifyTest();
        tester.test();
    }
}

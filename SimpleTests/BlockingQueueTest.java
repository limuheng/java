import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTest {
    private BlockingQueue<String> mQueue = new LinkedBlockingQueue<>();
    private Object lock = new Object();
    private boolean mStop = false;

    private Runnable mProducer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            int counter = 0;
            while (counter < Integer.MAX_VALUE) {
                try {
                    String product = "Product(" + counter + ")";
                    System.out.println(name + " add " + product);
                    mQueue.put(product);
                    Thread.sleep(1000);
                    counter++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (lock) {
                mStop = true;
            }
        }
    };

    private Runnable mConsumer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                synchronized (lock) {
                    if (mStop) {
                        break;
                    }
                }
                try {
                    System.out.println(name + " wanna takes");
                    String a = mQueue.take();
                    System.out.println(name + " takes " + a);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + " exits");
        }
    };

    public void test() {
        Thread producer = new Thread(mProducer, "Producer");
        Thread cunsumer1 = new Thread(mConsumer, "Consumer1");
        Thread cunsumer2 = new Thread(mConsumer, "Consumer2");
        producer.start();
        cunsumer1.start();
        cunsumer2.start();
    }

    public static void main(String[] args) {
        BlockingQueueTest testObj = new BlockingQueueTest();
        testObj.test();
    }
}

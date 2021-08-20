import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class BlockingQueueTest2 {
    private BlockingQueue<String> mQueue = new LinkedBlockingQueue<>();

    private Runnable producer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    // Generate integer ranges in [1, 100]
                    int num = (int)(Math.random() * 100) + 1;
                    String product = "產品[" + String.valueOf(num) + "] by " + name;
                    System.out.println(name + " 製造 產品[" + String.valueOf(num) + "]");
                    mQueue.put(product);
                    Thread.sleep(num * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Runnable consumer = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    String product = mQueue.take();
                    System.out.println(name + " 消費 " + product);
                    int num = (int)(Math.random() * 10) + 1;
                    Thread.sleep(num * 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Thread producer1 = new Thread(producer, "生產者A");
    private Thread producer2 = new Thread(producer, "生產者B");
    private Thread consumer1 = new Thread(consumer, "消費者C");
    private Thread consumer2 = new Thread(consumer, "消費者D");

    public void test() {
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
    }

    public static void main(String[] args) {
        BlockingQueueTest2 tester = new BlockingQueueTest2();
        tester.test();
    }
}
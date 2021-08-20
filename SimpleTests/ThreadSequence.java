public class ThreadSequence {
    private final int MAX_THREAD_NUM = 5;
    private Thread[] threads = new Thread[MAX_THREAD_NUM];
    private final int BASE = 'A';

    private Object obj = new Object();
    private int count = 0;

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (true) {
                synchronized (obj) {
                    if (count == ((int)threadName.charAt(0) - BASE)) {
                        //System.out.println(threadName + " exits loop");
                        break;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (obj) {
                System.out.println(threadName + ", count: " + count);
                count++;
            }
        }
    };

    public void test() {
        //for (int i = 0; i < MAX_THREAD_NUM; i++) {
        for (int i = MAX_THREAD_NUM - 1; i >= 0; i--) {          
            threads[i] = new Thread(task, String.valueOf((char)(BASE + i)));
            threads[i].start();
        }
    } 

    public static void main(String[] args) {
        ThreadSequence threadSequence = new ThreadSequence();
        threadSequence.test();
    }
}

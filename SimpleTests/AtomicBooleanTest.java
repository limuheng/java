import java.util.concurrent.atomic.AtomicBoolean;

class AtomicBooleanTest {
    private AtomicBoolean isSet = new AtomicBoolean(false);

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            int random = (int)(Math.random() * 10) + 1;
            try {
                System.out.println(name + " sleeps " + random + " seconds");
                Thread.sleep(random * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isSet.compareAndSet(false, true)) {
                System.out.println(name + " runs");
            } else {
                System.out.println(name + " NOT runs");
            }
        }
    };

    Thread a = new Thread(task, "A");
    Thread b = new Thread(task, "B");
    Thread c = new Thread(task, "C");

    public void test() {
        a.start();
        b.start();
        c.start();
    }

    public static void main(String[] args) {
        AtomicBooleanTest obj = new AtomicBooleanTest();
        obj.test();
    }
}
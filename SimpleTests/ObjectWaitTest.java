public class ObjectWaitTest {
    Object mObj = new Object();

    int count = 0;

    Runnable countingtask = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    synchronized (mObj) {
                        mObj.notifyAll();
                        if (count >= 30) {
                            break;
                        }
                        count++;
                        System.out.println(name + "報數：" + count);
                        mObj.wait();
                      }
                    int time = (int)(Math.random() * 10) + 1;
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + "停止報數");
        }
    };
    Thread thread1 = new Thread(countingtask, "A");
    Thread thread2 = new Thread(countingtask, "B");
    Thread thread3 = new Thread(countingtask, "C");
    Thread thread4 = new Thread(countingtask, "D");

/*
    Runnable waitTask = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    synchronized (mObj) {
                        System.out.println(name + "進入等待");
                        mObj.wait();
                        System.out.println(name + "被喚醒了");
                    }
                    int time = (int)(Math.random() * 10) + 1;
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Runnable notifyTask = new Runnable() {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    synchronized (mObj) {
                        System.out.println(name + "嘗試喚醒等待者");
                        mObj.notify();
                    }
                    int time = (int)(Math.random() * 10) + 1;
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Thread thread1 = new Thread(waitTask, "A");
    Thread thread2 = new Thread(waitTask, "B");
    Thread thread3 = new Thread(notifyTask, "C");
    Thread thread4 = new Thread(notifyTask, "D");
*/

    public void test() {
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    public static void main(String[] args) {
        ObjectWaitTest tester = new ObjectWaitTest();
        tester.test();
    }
}

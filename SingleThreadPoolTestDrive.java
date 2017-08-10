
public class SingleThreadPoolTestDrive {

    public static void main(String[] args) {
        final SingleThreadPool singleThreadPool = new SingleThreadPool();
        singleThreadPool.start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    singleThreadPool.submit(new MyRunnable(Thread.currentThread().getName()), Thread.currentThread().getName());
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    singleThreadPool.submit(new MyRunnable(Thread.currentThread().getName()), Thread.currentThread().getName());
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    singleThreadPool.submit(new MyRunnable(Thread.currentThread().getName()), Thread.currentThread().getName());
                }
            }
        }).start();

        try {
            Thread.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        singleThreadPool.terminate();
//        singleThreadPool.stop();
    }
}

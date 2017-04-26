public class SingleThreadPoolTestDrive {

    public static void main(String[] args) {
        final SingleThreadPool singleThreadPool = new SingleThreadPool();

        //Запуск фонового потока
        singleThreadPool.start();

        new Thread(new Runnable() {
            public void run() {
                //Добавление в цикле 10 задач в фоновый поток из Thread-1
                for (int i = 0; i < 10; i++) {
                    singleThreadPool.submit(new MyRunnable(Thread.currentThread().getName()));
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                //Добавление в цикле 10 задач в фоновый поток из Thread-2
                for (int i = 0; i < 10; i++) {
                    singleThreadPool.submit(new MyRunnable(Thread.currentThread().getName()));
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                //Добавление в цикле 10 задач в фоновый поток из Thread-3
                for (int i = 0; i < 10; i++) {
                    singleThreadPool.submit(new MyRunnable(Thread.currentThread().getName()));
                }
            }
        }).start();


//        singleThreadPool.stop();
        singleThreadPool.terminate();
    }
}

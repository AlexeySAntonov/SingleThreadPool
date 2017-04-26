import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SingleThreadPool {
    private BlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(1000);
    private Runnable task;
    private Thread threadPool;
    private boolean terminate = false;


    public void submit(MyRunnable myRunnable) {
        System.out.println("Зашли в submit на synchronized");
        synchronized (runnables) {
            try {
                runnables.put(myRunnable);
                System.out.println("Задача добавлена");
                runnables.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        threadPool = new Thread(new Runnable() {
            public void run() {
                while (!Thread.interrupted()) {
                    synchronized (runnables) {
                        while (runnables.size() == 0) {
                            try {
                                runnables.wait();
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                    if ((task = runnables.poll()) != null && !terminate) {
                        //нет никаких прерываний - спокойно выполняем все таски
                        task.run();
                    } else if ((task = runnables.poll()) != null && terminate) {
                        //сработал terminate() выполняем только последнюю таску
                        task.run();
                        break;
                    } else if ((task = runnables.poll()) == null && terminate) {
                        //сработал terminate() и нет тасок - просто выходим из цикла
                        break;
                    } else {
                        break;
                    }
                }
            }
        });
        threadPool.start();
    }

    public void terminate() {
        System.out.println("Зашли в terminate()");
        stop();
        terminate = true;
        System.out.println("terminate() завершен");
    }

    public void stop() {
        System.out.println("Зашли в stop->interrupt()");
        threadPool.interrupt();
        System.out.println("stop->interrupt() завершен");
    }
}


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SingleThreadPool {
    private BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<Runnable>(10000);
    private Thread thread;
    private Runnable task;
    private boolean addPermission = true;

    public void start() {
        thread = new Thread(new Runnable() {
            public void run() {
                while (thread != null) {
                    if (!addPermission && tasks.size() == 0) {
                        terminate();
                    }
                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            try {
                                tasks.wait();
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                    if ((task = tasks.poll()) != null) {
                        task.run();
                        System.out.println("Size of queue: " + tasks.size());
                    }
                }
            }
        });
        thread.start();
    }

    public void submit(Runnable task, String threadName) {
        synchronized (tasks) {
            if (addPermission) {
                tasks.add(task);
                tasks.notify();
                System.out.println(threadName + " added new task!");
            }
        }
    }

    public synchronized void terminate() {
        if (thread != null) {
            Thread stub = thread;
            thread = null;
            stub.interrupt();
            addPermission = false;
            System.out.println("|TERMINATE|TERMINATE|TERMINATE|TERMINATE|TERMINATE|");
        }
    }

    public synchronized void stop() {
        addPermission = false;
        System.out.println("|--STOP--|--STOP--|--STOP--|--STOP--|--STOP--|");
    }
}

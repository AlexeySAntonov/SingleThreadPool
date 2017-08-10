import java.util.concurrent.atomic.AtomicInteger;

public class MyRunnable implements Runnable {
    private static AtomicInteger taskCount = new AtomicInteger(0);
    private final int id = taskCount.incrementAndGet();
    private String parentThreadName;


    public MyRunnable(String parentThreadName) {
        this.parentThreadName = parentThreadName;
    }

    public void run() {
        System.out.println(parentThreadName + " --> " +  "| Task number - " + id);
    }
}

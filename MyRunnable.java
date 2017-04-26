public class MyRunnable implements Runnable {
    private static int taskCount = 0;
    private final int id = taskCount++;
    private String parentThreadName;

    public MyRunnable(String parentThreadName) {
        this.parentThreadName = parentThreadName;
    }

    public void run() {
        System.out.println(parentThreadName + "->" + Thread.currentThread().getName() + " | Task number - " + id);
    }
}

public class MyRunnable implements Runnable {
    private static int taskCount = 0;
    private final int id = taskCount++;

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task number - " + id);
    }
}

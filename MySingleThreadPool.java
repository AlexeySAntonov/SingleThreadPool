

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MySingleThreadPool {
    private BlockingQueue<MyRunnable> myRunnables = new ArrayBlockingQueue<MyRunnable>(100);
    private Runnable task;
    private boolean interruptedOneDone = false;
    private boolean interruptedAllDone = false;
    private Scanner in = new Scanner(System.in);
    private boolean f = true;

    public static void main(String[] args) {

        MySingleThreadPool mySingleThreadPool = new MySingleThreadPool();
        mySingleThreadPool.laMenu();

    }

    public void laMenu() {
        while (f) {
            System.out.println("[1] - Запустить фоновый поток на выполнение задач");
            System.out.println("[2] - добавить новую задачу в очередь");
            System.out.println("[3] - остановить пул, завершив все задачи в очереди");
            System.out.println("[4] - остановить пул, завершив только последнюю задачу");
            System.out.println("[5] - выход");

            switch (in.nextInt()) {
                case 1:
                    start();
                    break;
                case 2:
                    MyRunnable myRunnable = new MyRunnable();
                    submit(myRunnable);
                    break;
                case 3:
                    stop();
                    break;
                case 4:
                    terminate();
                    break;
                case 5:
                    f = false;
                    break;
            }
        }
    }

    public void submit(MyRunnable myRunnable) {
        try {
            myRunnables.put(myRunnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        interruptedOneDone = false;
        interruptedAllDone = false;

        final Thread thread = new Thread(new Runnable() {
            public void run() {
                while (!interruptedOneDone) {
                    if (interruptedAllDone) {
//                        for (int i = 0; i < myRunnables.size(); i++) {
                        while ((task = myRunnables.poll()) != null) {
                            System.out.println("Работает цикл на довыполнение задач |" + " размер очереди: " + myRunnables.size());
                            task.run();
                        }
                        terminate();
                    } else {
                        if ((task = myRunnables.poll()) != null) {
                            System.out.println("Работает основной цикл задач |" + " размер очереди: " + myRunnables.size());
                            task.run();
                        }
                    }
                }
            }
        });
        thread.start();
    }

    public void terminate() {
        interruptedOneDone = true;
    }

    public void stop() {
        interruptedAllDone = true;
    }
}

package concurrent_tasks;

import enumeration.Region;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by AZagorskyi on 10.04.2017.
 */
public class ExecuteTimerTask extends TimerTask{
    public void run() {
        SynchronousQueue<Region> queue = new SynchronousQueue<Region>();
        (new Thread(new PutInQueueWeatherDataRunnable_Producer(queue))).start();
        (new Thread(new TakeOutQueueWeatherDataRunnable_Consumer(queue))).start();
    }
}

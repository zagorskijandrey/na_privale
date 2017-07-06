package concurrent_tasks;

import enumeration.RegionEnum;

import java.util.TimerTask;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by AZagorskyi on 10.04.2017.
 */
public class ExecuteTimerTask extends TimerTask{
    public void run() {
        SynchronousQueue<RegionEnum> queue = new SynchronousQueue<RegionEnum>();
        (new Thread(new PutInQueueWeatherDataRunnable_Producer(queue))).start();
        (new Thread(new TakeOutQueueWeatherDataRunnable_Consumer(queue))).start();
    }
}

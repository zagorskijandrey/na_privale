package concurrent_tasks;

import enumeration.Region;

import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 07.04.2017.
 */
public class PutInQueueWeatherDataRunnable_Producer implements Runnable{
    private static Logger log = Logger.getLogger(PutInQueueWeatherDataRunnable_Producer.class.getName());

    private SynchronousQueue<Region> queue = null;

    public PutInQueueWeatherDataRunnable_Producer(SynchronousQueue<Region> queue){
        this.queue = queue;
    }
    public void run() {
        try {
            for (Region city : Region.values()){
                this.queue.put(city);
                log.info("Producer " + city);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

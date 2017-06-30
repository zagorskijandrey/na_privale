package concurrent_tasks;

import enumeration.Region;

import java.text.ParseException;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 10.04.2017.
 */
public class TakeOutQueueWeatherDataRunnable_Consumer implements Runnable{
    private static Logger log = Logger.getLogger(TakeOutQueueWeatherDataRunnable_Consumer.class.getName());

    private SynchronousQueue<Region> queue = null;

    public TakeOutQueueWeatherDataRunnable_Consumer(SynchronousQueue<Region> queue){
        this.queue = queue;
    }

    public void run() {
        try {
            int count = 0;
            while (count < Region.values().length){
                RefreshInformationInDataBase refresh = new RefreshInformationInDataBase();
                Region region = this.queue.take();
                refresh.executeForRegion(region);
                log.info("Consumer " + region);
                ++count;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

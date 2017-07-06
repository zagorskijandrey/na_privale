package concurrent_tasks;

import enumeration.RegionEnum;

import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 07.04.2017.
 */
public class PutInQueueWeatherDataRunnable_Producer implements Runnable{
    private static Logger log = Logger.getLogger(PutInQueueWeatherDataRunnable_Producer.class.getName());

    private SynchronousQueue<RegionEnum> queue = null;

    PutInQueueWeatherDataRunnable_Producer(SynchronousQueue<RegionEnum> queue){
        this.queue = queue;
    }
    public void run() {
        try {
            for (RegionEnum regionEnum : RegionEnum.values()){
                this.queue.put(regionEnum);
                log.info("Producer " + regionEnum);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

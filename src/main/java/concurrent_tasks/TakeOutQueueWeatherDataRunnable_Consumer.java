package concurrent_tasks;

import enumeration.RegionEnum;
import service.save.SaveWeatherData;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 10.04.2017.
 */
public class TakeOutQueueWeatherDataRunnable_Consumer implements Runnable{
    private static Logger log = Logger.getLogger(TakeOutQueueWeatherDataRunnable_Consumer.class.getName());

    private SynchronousQueue<RegionEnum> queue = null;

    TakeOutQueueWeatherDataRunnable_Consumer(SynchronousQueue<RegionEnum> queue){
        this.queue = queue;
    }

    public void run() {
        try {
            int count = 0;
            while (count < RegionEnum.values().length){
                RefreshInformationInDataBase refresh = new RefreshInformationInDataBase();
                RegionEnum regionEnum = this.queue.take();
                refresh.executeForRegion(regionEnum);
                log.info("Consumer " + regionEnum);
                ++count;
            }
            SaveWeatherData saveWeather = new SaveWeatherData();
            saveWeather.saveMoonDataFromDynamicJSON();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

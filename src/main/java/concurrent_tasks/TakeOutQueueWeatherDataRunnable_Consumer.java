package concurrent_tasks;

import dao.weather.WeatherDaoSave;
import enumeration.RegionEnum;
import dao.weather.WeatherDaoSaveImpl;

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
            WeatherDaoSave saveWeather = new WeatherDaoSaveImpl();
            saveWeather.saveMoonDataFromDynamicJSON();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

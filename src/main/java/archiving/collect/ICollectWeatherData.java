package archiving.collect;

import java.util.List;

/**
 * Created by andrey on 12.04.2017.
 */
public interface ICollectWeatherData {
    List<Integer> getPressures();
    int getWindRout();
    int getWindSpeed();
}

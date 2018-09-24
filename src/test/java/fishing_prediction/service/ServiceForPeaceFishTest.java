package fishing_prediction.service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by andrey on 20.11.2017.
 */
public class ServiceForPeaceFishTest {
    private ServiceForPeaceFish serviceForPeaceFish;

    @Before
    public void init(){
        serviceForPeaceFish = new ServiceForPeaceFish();
    }

    @After
    public void tearDown(){
        serviceForPeaceFish = null;
    }

    @Test
    public void calls(){
        int moonDay = 2;
        int month = 8;

        List<Integer> pressures = new ArrayList<Integer>(5);
        pressures.add(700);
        pressures.add(710);
        pressures.add(720);
        pressures.add(730);
        pressures.add(740);

        int windRout = 300;
        int windSpeed = 16;


        Map<String, Integer> mapMarks = serviceForPeaceFish.calculatePeacePrediction(moonDay,
                month, pressures, windRout, windSpeed);
        int count = 0;
        int sum = 0;
        for(Map.Entry<String, Integer> pare: mapMarks.entrySet()){
            ++count;
            sum = sum + pare.getValue();
        }

        assertEquals(6, sum/count);
    }
}

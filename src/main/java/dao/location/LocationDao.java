/**
 * Created by AZagorskyi on 06.11.2017.
 */
package dao.location;

import model.Hamlet;
import java.util.Map;

public interface LocationDao {
    Map<Integer, String> getCountries();
    Map<Integer, String> getProvincesByCountryId(Integer id);
    Map<Integer, String> getRegionsByProvinceId(Integer id);
    Map<Integer, String> getHamletsByRegionId(Integer id);
}

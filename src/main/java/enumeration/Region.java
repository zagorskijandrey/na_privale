package enumeration;

import java.util.HashMap;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
public enum Region {
    Vinnytsia("Vinnytsia"), Dnipropetrovsk("Dnipropetrovsk"), Donetsk("Donetsk"), Zhytomyr("Zhytomyr"),
    Zaporizhzhia("Zaporizhzhia"), IvanoFrankivsk("IvanoFrankivsk"), Kyiv("Kyiv"), Kirovohrad("Kirovohrad"),
    Luhansk("Luhansk"), Lutsk("Lutsk"), Lviv("Lviv"), Mykolaiv("Mykolaiv"), Odesa("Odesa"), Poltava("Poltava"),
    Rivne("Rivne"), Sevastopol("Sevastopol"), Simferopol("Simferopol"), Sumy("Sumy"), Ternopil("Ternopil"),
    Uzhhorod("Uzhhorod"), Kharkiv("Kharkiv"), Kherson("Kherson"), Khmelnytskyi("Khmelnytskyi"),
    Cherkasy("Cherkasy"), Chernivtsi("Chernivtsi"), Chernihiv("Chernihiv");

    String city;

    Region(String city) {
        this.city = city;
    }

    public String getCity(){
        return city;
    }
}

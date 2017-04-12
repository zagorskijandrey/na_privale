package enumeration;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
public enum Region {
    Vinnytsya("Vinnyts’ka Oblast’"), Dnipropetrovsk("Dnipropetrovska Oblast'"), Donetsk("Donets’ka Oblast’"),
    Zhytomyr("Zhytomyrs’ka Oblast’"), Zaporizhzhya("Zaporiz’ka Oblast’"), IvanoFrankivsk("Ivano-Frankivs’ka Oblast’"),
    Kiev("Kyyivs’ka Oblast’"), Kirovohrad("Kirovohrads’ka Oblast’"), Luhansk("Luhans’ka Oblast’"), Lutsk("Volyns’ka Oblast’"),
    Lviv("L’vivs’ka Oblast’"), Mykolaiv("Mykolayivs’ka Oblast’"), Odessa("Odes’ka Oblast’"), Poltava("Poltavs’ka Oblast’"),
    Rivne("Rivnens’ka Oblast’"), Sevastopol("Sevastopol"), Simferopol("Simferopol"), Sumy("Sums’ka Oblast’"),
    Ternopil("Ternopil’s’ka Oblast’"), Uzhhorod("Zakarpats’ka Oblast’"), Kharkiv("Kharkivs’ka Oblast’"),
    Kherson("Khersons’ka Oblast’"), Khmelnytskyi("Khmel’nyts’ka Oblast’"), Cherkasy("Cherkas’ka Oblast’"),
    Chernivtsi("Chernivets’ka Oblast’"), Chernihiv("Chernihivs’ka Oblast’");

    String region;

    Region(String region) {
        this.region = region;
    }

    public String getRegion(){
        return region;
    }
}

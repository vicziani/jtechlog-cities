package hu.jtechlog.cities;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class TemperatureGateway {

    private String url;

    public TemperatureGateway(@Value("${cities.idokep.url}") String url) {
        this.url = url;
    }

    public String getTemperature(String city) {
        log.debug("Get temperature for: " + city);
        try {
            String postfixedUrl = url + city;
            Document doc = Jsoup.connect(postfixedUrl).get();
            Elements elements = doc.select(".harminchat > div:nth-child(1) > div:nth-child(3)");
            String temp = elements.get(0).text();
            log.debug("Got temperature: " + temp);
            return temp;
        }
        catch (IOException ioe) {
           throw new IllegalStateException("Can not get temperature data", ioe);
        }
    }

}

package com.fujitsu.deliverycostcalc.scheduler;

import com.fujitsu.deliverycostcalc.exception.MissingXmlTagException;
import com.fujitsu.deliverycostcalc.service.WeatherDataFetcherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
public class WeatherDataScheduler {
    private static final String WEATHER_DATA_SCHEDULER_CRON_EXPRESSION = "0,15,30,45 * * * * *";

    private final WeatherDataFetcherService weatherDataFetcherService;

    public WeatherDataScheduler(WeatherDataFetcherService weatherDataFetcherService) {
        this.weatherDataFetcherService = weatherDataFetcherService;
    }

    @Scheduled(cron = WEATHER_DATA_SCHEDULER_CRON_EXPRESSION)
    public void fetchWeatherData() throws ParserConfigurationException, IOException, SAXException,
            MissingXmlTagException {

        weatherDataFetcherService.fetchWeatherData();
    }
}

package com.fujitsu.deliverycostcalc.service;

import com.fujitsu.deliverycostcalc.entity.City;
import com.fujitsu.deliverycostcalc.entity.WeatherData;
import com.fujitsu.deliverycostcalc.exception.EmptyXmlTagValueException;
import com.fujitsu.deliverycostcalc.exception.MissingXmlTagException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class WeatherDataFetcherService {
    private static final String WEATHER_DATA_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private static final String TIMESTAMP_TAG = "timestamp";
    private static final String STATION_TAG = "station";
    private static final String STATION_NAME_TAG = "name";
    private static final String WMO_CODE_TAG = "wmocode";
    private static final String AIR_TEMPERATURE_TAG = "airtemperature";
    private static final String WIND_SPEED_TAG = "windspeed";
    private static final String WEATHER_PHENOMENON_TAG = "phenomenon";

    private final CityService cityService;
    private final WeatherDataService weatherDataService;

    public WeatherDataFetcherService(CityService cityService, WeatherDataService weatherDataService) {
        this.cityService = cityService;
        this.weatherDataService = weatherDataService;
    }

    private static String getXmlTagValue(Element element, String tagName)
            throws MissingXmlTagException, EmptyXmlTagValueException {

        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) {
            throw new MissingXmlTagException(tagName);
        }

        String value = nodeList.item(0).getTextContent().trim();
        if (value.isEmpty()) {
            throw new EmptyXmlTagValueException(tagName);
        }

        return value;
    }

    private static String tryReadTagValue(Element element, String tagName, StringBuilder errorCollector)
            throws MissingXmlTagException {

        try {
            return getXmlTagValue(element, tagName);
        } catch (EmptyXmlTagValueException exception) {
            errorCollector.append(exception.getMessage()).append("\n");
            return null;
        }
    }

    @Transactional
    public void fetchWeatherData() throws ParserConfigurationException, IOException, SAXException,
            MissingXmlTagException {

        HashMap<String, City> citiesByStationName = cityService.getCitiesMappedByStationName();
        Set<String> stationNames = new HashSet<>(citiesByStationName.keySet());

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(WEATHER_DATA_URL);
        doc.getDocumentElement().normalize();

        String timestamp = doc.getDocumentElement().getAttribute(TIMESTAMP_TAG);

        NodeList stations = doc.getElementsByTagName(STATION_TAG);

        for (int i = 0; i < stations.getLength(); i++) {
            Node node = stations.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String stationName;
                try {
                    stationName = getXmlTagValue(element, STATION_NAME_TAG);
                } catch (EmptyXmlTagValueException exception) {
                    // TODO: Some kind of logging
                    continue;
                }

                City city = citiesByStationName.get(stationName);
                if (city != null) {
                    StringBuilder errorMessages = new StringBuilder();

                    String wmoCode = tryReadTagValue(element, WMO_CODE_TAG, errorMessages);
                    String airTemperature = tryReadTagValue(element, AIR_TEMPERATURE_TAG, errorMessages);
                    String windSpeed = tryReadTagValue(element, WIND_SPEED_TAG, errorMessages);
                    String phenomenon = tryReadTagValue(element, WEATHER_PHENOMENON_TAG, errorMessages);

                    WeatherData data = new WeatherData(
                            timestamp,
                            airTemperature,
                            windSpeed,
                            phenomenon,
                            city,
                            !errorMessages.isEmpty(),
                            errorMessages.toString()
                    );

                    weatherDataService.save(data);

                    if (wmoCode != null) {
                        city.refreshWMOCode(wmoCode);
                        cityService.save(city);
                    }

                    stationNames.remove(stationName);
                    if (stationNames.isEmpty()) {
                        break;
                    }
                }
            }
        }
    }
}

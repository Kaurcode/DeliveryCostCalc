package com.fujitsu.deliverycostcalc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WeatherDataFetcher {
    private static final String WEATHER_DATA_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private static final String TIMESTAMP_TAG = "timestamp";
    private static final String STATION_TAG = "station";

    private static final String STATION_NAME_TAG = "name";
    private static final String WMO_CODE_TAG = "wmocode";
    private static final String AIR_TEMPERATURE_TAG = "airtemperature";
    private static final String WIND_SPEED_TAG = "windspeed";
    private static final String WEATHER_PHENOMENON_TAG = "phenomenon";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        // readXML();
    }

    private static String getXmlElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        return (0 < nodeList.getLength()) ? nodeList.item(0).getTextContent() : "N/A";
    }

    public static void readXML(HashMap<String, City> cities) throws ParserConfigurationException, IOException, SAXException {
        Set<String> stationNames = new HashSet<>(cities.keySet());

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(WEATHER_DATA_URL);
        doc.getDocumentElement().normalize();

        String timestamp = doc.getDocumentElement().getAttribute(TIMESTAMP_TAG);

        NodeList stations = doc.getElementsByTagName(STATION_TAG);

        for (int i = 0; i < stations.getLength(); i++) {
            Node node = stations.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String stationName = getXmlElementValue(element, STATION_NAME_TAG);

                City city = cities.get(stationName);
                if (city != null) {
                    String wmoCode = getXmlElementValue(element, WMO_CODE_TAG);
                    String airTemperature = getXmlElementValue(element, AIR_TEMPERATURE_TAG);
                    String windSpeed = getXmlElementValue(element, WIND_SPEED_TAG);
                    String phenomenon = getXmlElementValue(element, WEATHER_PHENOMENON_TAG);

                    WeatherData data = new WeatherData(
                            timestamp,
                            airTemperature,
                            windSpeed,
                            phenomenon
                    );

                    city.refreshWMOCode(wmoCode);
                    city.addWeatherData(data);

                    stationNames.remove(stationName);
                    if (stationNames.isEmpty()) {
                        break;
                    }
                }
            }
        }
    }

    private static String readResponseBody(HttpURLConnection connection) throws IOException {
        StringBuilder responseBody = new StringBuilder();

        try (InputStream inputStream = connection.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBody.append(line).append("\n");
            }
        }

        return responseBody.toString();
    }

    private static String fetchData() throws URISyntaxException, IOException, HttpResponseException {
        URI uri = new URI(WEATHER_DATA_URL);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        String xmlResponse;

        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                xmlResponse = readResponseBody(connection);
            } else {
                throw new HttpResponseException("Response code: " + responseCode);
            }
        } finally {
            connection.disconnect();
        }

        return xmlResponse;
    }
}

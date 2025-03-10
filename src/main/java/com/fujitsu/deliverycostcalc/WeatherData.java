package com.fujitsu.deliverycostcalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class WeatherData {
    private final static String WEATHER_DATA_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    public static void main(String[] args) throws URISyntaxException, IOException, HttpResponseException {
        System.out.println(fetchData());
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

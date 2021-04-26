package hu.meiit.sweng.solid.singleresponsibility.http;

import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
public class HttpAdapterUrlConnection implements HttpAdapter {

    @Override
    public InputStream doGet(String requestUrl, String body) {
        URL url;
        try {
            url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            return connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}


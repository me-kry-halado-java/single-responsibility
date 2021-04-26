package hu.meiit.sweng.solid.singleresponsibility.http;

import java.io.InputStream;

public interface HttpAdapter {
        InputStream doGet(String url, String body);
}

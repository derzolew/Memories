package com.memories.maxvi.memories.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpClient {
    public String get(String url) throws Exception {
        return doRequest(url, "GET", null, null);
    }

    public String get(String url, Map<String, String> header) throws Exception {
        return doRequest(url, "GET", header, null);
    }

    public String post(String url, Map<String, String> header, String body) throws Exception {
        return doRequest(url, "POST", header, body);
    }

    private String doRequest(String url, String type, Map<String, String> header, String body) throws Exception {
        String response = null;
        HttpURLConnection connection = null;
        try {
            URL reqUrl = new URL(url);
            connection = (HttpURLConnection) reqUrl.openConnection();
            connection.setRequestMethod(type);
            if (header != null) {
                for (String key : header.keySet()) {
                    connection.addRequestProperty(key, header.get(key));
                }
            }
            if (body != null) {
                applyBody(connection, body);
            }

            InputStream inputStream;

            boolean isSuccess = connection.getResponseCode() >= 200 && connection.getResponseCode() < 300;
            if (isSuccess) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            response = stringBuilder.toString();

            inputStream.close();

            if (!isSuccess) {
                System.out.println("http exception = " + response);
                throw new Exception(response);
            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    private void applyBody(HttpURLConnection httpURLConnection, String body) throws Exception {
        byte[] outputInBytes = body.getBytes("UTF-8");
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(outputInBytes);
        outputStream.close();
    }
}

package Requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutRequest {

    private HttpURLConnection connect;
    private final int TIMEOUT = 5000;

    //return message or error code?
    public void put(String id) {
        try {
            StringBuffer response = new StringBuffer();
            String host = "http://localhost:8080/api/order/cancel/" + id;
            System.out.println(host);
            URL url = new URL(host);
            connect = (HttpURLConnection) url.openConnection();

            //GET
            connect.setRequestMethod("PUT");
            connect.setConnectTimeout(TIMEOUT);
            connect.setReadTimeout(TIMEOUT);

            int status = connect.getResponseCode();

            String ln;
            BufferedReader read;

            if (status > 299) {
                read = new BufferedReader(new InputStreamReader(connect.getErrorStream()));
                while ((ln = read.readLine()) != null) {
                    response.append(ln);
                }
                read.close();
            } else {
                read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                while ((ln = read.readLine()) != null) {
                    response.append(ln);
                }
            }
            System.out.println(response.toString());

        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            connect.disconnect();
        }
    }
}

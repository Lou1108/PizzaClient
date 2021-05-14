package Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;



public class PostRequests {

    private HttpURLConnection connect;

    public String post(String[] toPost) {


        try {
            URL url = new URL("http://localhost:8080/api/order");
            connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setDoOutput(true);

            JSONObject json = new JSONObject();
            String jsonText =   "{\"id\": 1," +
                                " \"customerId\": "+ toPost[3] +
                                ",\"status\": \"In Progress\"," +
                                "\"orderedAt\": \"2012-04-23T18:05:32.511Z\"," +
                                "\"takeaway\": "+ toPost[1] +", \"payment_type\": \"cash\"," +
                                "\"delivery_address\": " +
                                    " { \"street\": \"Paul-Henri Spaaklaan 1\", " +
                                    "\"city\": \"Maastricht\"," +
                                    "\"country\": \"Netherlands\", " +
                                    "\"zipcode\": \"6229 EN\" }," +
                                "\"pizzas\": [{ \"pizza_id\": " + toPost[0] + " }]" +
                                ", \"note\": \"No Onions\" }";


            StringBuffer response = new StringBuffer();

            try (OutputStream os = connect.getOutputStream()) {
                byte[] input = jsonText.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            System.out.println(connect.getOutputStream().toString());

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
            return response.toString();



        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        return "POST Request failed";
    }
}












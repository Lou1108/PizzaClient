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


            String jsonText =   "{\"customerId\": "+ toPost[0] +
                                ",\"status\": \"In Progress\"," +
                                "\"takeaway\": "+ toPost[1] +", " +
                                "\"payment_type\": "+ toPost[2] +"," +
                                "\"delivery_address\": " +
                                    " { \"street\": "+ toPost[3] +", " +
                                    "\"city\": "+ toPost[4] +"," +
                                    "\"country\": "+ toPost[5] +", " +
                                    "\"zipcode\": "+ toPost[6] +" }," +
                                "\"pizzas\": [{ \"pizza_id\": " + toPost[7] + " }]" +
                                ", \"note\": "+toPost[8]+" }";
/*
String jsonText =   "{" +
                    //"\"id\": 1," +
                                " \"customerId\": "+ toPost[3] +
                                ",\"status\": \"In Progress\"," +
                              //  "\"orderedAt\": \"2012-04-23T18:05:32.511Z\"," +
                                "\"takeaway\": "+ toPost[1] +", \"payment_type\": \"cash\"," +
                                "\"delivery_address\": " +
                                    " { \"street\": \"Paul-Henri Spaaklaan 1\", " +
                                    "\"city\": \"Maastricht\"," +
                                    "\"country\": \"Netherlands\", " +
                                    "\"zipcode\": \"6229 EN\" }," +
                                "\"pizzas\": [{ \"pizza_id\": " + toPost[0] + " }]" +
                                ", \"note\": \"No Onions\" }";


                                "\"delivery_address\": " +
                                    " { \"street\": "+ toPost[3] +", " +
                                    "\"city\": "+ toPost[4] +"," +
                                    "\"country\": "+ toPost[5] +", " +
                                    "\"zipcode\": "+ toPost[6] +" }," +
 */

            StringBuffer response = new StringBuffer();

            try (OutputStream os = connect.getOutputStream()) {
                byte[] input = jsonText.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


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
                return "Your order was submitted successfully.";

            }

            return response.toString();



        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        return "POST Request failed";
    }
}












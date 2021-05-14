package Requests;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;



public class PostRequests {

    private HttpURLConnection connect;

    public void post(String[] toPost) {


        try {
            URL url = new URL("http://localhost:8080/api/order");
            connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json; utf-8");
            connect.setRequestProperty("Accept", "application/json");
            connect.setDoOutput(true);

            Map<String, String> arg = new HashMap<>();
            arg.put("pizzas", toPost[0]);
            arg.put("takeaway", toPost[1]);
            arg.put("payment_type", toPost[2]);
            arg.put("customer_id", toPost[3]);
            arg.put("delivery_address", toPost[4]);
            //TODO continue here, add the actual address, notes


            System.out.println(arg);

            StringJoiner post = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arg.entrySet())
                post.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));

            byte[] out = post.toString().getBytes(StandardCharsets.UTF_8);
            System.out.println(out);

            try (OutputStream os = connect.getOutputStream()) {
                os.write(out);
            }
            System.out.println(connect.getOutputStream().toString());

            //read response



        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }

    }
}












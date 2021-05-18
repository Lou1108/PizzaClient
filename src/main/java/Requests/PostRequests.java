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
            URL url = new URL("https://safe-savannah-12795.herokuapp.com/api/order");
            connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setDoOutput(true);

            String[] pizzas = toPost[7].split(" ");
            String pizzaList = "";
            for(int i=0; i<pizzas.length-1;i++){
                pizzaList += "{ \"pizza_id\": " + pizzas[i] + " },";
            }
                pizzaList += "{ \"pizza_id\": " + pizzas[pizzas.length-1] + " }";

            String jsonText =   "{\"customerId\": "+ toPost[0] + ","+
                                "\"takeaway\": "+ toPost[1] +", " +
                                "\"payment_type\": \""+ toPost[2] +"\"," +
                                "\"delivery_address\": " +
                                    " { \"street\": \""+ toPost[3] +"\", " +
                                    "\"city\": \""+ toPost[4] +"\", " +
                                    "\"country\": \""+ toPost[5] +"\", " +
                                    "\"zipcode\": "+ toPost[6] +" }," +
                                "\"pizzas\": [ " + pizzaList +" ]" +
                                ", \"note\": \""+toPost[8]+"\" }";


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
                    System.out.println("ERROR");
                }
                read.close();
            } else {
                read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                while ((ln = read.readLine()) != null) {
                    response.append(ln);
                }
                //return "Your order was submitted successfully.";
            }
            System.out.println(status);
            return parsePost(response.toString());


        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        return "POST Request failed";
    }

    String parsePost(String response){
        if (response.charAt(0) != '{'){
            return response;
        }

        System.out.println(response);
        JSONObject pizza = new JSONObject(response);

        int id = pizza.getInt("id");
       // String status = pizza.getString("delivery_time");


        String output = "Your order was submitted successfully." + "\n";

        output += ("Your order number is : " + id + "\n" +
                "The delivery time is : " + "status" + "\n"  +"\n");

        // System.out.println(output);
        System.out.println("out" + output);
        return output;
    }


}












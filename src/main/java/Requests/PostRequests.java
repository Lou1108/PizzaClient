package Requests;

import org.json.JSONObject;

import java.io.*;
import java.net.*;


public class PostRequests {

    private HttpURLConnection connect;

    public String post(String host, String[] toPost) {


        try {
            URL url = new URL(host);
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
                }
                read.close();
            } else {
                read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                while ((ln = read.readLine()) != null) {
                    response.append(ln);
                }
            }
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

        JSONObject pizza = new JSONObject(response);

        int id = pizza.getInt("id");
        String output = "Your order was submitted successfully." + "\n";
        output += ("Your order number is : " + id);

        return output;
    }


}












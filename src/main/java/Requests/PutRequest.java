package Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutRequest {

    private HttpURLConnection connect;
    private final int TIMEOUT = 5000;

    //return message or error code?
    public String put(String id) {
        try {
            StringBuffer response = new StringBuffer();
            String host = "https://safe-savannah-12795.herokuapp.com/api/order/cancel/" + id;
            System.out.println(host);
            URL url = new URL(host);
            connect = (HttpURLConnection) url.openConnection();

            //PUT
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
            return parsePut(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connect.disconnect();
        }
        return "PUT Request Failed";
    }


   public String parsePut(String response){
       if (response.charAt(0) != '{'){
           return response;
       }

      // JSONArray pizzas = new JSONArray(response);
       String output = "";
      // for (int i = 0; i<pizzas.length();i++) {
           JSONObject pizza = new JSONObject(response);

           //TODO             int pizzaId = pizza.getInt("pizza_id");
           int orderId = pizza.getInt("id");
           String status = pizza.getString("status");
           output +=  "ID: " + orderId + " \n" + "status: " +status;
      // }
       return output;
   }


}

package Requests;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutRequest {

    private HttpURLConnection connect;
    private final int TIMEOUT = 5000;

    /**
     * initializes the APIs put request
     * @param host the url
     * @return the error message or response from completing the request
     */
    public String put(String host) {
        try {
            StringBuffer response = new StringBuffer();
            //String host = "https://safe-savannah-12795.herokuapp.com/api/order/cancel/" + id;
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
                return "The order was already canceled or delivered.";
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

    /**
     * @param response output message shown to the user
     * @return the output message shown to the user
     */
   public String parsePut(String response){
       if (response.charAt(0) != '{'){
           return response;
       }

       String output = "The order with ID ";
           JSONObject pizza = new JSONObject(response);

           int orderId = pizza.getInt("id");
           String status = pizza.getString("status");
           output += orderId + " \n" + " was successfully " +status;
       return output;
   }


}

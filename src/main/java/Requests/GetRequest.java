package Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequest {

    private HttpURLConnection connect;
    private final int TIMEOUT = 5000;

    public String get(String toGet) {
        try {

            StringBuffer response = new StringBuffer();
            String host = "http://localhost:8080/api/" + toGet;
            URL url = new URL(host);
            connect = (HttpURLConnection) url.openConnection();

            //GET
            connect.setRequestMethod("GET");
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
            return response.toString();

        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            connect.disconnect();
        }

        return "GET Request Failed";
    }


    public String parsePizzaMenu(String response){
        JSONArray pizzas = new JSONArray(response);
        String output = "";
        for (int i = 0; i<pizzas.length();i++){
            JSONObject pizza = pizzas.getJSONObject(i);

            //TODO             int pizzaId = pizza.getInt("pizza_id");
            int pizzaId = pizza.getInt("id");
            String name = pizza.getString("name");
            boolean veg = pizza.getBoolean("vegeterian");
            double price = pizza.getDouble("price");

            output += ("PizzaID: " + pizzaId + "\n" + "name: " + name
                    + "\n" + "vegeterian: " + veg + "\n" + "price: " +price + "\n" + "\n");
        }
        return output;
    }

    public String parsePizzaInfo(String response){
        JSONArray pizzas = new JSONArray(response);
        String output = "";
        for (int i = 0; i<pizzas.length();i++){
            JSONObject pizza = pizzas.getJSONObject(i);

            //TODO             int pizzaId = pizza.getInt("pizza_id");
            int pizzaId = pizza.getInt("id");
            String name = pizza.getString("name");
            boolean veg = pizza.getBoolean("vegeterian");
            double price = pizza.getDouble("price");
            JSONArray toppings = pizza.getJSONArray("toppings");
            String top = "";
            for(int j = 0; j < toppings.length();j++){
                top += toppings.getString(i);
            }


            output += ("PizzaID: " + pizzaId + "\n" + "name: " + name
                    + "\n" + "vegeterian: " + veg + "\n" + "price: " +price + "\n" + "toppings: " + top + "\n"  + "\n");
        }
        return output;
    }

    public String parseOrderHistory(String response){
        JSONArray pizzas = new JSONArray(response);
        String output = "";
        for (int i = 0; i<pizzas.length();i++){
            JSONObject pizza = pizzas.getJSONObject(i);

            //TODO             int pizzaId = pizza.getInt("pizza_id");
            int pizzaId = pizza.getInt("id");
            //TODO             int pizzaId = pizza.getInt("customer_id");
            int customer = pizza.getInt("customerId");
            String status = pizza.getString("status");
            String time = pizza.getString("orderedAt");
            boolean takeaway = pizza.getBoolean("takeaway");
            String payment = pizza.getString("paymentType");
            JSONObject delivery = pizza.getJSONObject("deliveryAddress");



            output += ("PizzaID: " + pizzaId + "\n" + "customer_id" + customer
                    + "\n" + "status: " + status + "\n" + "ordered_at: " +time
                    + "\n" + "takeaway: " + takeaway + "\n"  + "payment type: " + payment
                    + "\n" + "delivery address" + delivery + "\n"  +"\n");
        }
        return output;

    }

}





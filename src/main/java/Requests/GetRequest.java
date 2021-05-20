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
            String host = toGet;
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
            System.out.println(response);
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
        if (response.charAt(0) != '['){
            return response;
        }

        JSONArray pizzas = new JSONArray(response);
        String output = "";
        for (int i = 0; i<pizzas.length();i++){
            JSONObject pizza = pizzas.getJSONObject(i);

            //TODO      int pizzaId = pizza.getInt("pizza_id");
            int pizzaId = pizza.getInt("pizza_id");
            String name = pizza.getString("name");
            boolean veg = pizza.getBoolean("vegetarian");  //TODO vegetarian instead of vegeterian
            double price = pizza.getDouble("price");

            output += ("PizzaID: " + pizzaId + "\n" + "name: " + name
                    + "\n" + "vegetarian: " + veg + "\n" + "price: " +price + "\n" + "\n");
        }
        return output;
    }

    public String parsePizzaInfo(String response){
        if (response.charAt(0) != '['){
            return response;
        }
        JSONArray pizzas = new JSONArray(response);

        String output = "";
        for (int i = 0; i<pizzas.length();i++){
            JSONObject pizza = pizzas.getJSONObject(i);

            int pizzaId = pizza.getInt("pizza_id");
            String name = pizza.getString("name");
            boolean veg = pizza.getBoolean("vegetarian");
            double price = pizza.getDouble("price");
            JSONArray toppings = pizza.getJSONArray("toppings");
            String top = "";
            for(int j = 0; j < toppings.length()-1;j++){
                top += toppings.getString(j) + ", ";
            }
            top += toppings.getString(toppings.length()-1);


            output += ("PizzaID: " + pizzaId + "\n" + "name: " + name
                    + "\n" + "vegetarian: " + veg + "\n" + "price: " +price + "\n" + "toppings: " + top + "\n"  + "\n");
        }
        return output;
    }

    public String parseOrderHistory(String response){
        if (response.charAt(0) != '['){
            return response;
        }
        JSONArray pizzas = new JSONArray(response);
        String output = "";
        for (int i = 0; i<pizzas.length();i++){
            JSONObject pizza = pizzas.getJSONObject(i);

            //TODO             int pizzaId = pizza.getInt("pizza_id");
            int pizzaId = pizza.getInt("pizza_id");
            //TODO             int pizzaId = pizza.getInt("customer_id");
            int customer = pizza.getInt("customer_id");
            String status = pizza.getString("status");
            String time = pizza.getString("ordered_at");
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

    public String[] pizzaIDs(String url){

        String response = get(url);
        if (response.charAt(0) != '['){
            return new String[] {};
        }
        JSONArray pizzas = new JSONArray(response);
        String[] output = new String[pizzas.length()];
        for (int i = 0; i<pizzas.length();i++) {
            JSONObject pizza = pizzas.getJSONObject(i);

            int pizzaId = pizza.getInt("pizza_id"); // "pizza_id" TODO for our program id is needed, for others' pizza_id
            String name = pizza.getString("name");
            output[i] =  "ID " + pizzaId + " : " +name;
        }
        return output;
    }

    public String parseDelTime(String response){
        if (response.charAt(0) != '{'){
            return response;
        }
        String output = "";
        int orderId = 0; int delTime = 0;
            JSONObject pizza = new JSONObject(response);
            if(pizza.toMap().containsKey("order_id")){
                orderId = pizza.getInt("order_id");
            }
            if(pizza.toMap().containsKey("delivery_time")){
                delTime = pizza.getInt("delivery_time");
            }

        output += ("Order number: " + orderId + "\n" + "Remaining delivery time: " + delTime
                + "\n"  +"\n");

        return output;
    }
}





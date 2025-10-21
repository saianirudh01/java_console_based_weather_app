package com.example;

import java.io.IOException;
import java.util.Scanner;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherApp {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = sc.nextLine();

        String apiKey = "9f1e29a87c864ef51f371443e070efdb";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        String responseBody = client.execute(request, response -> {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        });

        JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonObject main = json.getAsJsonObject("main");
        double temp = main.get("temp").getAsDouble();
        String weather = json.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

        System.out.println("\n🌤️ Weather in " + city + ": " + weather);
        System.out.println("🌡️ Temperature: " + temp + "°C");
    }
}


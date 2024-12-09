import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherQro {

    public static void main(String[] args) {
        // Coordenadas para Grammer
        double latitude = 20.641350163330447;
        double longitude = -100.44184624691523;

        // URL de la API
        String apiUrl = "http://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true";

        try {
            // URL y conexión
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Leer respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Convertir respuesta a JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject currentWeather = jsonResponse.getJSONObject("current_weather");
            double temperature = currentWeather.getDouble("temperature");

            // Imprimir la temperatura
            System.out.println("La temperatura actual en la Grammer es " + temperature + "°C");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

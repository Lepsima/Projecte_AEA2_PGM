package grup10.projecte_aea2_pgm.Classes;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Backend {

    String API_KEY_OPENAQ = System.getenv("OPENAQ_API_KEY");

    private JsonArray gsonParser(String body) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(body, JsonArray.class);

        return jsonArray;
    }
    public double[] obtainLatitudILongitud(String nomCiutat) {

        String headers[][] = {
                {"Accept", "application/json"},
                {"User-Agent", "App_Java_AEA2"}
        };

        Requester requester = new Requester( "GET", "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(nomCiutat.toLowerCase()) + "&format=json", headers);
        String response = requester.getResponseBody();
        System.out.println(response);

        JsonArray jsonArray = gsonParser(response);
        JsonObject ciutat = jsonArray.get(0).getAsJsonObject();
        double latitud = ciutat.get("lat").getAsDouble();
        double longitud = ciutat.get("lon").getAsDouble();

        return new double[]{latitud, longitud};
    }

    public List<Station> obtainEstacionsProperes(double latitud, double longitud, int radius) {
        String[][] headers = {
                {"X-API-Key", API_KEY_OPENAQ},
                {"Accept", "application/json"},
                {"User-Agent", "App_Java_AEA2"}
        };

        String url = "https://api.openaq.org/v3/locations?coordinates=" + latitud + "," + longitud + "&radius=" + radius;
        Requester requester = new Requester("GET", url, headers);
        String response = requester.getResponseBody();

        JsonObject root = JsonParser.parseString(response).getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results");

        List<Station> llistaEstacions = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject stationJson = results.get(i).getAsJsonObject();
            String nom = stationJson.get("name").getAsString();

            JsonArray sensorsJson = stationJson.getAsJsonArray("sensors");
            List<String> nomSensors = new ArrayList<>();

            for (int j = 0; j < sensorsJson.size(); j++) {
                JsonObject sensor = sensorsJson.get(j).getAsJsonObject();
                String tipusSensor = sensor.get("parameter").getAsJsonObject().get("displayName").getAsString();
                nomSensors.add(tipusSensor);
            }

            llistaEstacions.add(new Station(nom, nomSensors));
        }

        return llistaEstacions;
    }

}

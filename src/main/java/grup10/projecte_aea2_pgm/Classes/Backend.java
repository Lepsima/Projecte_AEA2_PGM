package grup10.projecte_aea2_pgm.Classes;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;

public class Backend {

    List<Object> data = new ArrayList<>();
    String API_KEY_OPENAQ = System.getenv("OPENAQ_API_KEY");

    private JsonArray gsonParser(String body) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(body, JsonArray.class);

        return jsonArray;
    }
    public List<Object> obtainLatitudILongitud(String nomCiutat) {

        String[][] headers = {
                {"Accept", "application/json"},
                {"User-Agent", "App_Java_AEA2"}
        };

        Requester requester = new Requester( "GET", "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(nomCiutat.toLowerCase()) + "&format=json", headers);
        String response = requester.getResponseBody();
        System.out.println(response);

        JsonArray jsonArray = gsonParser(response);
        JsonObject ciutat = jsonArray.get(0).getAsJsonObject();

        data.clear();
        data.add(ciutat.get("lat").getAsDouble());
        data.add(ciutat.get("lon").getAsDouble());

        return data;
    }

    public List<Object> obtainIPgeolocation() {

        String[][] headers = {
                {"Accept", "application/json"},
                {"User-Agent", "App_Java_AEA2"}
        };

        Requester requester = new Requester( "GET", "http://ip-api.com/json/", headers);
        String response = requester.getResponseBody();
        System.out.println(response);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        data.clear();
        data.add(jsonObject.get("lat").getAsDouble());
        data.add(jsonObject.get("lon").getAsDouble());
        data.add(jsonObject.get("query").getAsString());
        data.add(jsonObject.get("city").getAsString());
        data.add(jsonObject.get("country").getAsString());

        return data;
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

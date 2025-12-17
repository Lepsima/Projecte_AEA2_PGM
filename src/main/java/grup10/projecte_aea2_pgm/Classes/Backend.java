package grup10.projecte_aea2_pgm.Classes;

import java.net.URLEncoder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Backend {

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

        Requester requester = new Requester( "GET", " https://nominatim.openstreetmap.org/search/" + URLEncoder.encode(nomCiutat), headers);
        String response = requester.getResponseBody();

        JsonArray jsonArray = gsonParser(response);

        double latitud = jsonArray.get(0).getAsJsonObject().get("lat").getAsDouble();
        double longitud = jsonArray.get(0).getAsJsonObject().get("lon").getAsDouble();

        return new double[]{latitud, longitud};
    }

    private JsonObject obtainEstacioMesProper(double latitud, double longitud) {

        String headers[][] = {
                {},
                {"Accept", "application/json"},
                {"User-Agent", "App_Java_AEA2"}
        };

        Requester requester = new Requester( "GET", "https://api.openaq.org/v2/locations?coordinates=" + latitud + "," + longitud + "&order_by=distance&sort=asc&limit=1", headers);
        String response = requester.getResponseBody();

        JsonArray jsonArray = gsonParser(response);

        JsonObject estacioMesProper = jsonArray.get(0).getAsJsonObject();

        return estacioMesProper;
    }


}

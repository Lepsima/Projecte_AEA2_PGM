package grup10.projecte_aea2_pgm.Classes;

import java.net.URLEncoder;

public class Backend {

    public double[] obtainLatitudILongitud(String nomCiutat) {

        String headers[][] = {
                {"Accept", "application/json"}
        };

        Requester requester = new Requester( "GET", " https://nominatim.openstreetmap.org/search/" + URLEncoder.encode(nomCiutat), headers);

        String response = requester.getResponseBody();

        return null;
    }


}

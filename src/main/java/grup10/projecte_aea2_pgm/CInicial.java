package grup10.projecte_aea2_pgm;

import grup10.projecte_aea2_pgm.Classes.Requester;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URLEncoder;

public class CInicial {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private double obtainLatitudILongitud(String nomCiutat) {

        String headers[][] = {
                {"Accept", "application/json"}

        };

        Requester requester = new Requester( "GET", "https://geocode.maps.co/search?city=" + URLEncoder.encode(nomCiutat) );
    }


}
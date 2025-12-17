package grup10.projecte_aea2_pgm;

import grup10.projecte_aea2_pgm.Classes.Requester;
import grup10.projecte_aea2_pgm.Classes.Station;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URLEncoder;
import grup10.projecte_aea2_pgm.Classes.Backend;

public class StationController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Welcome to avaFX Application!");
    }
}
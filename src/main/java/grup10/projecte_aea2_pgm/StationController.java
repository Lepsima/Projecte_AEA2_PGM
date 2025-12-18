package grup10.projecte_aea2_pgm;

import grup10.projecte_aea2_pgm.Classes.Backend;
import grup10.projecte_aea2_pgm.Classes.Station;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StationController implements Initializable {
    @FXML
    public TextField cityField;
    @FXML
    public Button acceptButton;
    @FXML
    public Button IPGeolocation;
    @FXML
    public ComboBox<String> radiusDropdown;
    @FXML
    public RadioButton modeButton0;
    @FXML
    public RadioButton modeButton1;
    @FXML
    public RadioButton modeButton2;
    @FXML
    public TextArea output;
    @FXML
    public Label IPData;


    private int radius;
    private String city;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        modeButton0.setToggleGroup(group);
        modeButton1.setToggleGroup(group);
        modeButton2.setToggleGroup(group);
        modeButton0.setSelected(true);

        output.setEditable(false);

        radiusDropdown.getItems().addAll(
                 "5", "10", "15", "20", "25"
        );
        radiusDropdown.getSelectionModel().selectFirst();
    }

    private void setError(int secs, String message, TextArea output) {
        output.setStyle("-fx-control-inner-background: rgba(255,0,0,0.85);");
        output.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(secs));
        pause.play();
        cityField.setDisable(true);
        pause.setOnFinished(e -> {
            output.setStyle("");
            cityField.setDisable(false);
            output.setText("");
        });

    }

    @FXML
    public void OnAccept(ActionEvent actionEvent)  {

        String buttonID = ((Button) actionEvent.getSource()).getId();

        if(buttonID.equals("acceptButton")) {
            if(!cityField.getText().isEmpty()) {
                city = cityField.getText();
            } else {
                setError(5, "Si us plau, introdueix una ciutat vàlida.", output);
            }

            if (city.isEmpty() && radius <= 0) {
                setError(5, "Si us plau, introdueix una ciutat i un radi vàlid.", output);
            }
        }

        if(radiusDropdown.getValue() != null) {
            radius = Integer.parseInt(radiusDropdown.getValue()) * 1000;
        } else {
            setError(5, "Si us plau, introdueix un radi.", output);
        }


        int mode = GetSelectedMode();

        if (mode != 0) return;

        Backend backend = new Backend();
        List<Object> data_api;

        if (buttonID.equals("IPGeolocation")) {
            try {
                data_api = backend.obtainIPgeolocation();
            } catch (Exception e) {
                setError(5, "Error en obtenir les dades: " + e.getMessage(), output);
                return;
            }

            // fiquem les dades de l'IP
            String geolocationData = "IP: " + data_api.get(2) + "\n" +
                    "Ciutat: " + data_api.get(3) + "\n" +
                    "País: " + data_api.get(4) + "\n";

            cityField.setText(data_api.get(3)+ ", " + data_api.get(4));
            IPData.setText(geolocationData);
        } else {
            try {
                data_api = backend.obtainLatitudILongitud(city);
            } catch (Exception e) {
                setError(5, "Error en obtenir les dades: " + e.getMessage(), output);
                return;
            }
        }

        output.setStyle("");
        output.setText("");

        List<Station> data = backend.obtainEstacionsProperes((Double) data_api.get(0), (Double) data_api.get(1), radius);


        StringBuilder showData = new StringBuilder();
        for (Station station : data) {
            showData.append("\nEstació: ").append(station.nom);
            showData.append("\nSensors: ").append(station.sensors);
            showData.append("\n-----");
        }

        output.setText(showData.toString());
    }

    private int GetSelectedMode() {
        if (modeButton0.isSelected()) return 0;
        if (modeButton1.isSelected()) return 1;
        if (modeButton2.isSelected()) return 2;
        return -1;
    }
}
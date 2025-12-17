package grup10.projecte_aea2_pgm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StationController implements Initializable {
    @FXML
    public TextField cityField;

    @FXML
    public Button acceptButton;

    @FXML
    public ComboBox<String> radiusDropdown;

    @FXML
    public RadioButton modeButton0;
    @FXML
    public RadioButton modeButton1;
    @FXML
    public RadioButton modeButton2;

    @FXML
    public Label output;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup group = new ToggleGroup();
        modeButton0.setToggleGroup(group);
        modeButton1.setToggleGroup(group);
        modeButton2.setToggleGroup(group);

        modeButton0.setSelected(true);

        radiusDropdown.getItems().addAll(
                "1", "5", "10", "15", "20", "25"
        );
    }

    @FXML
    public void OnAccept(ActionEvent actionEvent) {
        String city = cityField.getText();
        int radius = Integer.parseInt(radiusDropdown.getValue()) * 1000;
        int mode = GetSelectedMode();

        if (mode != 0) return;
        cityField.setText(String.format("%s, %s, %s", city, radius, mode));
    }

    private int GetSelectedMode() {
        if (modeButton0.isSelected()) return 0;
        if (modeButton1.isSelected()) return 1;
        if (modeButton2.isSelected()) return 2;
        return -1;
    }
}
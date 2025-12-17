package grup10.projecte_aea2_pgm;

import grup10.projecte_aea2_pgm.Classes.Station;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import grup10.projecte_aea2_pgm.Classes.Backend;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("station.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);

        stage.setTitle("Estat del aire");
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.setResizable(true);
        stage.show();
    }
}
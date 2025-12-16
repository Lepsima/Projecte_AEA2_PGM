package grup10.projecte_aea2_pgm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import grup10.projecte_aea2_pgm.Classes.Backend;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("station.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        stage.setTitle("Estat del aire");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
        Backend backend = new Backend();
        double[] coords = backend.obtainLatitudILongitud("Barcelona");
        System.out.println("Latitud: " + coords[0] + ", Longitud: " + coords[1]);
    }
}
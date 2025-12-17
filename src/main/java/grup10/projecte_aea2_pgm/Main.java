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
        stage.setResizable(true);
        stage.show();

        TEST_backend();
    }

    private static void TEST_backend() {
        Backend backend = new Backend();
        double[] coords = backend.obtainLatitudILongitud("Vilafranca del Penedès");

        List<Station> data = backend.obtainEstacionsProperes(coords[0], coords[1], 15000);

        for (Station station : data) {
            System.out.println("Estació: " + station.nom);
            System.out.println("Sensors: " + station.sensors);
            System.out.println("-----");
        }

    }
}
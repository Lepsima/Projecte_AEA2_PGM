module grup10.projecte_aea2_pgm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens grup10.projecte_aea2_pgm to javafx.fxml;
    exports grup10.projecte_aea2_pgm;
}
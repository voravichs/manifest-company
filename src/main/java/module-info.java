module mc.manifestcompany {
    requires javafx.controls;
    requires javafx.fxml;


    opens mc.manifestcompany to javafx.fxml;
    exports mc.manifestcompany;
}
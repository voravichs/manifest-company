module mc.manifestcompany {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens mc.manifestcompany to javafx.fxml;
    exports mc.manifestcompany;
}
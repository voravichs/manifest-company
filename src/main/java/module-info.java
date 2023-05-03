module mc.manifestcompany {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens mc.manifestcompany to javafx.fxml;
    exports mc.manifestcompany;
    exports mc.manifestcompany.gui;
    opens mc.manifestcompany.gui to javafx.fxml;
    exports mc.manifestcompany.company;
    opens mc.manifestcompany.company to javafx.fxml;
    exports mc.manifestcompany.gamelogic;
    opens mc.manifestcompany.gamelogic to javafx.fxml;
}
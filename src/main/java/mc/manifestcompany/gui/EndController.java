package mc.manifestcompany.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mc.manifestcompany.App;

import java.io.IOException;

public class EndController {
    @FXML
    private Label results;

    public void setResults(String text) {
        this.results.setText(text);
    }

    @FXML
    protected void exit(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("title.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
        stage.show();
    }
}

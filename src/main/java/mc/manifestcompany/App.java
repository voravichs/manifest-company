package mc.manifestcompany;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Starts the application, initializing the FXML and CSS files.
 * @author VoravichS
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(App.class.getResource("root.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        String css = Objects.requireNonNull(
                App.class.getResource("stylesheet.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        // now testing the branch protection - again by Jay
        // new test - Jay
    }

    public static void main(String[] args) {
        launch();
    }
}
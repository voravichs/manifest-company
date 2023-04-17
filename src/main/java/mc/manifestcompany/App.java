package mc.manifestcompany;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Starts the application, initializing the FXML and CSS files.
 * @author VoravichS
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Boots the game either in "production" mode (with args)
        // or in normal mode, showing the title screen
        Parameters params = getParameters();
        List<String> argList = params.getRaw();
        FXMLLoader fxmlLoader;
        if (argList.size() == 0) {
            fxmlLoader = new FXMLLoader(App.class.getResource("root.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            stage.setTitle("Manifest Company");
            stage.setScene(scene);
        } else {
            fxmlLoader = new FXMLLoader(App.class.getResource("gameScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            stage.setTitle("Manifest Company");
            stage.setScene(scene);
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


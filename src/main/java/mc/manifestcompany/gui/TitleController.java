package mc.manifestcompany.gui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import mc.manifestcompany.App;
import mc.manifestcompany.FileHandler;
import mc.manifestcompany.gamelogic.Game;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Defines the functions of the buttons on the title screen.
 * @author Team Manifest Company
 */
public class TitleController {

    @FXML
    private BorderPane mainMenuPane;
    @FXML
    private Pane loadPane;
    @FXML
    private Pane levelSelectPane;
    @FXML
    private Pane companyNamePane;
    @FXML
    private TextField companyNameInput;
    @FXML
    private FlowPane loadFiles;

    private Level levelChosen;

    @FXML
    protected void startNewGame(ActionEvent event) throws IOException {
        String playerCompanyName = companyNameInput.getText();
        switchToGameScreen(event, playerCompanyName);
    }

    /**
     * Switches to the game screen.
     * @param event click event
     * @throws IOException IO exception thrown if fxml file cannot be found
     */
    private void switchToGameScreen(ActionEvent event, String playerCompanyName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gameScreen.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        controller.setGame(new Game(Game.X_SIZE,Game.Y_SIZE, playerCompanyName, levelChosen.file));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches to the game screen using a loaded game.
     * @throws IOException IO exception thrown if fxml file cannot be found
     */
    private void switchToGameScreenFromLoad(Game loadedGame) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gameScreen.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        controller.setGame(loadedGame);
        Stage stage = (Stage) mainMenuPane.getScene().getWindow();
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Quits the program.
     */
    @FXML
    protected void quit() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Shows the load pane.
     */
    @FXML
    protected void showLoad() {
        mainMenuPane.setOpacity(0.3);
        loadSaveFiles();
        loadPane.setVisible(true);
    }

    /**
     * Shows the load pane.
     */
    @FXML
    protected void closeLoad() {
        mainMenuPane.setOpacity(1);
        loadPane.setVisible(false);
    }

    /**
     * Loads the list of save files stored in the saveFile directory.
     */
    private void loadSaveFiles() {
        // Clear previous files
        ObservableList<Node> files = loadFiles.getChildren();
        loadFiles.getChildren().removeAll(files);

        File folder = new File("saveFiles");
        List<File> fileList = List.of(Objects.requireNonNull(folder.listFiles()));
        for (File file: fileList) {
            Label fileLabel = new Label(file.getName());
            fileLabel.getStyleClass().add("load-text");
            fileLabel.getStyleClass().add("hover-text");
            fileLabel.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    try {
                        Game loadedGame = FileHandler.load(fileLabel.getText());
                        switchToGameScreenFromLoad(loadedGame);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            loadFiles.getChildren().add(fileLabel);
        }
    }

    @FXML
    protected void showLevelSelect() {
        levelSelectPane.setVisible(true);
    }

    @FXML
    protected void closeLevelSelect() {
        levelSelectPane.setVisible(false);
    }

    @FXML
    protected void showCompanyName() {
        levelSelectPane.setOpacity(0.3);
        mainMenuPane.setOpacity(0.0);
        companyNamePane.setVisible(true);
    }

    @FXML
    protected void loadFastFoodLevel() {
        showCompanyName();
        levelChosen = Level.FAST_FOOD;
    }

    public enum Level {
        FAST_FOOD("companies/fastFood.txt");

        private final String file;

        Level(String file) {
            this.file = file;
        }
    }
}
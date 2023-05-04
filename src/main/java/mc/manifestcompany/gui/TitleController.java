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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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
    private FlowPane loadFiles;

    /**
     * Switches to the game screen.
     * @param event click event
     * @throws IOException IO exception thrown if fxml file cannot be found
     */
    public void switchToGameScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gameScreen.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        controller.setGame(new Game(Game.X_SIZE,Game.Y_SIZE));
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

//    /**
//     * Called upon clicking the program at the start.
//     */
//    @FXML
//    protected void start() {
//        if (!started) {
//            started = true;
//            centerTitlePane.getChildren().remove(startBox);
//
//            // GridPane
//            GridPane gridPane = new GridPane();
//            gridPane.setPrefSize(600, 300);
//            gridPane.setVgap(40);
//            gridPane.setHgap(40);
//            gridPane.setAlignment(Pos.CENTER);
//            centerTitlePane.setCenter(gridPane);
//
//            // Buttons
//            Button b1 = new Button("New Game");
//            b1.setFont(new Font(30));
//            Button b2 = new Button("Load");
//            b2.setFont(new Font(30));
//            Button b3 = new Button("Options");
//            b3.setFont(new Font(30));
//            Button b4 = new Button("Quit");
//            b4.setFont(new Font(30));
//            gridPane.add(b1,0,0);
//            gridPane.add(b2, 1,0);
//            gridPane.add(b3,0,1);
//            gridPane.add(b4,1,1);
//
//            titleAnimation();
//        }
//    }

//    private void titleAnimation() {
//        List<Rectangle> rectList = new ArrayList<>();
//        Boolean[][] blockArray = new Boolean[6][14];
//        while (true) {
//            int randOffset = (int) (Math.random() * 6);
//            Rectangle rect = new Rectangle(50 * randOffset, 0, 50, 50);
//            rect.setFill(Color.WHITE);
//            rect.setStroke(Color.BLACK);
//            leftTitlePane.getChildren().add(rect);
//
//            // Keyframe
//            KeyFrame kf = new KeyFrame(Duration.millis(50),
//                    (ActionEvent e) -> dropBlock(rect, randOffset, blockArray));
//
//            Timeline tl = new Timeline(kf);
//
//            tl.setCycleCount(20);
//            tl.play();
//        }
//    }

//    private void dropBlock(Rectangle rect, int offset, Boolean[][] blockArray) {
//
//        for (int i = 0; i < 14; i++) {
//            if (blockArray[offset][i]) {
//
//            }
//        }
//        if (rect.getY() + 50 < 700) {
//            rect.setY(rect.getY() + 50);
//        }
//
//    }
}
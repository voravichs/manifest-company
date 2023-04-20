package mc.manifestcompany;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Defines the functions of the buttons on the title screen.
 * @author Team Manifest Company
 */
public class TitleController {
    /**
     * Switches to the game screen.
     * @param event click event
     * @throws IOException IO exception thrown if fxml file cannot be found
     */
    public void switchToGameScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gameScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
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
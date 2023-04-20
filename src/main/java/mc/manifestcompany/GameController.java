package mc.manifestcompany;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Controls the GUI elements of the game screen.
 * @author Team Manifest Company
 */
public class GameController {

    /* Final Variables */
    public static final int X_SIZE = 20;
    public static final int Y_SIZE = 20;

    /* FXML Variables */
    @FXML
    private Pane gameBoard;
    @FXML
    private TextFlow textBox;
    @FXML
    private GridPane sideBar;
    @FXML
    private Pane transitionPane;
    @FXML
    private StackPane root;

    /* Instance Variables */
    private final Game game;

    public GameController() {
        this.game = new Game(X_SIZE, Y_SIZE);
    }

    /**
     * Initializes the game and hides the transition pane.
     */
    @FXML
    protected void init() throws FileNotFoundException {
        initSidebar();
        updateGrid();
        root.getChildren().remove(transitionPane);
    }

    /**
     * Initializes the sidebar
     */
    @FXML
    protected void initSidebar() throws FileNotFoundException {
        int i = 0;
        for (FastFoodLevel level:
             FastFoodLevel.values()) {
            URL imagePath = App.class.getResource(level.getImageLink());
            assert imagePath != null;
            Image image = new Image(new FileInputStream(imagePath.getPath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setTranslateX(12.5);
            sideBar.add(imageView,0,i);
            i++;
        }
    }

    /**
     * Visually updates the grid using the game's tile
     */
    @FXML
    protected void updateGrid() {
        Tile[][] tileGrid = game.getTileGrid();
        for (Tile[] tiles : tileGrid) {
            for (Tile tile : tiles) {
                Rectangle currTile = tile.getSquare();
                gameBoard.getChildren().add(currTile);
            }
        }
    }


    /**
     * Removes all shapes from the GUI
     */
    @FXML
    protected void clearBoard() {
        Tile[][] tileGrid = game.getTileGrid();
        for (Tile[] tiles : tileGrid) {
            for (Tile tile : tiles) {
                gameBoard.getChildren().remove(tile.getSquare());
            }
        }
    }

    /**
     * Advances to the next turn
     */
    @FXML
    protected void advanceTurn() {
        // TODO: WRITE CODE FOR WHAT HAPPENS WHEN A TURN ADVANCES
        System.out.println("Advancing Turn...");
        Text advanceText = new Text("Advancing Turn...\n");
        textBox.getChildren().add(advanceText);
    }


}

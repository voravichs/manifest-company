package mc.manifestcompany;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Stack;

/**
 * Controls the GUI elements of the game screen.
 * @author Team Manifest Company
 */
public class GameController {

    /* Final Variables */
    public static final int X_SIZE = 20;
    public static final int Y_SIZE = 20;

    /* FXML Variables */
    // StackPane Layers
    @FXML
    private BorderPane gamePane;
    @FXML
    private Pane dataPane;
    @FXML
    private Pane actionPane;
    @FXML
    private Pane investPane;
    @FXML
    private Pane transitionPane;

    @FXML
    private Pane gameBoard;
    @FXML
    private TextFlow textBox;
    @FXML
    private GridPane sideBar;
    @FXML
    private Text date;

    /* Instance Variables */
    private final Game game;
    private Stack<Text> textStack;

    public GameController() {
        this.game = new Game(X_SIZE, Y_SIZE);
        this.textStack = new Stack<>();
    }

    /**
     * Initializes the game and hides the transition pane.
     */
    @FXML
    protected void init() throws FileNotFoundException {
        initSidebar();
        updateGrid();
        Text startext = new Text("Welcome to Manifest Company!\n");
        textBox.getChildren().add(startext);
        textStack.push(startext);
        date.setText("January 1970");
        transitionPane.setVisible(false);
        gamePane.setVisible(true);
    }

    /**
     * Initializes the sidebar
     */
    @FXML
    protected void initSidebar() throws FileNotFoundException {
        int i = 0;

        for (FastFoodLevel level:
             FastFoodLevel.values()) {
            // Image
            URL imagePath = App.class.getResource(level.getImageLink());
            assert imagePath != null;
            Image image = new Image(new FileInputStream(imagePath.getPath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setTranslateX(18.75);
            sideBar.add(imageView,0,i);

            // init company stats
            // TODO: FILL THESE IN WITH THE REAL STATS
            updateCompanyStats(i);

            i++;
        }
    }

    /**
     * Updates the company stats at the sidebar.
     * @param row row of the company to update
     */
    public void updateCompanyStats(int row) {
        // TODO: REAL STATS
        TextFlow tf = new TextFlow();
        tf.setPadding(new Insets(20,20,20,20));
        tf.getStyleClass().add("stats-text");
        Text text = new Text(
                "Name\n" +
                "Net Worth: 0\n" +
                "Profit: 0\n" +
                "PLACEHOLDER: 0");
        tf.getChildren().add(text);

        sideBar.add(tf,1,row);
    }

    /**
     * Visually updates the grid using the game's tile
     */
    @FXML
    protected void updateGrid() {
        clearBoard();
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
     * Shows the data pane.
     */
    @FXML
    protected void showData() {
        gamePane.setOpacity(0.3);
        dataPane.setVisible(true);
        // TODO: ACTUALLY ADD DATA
    }

    /**
     * Shows the data pane.
     */
    @FXML
    protected void closeData() {
        gamePane.setOpacity(1);
        dataPane.setVisible(false);
    }

    /**
     * Shows the action pane.
     */
    @FXML
    protected void showActions() {
        gamePane.setOpacity(0.3);
        actionPane.setVisible(true);
    }

    /**
     * Closes the action pane.
     */
    @FXML
    protected void closeActions() {
        gamePane.setOpacity(1);
        actionPane.setVisible(false);
    }

    /**
     * Shows investing pane.
     */
    @FXML
    protected void showInvest() {
        actionPane.setOpacity(0.3);
        investPane.setVisible(true);
    }

    /**
     * Closes investing pane.
     */
    @FXML
    protected void closeInvest() {
        actionPane.setOpacity(1);
        investPane.setVisible(false);
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
        textStack.push(advanceText);

        // TODO: EXAMPLE METHOD, REPLACE LATER
        // go to next turn, changing the board, then update the grid
        this.game.nextTurn();
        updateGrid();
    }




}

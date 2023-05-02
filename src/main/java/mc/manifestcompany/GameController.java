package mc.manifestcompany;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
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
    private Pane transitionPane;

    // Action/Spinner Elements
    @FXML
    private Spinner<Integer> marketSpinner;
    @FXML
    private Spinner<Integer> rdSpinner;
    @FXML
    private Spinner<Integer> goodSpinner;
    @FXML
    private Spinner<Integer> hrSpinner;
    @FXML
    private Spinner<Integer> tileSpinner;
    @FXML
    private Text totalInvest;
    @FXML
    private Text possibleToInvest;

    // Changing Graphical Elements
    @FXML
    private Pane gameBoard;
    @FXML
    private TextFlow textBox;
    @FXML
    private GridPane sideBar;
    @FXML
    private Text date;
    @FXML
    private GridPane dataChart;

    /* Instance Variables */
    private final Game game;
    private Queue<Text> textQueue;

    public GameController() {
        this.game = new Game(X_SIZE, Y_SIZE);
        this.textQueue = new LinkedList<>();
    }

    /**
     * Initializes the game and hides the transition pane.
     */
    @FXML
    protected void init() throws FileNotFoundException {
        initSidebar();
        updateGrid();
        initSpinners();
        addText("Welcome to Manifest Company!\n");
        date.setText("January 1970");
        transitionPane.setVisible(false);
        gamePane.setVisible(true);

        // Init chart parameters
        HashMap<Company, HashMap<Enum<DataType>, Integer>> companyStats = game.getCompanyStats();
        int i = 1;
        for (Company company:
             companyStats.keySet()) {
            dataChart.add(new Label(company.getName()), 0, i);
            i++;
        }
        dataChart.add(new Label("Net Worth"), 1,0);
        dataChart.add(new Label("Profit"), 2,0);
        dataChart.add(new Label("Profit %"), 3,0);
        dataChart.add(new Label("Goods"), 4,0);

        for (i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                dataChart.add(new Label("0"), 1+i,1+j);
            }
        }
    }

    /**
     * Initializes the listeners for the actions pane.
     */
    @FXML
    protected void initSpinners() {
        // Spinner value factory for investing increments/decrements by 1000
        SpinnerValueFactory<Integer> investMarketFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,0,1000);
        SpinnerValueFactory<Integer> investRDFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,0,1000);
        SpinnerValueFactory<Integer> investGoodsFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,0,1000);
        SpinnerValueFactory<Integer> investHRFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,0,1000);

        // Spinner value factory for investing increments/decrements by 1000
        SpinnerValueFactory<Integer> buyTileFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);

        // Set factories for each spinner
        marketSpinner.setValueFactory(investMarketFactory);
        rdSpinner.setValueFactory(investRDFactory);
        goodSpinner.setValueFactory(investGoodsFactory);
        hrSpinner.setValueFactory(investHRFactory);
        tileSpinner.setValueFactory(buyTileFactory);

        // Update the total when spinners change value
        ChangeListener<Object> totalUpdate = (observable, oldValue, newValue) ->
                totalInvest.setText("$" +
                        (marketSpinner.getValue() + rdSpinner.getValue() +
                        goodSpinner.getValue() + hrSpinner.getValue()));
        marketSpinner.valueProperty().addListener(totalUpdate);
        rdSpinner.valueProperty().addListener(totalUpdate);
        goodSpinner.valueProperty().addListener(totalUpdate);
        hrSpinner.valueProperty().addListener(totalUpdate);
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
    @FXML
    protected void updateCompanyStats(int row) {
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
     * Advances to the next turn
     */
    @FXML
    protected void advanceTurn() {
        // Add text to the box showing the turn has advanced
        addText("Advancing Turn...\n");

        // go to next turn, changing the board, then update the grid
        this.game.nextTurn();
        updateGrid();
    }

    /**
     * Adds text to the textQueue, and removes text from the front
     * once it reaches max capacity (10 texts)
     * @param text the text to add to the queue
     */
    @FXML
    protected void addText(String text) {
        // text stack only stores 10 most recent texts
        if (textQueue.size() < 10) {
            Text nextText = new Text(text);
            textBox.getChildren().add(nextText);
            textQueue.add(nextText);
        } else {
            Text poppedText = textQueue.remove();
            textBox.getChildren().remove(poppedText);
            // Call this again to add the text
            addText(text);
        }
    }

    @FXML
    protected void invest() {

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

    /* ***** SHOW/HIDE PANES ****** */

    /**
     * Shows the data pane.
     */
    @FXML
    protected void showData() {
        gamePane.setOpacity(0.3);
        dataPane.setVisible(true);
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

        // Reset the spinners
        marketSpinner.getValueFactory().setValue(0);
        rdSpinner.getValueFactory().setValue(0);
        goodSpinner.getValueFactory().setValue(0);
        hrSpinner.getValueFactory().setValue(0);
        tileSpinner.getValueFactory().setValue(0);
    }

    /**
     * Closes the action pane.
     */
    @FXML
    protected void closeActions() {
        gamePane.setOpacity(1);
        actionPane.setVisible(false);
    }

}

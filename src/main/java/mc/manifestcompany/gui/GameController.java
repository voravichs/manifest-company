package mc.manifestcompany.gui;

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
import mc.manifestcompany.*;
import mc.manifestcompany.company.Company;
import mc.manifestcompany.gamelogic.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

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
    private Text marketTotal;
    @FXML
    private Text rdTotal;
    @FXML
    private Text goodsTotal;
    @FXML
    private Text hrTotal;
    @FXML
    private Text tileTotal;
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
    private final Queue<Text> textQueue;

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
        addText("Open the ACTIONS menu to start investing.\n");
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
                dataChart.add(new Label("0"), 1 + i,1 + j);
            }
        }
    }

    /**
     * Initializes the listeners for the action pane.
     */
    @FXML
    protected void initSpinners() {
        // Spinner value factory for investing increments/decrements by 1000
        SpinnerValueFactory<Integer> investMarketFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,0,100);
        SpinnerValueFactory<Integer> investRDFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,0,100);
        SpinnerValueFactory<Integer> investGoodsFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,0,100);
        SpinnerValueFactory<Integer> investHRFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000000,0,100);

        // Spinner value factory for investing increments/decrements by 1000
        SpinnerValueFactory<Integer> tileFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(-100,100,0);

        // Set factories for each spinner
        marketSpinner.setValueFactory(investMarketFactory);
        rdSpinner.setValueFactory(investRDFactory);
        goodSpinner.setValueFactory(investGoodsFactory);
        hrSpinner.setValueFactory(investHRFactory);
        tileSpinner.setValueFactory(tileFactory);

        // Update the total when spinners change value
        ChangeListener<Object> totalUpdate = (observable, oldValue, newValue) -> {
            int total = marketSpinner.getValue() + rdSpinner.getValue() +
                        goodSpinner.getValue() + hrSpinner.getValue() +
                        (300 * tileSpinner.getValue());
            totalInvest.setText("$" + -total);
            if (total < 0) {
                totalInvest.setStyle("-fx-fill: green;");
            } else {
                totalInvest.setStyle("-fx-fill: red;");
            }
            // Update possible to buy
            if (game.getPlayer().checkValidInvest(-1 * total, -1 * tileSpinner.getValue())) {
                possibleToInvest.setText("Investment possible!");
                possibleToInvest.setStyle("-fx-fill: green;");
            } else {
                possibleToInvest.setText("Invalid investment! \n (not enough money or tiles)");
                possibleToInvest.setStyle("-fx-fill: red;");
            }
        };

        marketSpinner.valueProperty().addListener(totalUpdate);
        rdSpinner.valueProperty().addListener(totalUpdate);
        goodSpinner.valueProperty().addListener(totalUpdate);
        hrSpinner.valueProperty().addListener(totalUpdate);
        tileSpinner.valueProperty().addListener(totalUpdate);

        // Update individual totals
        marketSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                marketTotal.setText("$" + -marketSpinner.getValue()));
        rdSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                rdTotal.setText("$" + -rdSpinner.getValue()));
        goodSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                goodsTotal.setText("$" + -goodSpinner.getValue()));
        hrSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
                hrTotal.setText("$" + -hrSpinner.getValue()));
        tileSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            tileTotal.setText("$" + -300 * tileSpinner.getValue());
            if (tileSpinner.getValue() < 0) {
                tileTotal.setStyle("-fx-fill: green;");
            } else {
                tileTotal.setStyle("-fx-fill: red;");
            }
        });
    }

    /**
     * Initializes the sidebar
     */
    @FXML
    protected void initSidebar() throws FileNotFoundException {
        int i = 0;

        // Add all players and npcs to a list
        List<Company> companyList = new ArrayList<>();
        companyList.add(game.getPlayer());
        companyList.addAll(game.getNPCs());

        // Loop through the list
        for (Company company:
             companyList) {
            // Image
            URL imagePath = App.class.getResource(company.getImageLink());
            assert imagePath != null;
            Image image = new Image(new FileInputStream(imagePath.getPath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setTranslateX(18.75);
            sideBar.add(imageView,0,i);

            // init company stats
            TextFlow tf = new TextFlow();
            tf.setPadding(new Insets(20,20,20,20));
            tf.getStyleClass().add("stats-text");
            Text text = new Text(
                    company.getName() + "\n" +
                    "Cash: " + company.getStats().get(DataType.CASH));
            // TODO: UPDATE CASH WHEN A NEW TURN ARRIVES

            tf.getChildren().add(text);


            sideBar.add(tf,1,i);

            i++;
        }
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
        if (textQueue.size() < 5) {
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

    /**
     * From the action menu, confirms a player's investing decisions,
     * invests into the chosen sectors, and buys/sells the chosen
     * number of tiles if valid.
     */
    @FXML
    protected void invest() {
        if (possibleToInvest.getText().equals("Investment possible!")) {
            // Invest values from spinners
            game.getPlayer().invest(marketSpinner.getValue(), "Marketing");
            game.getPlayer().invest(rdSpinner.getValue(), "R&D");
            game.getPlayer().invest(goodSpinner.getValue(), "Goods");
            game.getPlayer().invest(hrSpinner.getValue(), "HR");

            if (tileSpinner.getValue() > 0) {
                // (+) Buy Tiles
                game.getPlayer().tiles(tileSpinner.getValue(), "Purchase", game.getTileGrid());
            } else if (tileSpinner.getValue() < 0) {
                // (-) Sell Tiles
                game.getPlayer().tiles(tileSpinner.getValue(), "Sell", game.getTileGrid());
            }

            addText("Player Investing decision recorded!\nPress NEXT TURN to continue.\n");

            closeActions();
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

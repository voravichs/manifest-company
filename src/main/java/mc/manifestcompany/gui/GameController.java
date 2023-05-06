package mc.manifestcompany.gui;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import mc.manifestcompany.*;
import mc.manifestcompany.company.Company;
import mc.manifestcompany.gamelogic.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controls the GUI elements of the game screen.
 * @author Team Manifest Company
 */
public class GameController {

    /* FXML Variables */
    // StackPane Layers
    @FXML
    private BorderPane gamePane;
    @FXML
    private Pane dataPane;
    @FXML
    private Pane actionPane;
    @FXML
    private Pane saveNamePane;
    @FXML
    private Pane exitConfirmPane;
    @FXML
    private Pane transitionPane;
    @FXML
    private Pane startPane;

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
    private Text turn;
    @FXML
    private GridPane dataChart;
    @FXML
    private Label turnText;
    @FXML
    private TextField saveNameEntry;

    /* Instance Variables */
    private Game game;
    private final Queue<Text> textQueue;
    private boolean sorted;
    private DataType currentSorted;

    // Constructor inits the textQueue on startup
    public GameController() {
        this.textQueue = new LinkedList<>();
    }

    // Setter for the Game, used for loading a save file
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Initializes the game and hides the start pane.
     */
    @FXML
    protected void init() throws FileNotFoundException {
        // Inits all GUI elements
        initSidebar();
        initSpinners();
        initChart();
        updateChart(game.sortCompaniesBy(DataType.TILES));
        sorted = false;
        currentSorted = DataType.TILES;
        updateGrid();

        // Add text to the textQueue and turn num
        addText("[TURN " + game.getTurnNum() + "]\n");
        addText("Open the ACTIONS menu to\n");
        addText("start investing.\n");
        turn.setText("Turn " + game.getTurnNum());

        // Set the startPane to invisible and the gamePane to visible
        startPane.setVisible(false);
        gamePane.setVisible(true);
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
            tf.setPadding(new Insets(50,20,20,10));
            tf.getStyleClass().add("stats-text");
            Text text = new Text(
                    company.getName() + "\n" +
                    "Cash: $" + company.getStats().get(DataType.CASH));
            transitionPane.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> text.setText(company.getName() + "\n" +
                        "Cash: $" + company.getStats().get(DataType.CASH)));

            tf.getChildren().add(text);


            sideBar.add(tf,1,i);

            i++;
        }
    }

    /**
     * Initializes the chart headers and sorting handlers.
     */
    @FXML
    protected void initChart() {
        Label tiles = new Label("Tiles");
        GridPane.setHalignment(tiles, HPos.CENTER);
        tiles.getStyleClass().add("hover-text");
        dataChart.add(tiles, 2,0);

        Label cash = new Label("Cash");
        GridPane.setHalignment(cash, HPos.CENTER);
        cash.getStyleClass().add("hover-text");
        dataChart.add(cash, 3,0);

        Label price = new Label("Price of\nGoods");
        GridPane.setHalignment(price, HPos.CENTER);
        price.getStyleClass().add("hover-text");
        dataChart.add(price, 4,0);

        Label goods = new Label("Available\nGoods");
        GridPane.setHalignment(goods, HPos.CENTER);
        goods.getStyleClass().add("hover-text");
        dataChart.add(goods, 5,0);

        Label cost = new Label("Operation\nCost");
        GridPane.setHalignment(cost, HPos.CENTER);
        cost.getStyleClass().add("hover-text");
        dataChart.add(cost, 6,0);

        tiles.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    sort(DataType.TILES);
                    if (sorted) {
                        tiles.setText("Tiles↑");
                    } else {
                        tiles.setText("Tiles↓");
                    }
                    switch (currentSorted) {
                        case CASH -> cash.setText("Cash");
                        case PRICE -> price.setText("Price of\nGoods");
                        case CAPACITY -> goods.setText("Available\nGoods");
                        case COST -> cost.setText("Operation\nCost");
                    }
                    currentSorted = DataType.TILES;
                });
        cash.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    sort(DataType.CASH);
                    if (sorted) {
                        cash.setText("Cash↑");
                    } else {
                        cash.setText("Cash↓");
                    }
                    switch (currentSorted) {
                        case TILES -> tiles.setText("Tiles");
                        case PRICE -> price.setText("Price of\nGoods");
                        case CAPACITY -> goods.setText("Available\nGoods");
                        case COST -> cost.setText("Operation\nCost");
                    }
                    currentSorted = DataType.CASH;
                });
        price.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    sort(DataType.PRICE);
                    if (sorted) {
                        price.setText("Price of\nGoods↑");
                    } else {
                        price.setText("Price of\nGoods↓");
                    }
                    switch (currentSorted) {
                        case TILES -> tiles.setText("Tiles");
                        case CASH -> cash.setText("Cash");
                        case CAPACITY -> goods.setText("Available\nGoods");
                        case COST -> cost.setText("Operation\nCost");
                    }
                    currentSorted = DataType.PRICE;
                });
        goods.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    sort(DataType.CAPACITY);
                    if (sorted) {
                        goods.setText("Available\nGoods↑");
                    } else {
                        goods.setText("Available\nGoods↓");
                    }
                    switch (currentSorted) {
                        case TILES -> tiles.setText("Tiles");
                        case CASH -> cash.setText("Cash");
                        case PRICE -> price.setText("Price of\nGoods");
                        case COST -> cost.setText("Operation\nCost");
                    }
                    currentSorted = DataType.CAPACITY;
                });
        cost.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    sort(DataType.COST);
                    if (sorted) {
                        cost.setText("Operation\nCost↑");
                    } else {
                        cost.setText("Operation\nCost↓");
                    }
                    switch (currentSorted) {
                        case TILES -> tiles.setText("Tiles");
                        case CASH -> cash.setText("Cash");
                        case PRICE -> price.setText("Price of\nGoods");
                        case CAPACITY -> goods.setText("Available\nGoods");
                    }
                    currentSorted = DataType.COST;
                });
    }

    /**
     * Updates the chart according to a sorted companyList.
     * @param companyList a sorted list to display parameters for companies in order
     */
    @FXML
    protected void updateChart(List<Company> companyList) {
        // Clear previous chart values
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 6; j++) {
                for (Node node : dataChart.getChildren()) {
                    if ((node instanceof Text || node instanceof Label) &&
                            GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) {
                        dataChart.getChildren().remove(node);
                        break;
                    }
                }
            }
        }

        // Init chart parameters
        int i = 1;
        for (Company company: companyList) {
            // Name
            Text name = new Text(company.getName());
            GridPane.setHalignment(name, HPos.CENTER);
            dataChart.add(name, 1, i);
            // Tiles
            Text tiles = new Text(Integer.toString(company.getStats().get(DataType.TILES)));
            GridPane.setHalignment(tiles, HPos.CENTER);
            dataChart.add(tiles, 2, i);
            // Cash
            Text cash = new Text(Integer.toString(company.getStats().get(DataType.CASH)));
            GridPane.setHalignment(cash, HPos.CENTER);
            dataChart.add(cash, 3, i);
            // Price
            Text price = new Text(Integer.toString(company.getStats().get(DataType.PRICE)));
            GridPane.setHalignment(price, HPos.CENTER);
            dataChart.add(price, 4, i);
            // Goods
            Text goods = new Text(Integer.toString(company.getStats().get(DataType.CAPACITY)));
            GridPane.setHalignment(goods, HPos.CENTER);
            dataChart.add(goods, 5, i);
            // Cost
            Text cost = new Text(Integer.toString(company.getStats().get(DataType.COST)));
            GridPane.setHalignment(cost, HPos.CENTER);
            dataChart.add(cost, 6, i);
            i++;
        }
    }

    /**
     * Visually updates the grid using the game's tileGrid
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
     * Advances to the next turn.
     * Updates the board, chart, and visually transitions to the next turn.
     */
    @FXML
    protected void advanceTurn() {
        // Call the next turn method in game
        this.game.nextTurn(game.getTileGrid());

        // Update chart and grid
        updateChart(game.sortCompaniesBy(DataType.TILES));
        sorted = false;
        currentSorted = DataType.TILES;
        updateGrid();

        // show the transition pane, set the text for the turn
        transitionPane.setVisible(true);
        game.setTurnNum(game.getTurnNum() + 1);
        turnText.setText("Turn " + game.getTurnNum());

        // Add text to the box showing the turn has advanced
        addText("[TURN " + game.getTurnNum() + "]\n");
        turn.setText("Turn " + game.getTurnNum());
    }

    /**
     * Adds text to the textQueue, and removes text from the front
     * once it reaches max capacity (9 texts)
     * @param text the text to add to the queue
     */
    @FXML
    protected void addText(String text) {
        // text stack only stores 10 most recent texts
        if (textQueue.size() < 9) {
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

            addText("Player Investing \n");
            addText("decision recorded!\n");
            addText("Press NEXT TURN \n");
            addText("to continue.\n");

            closeActions();
        }
    }

    /**
     * Removes all shapes from the tileGrid GUI.
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
     * Takes the data from the game and saves it to a file.
     */
    @FXML
    protected void save() throws IOException {
        FileHandler.save(game.getTileGrid(), game.getCompanyList(), game.getTurnNum(), saveNameEntry.getText());
    }

    /**
     * Exits to the main menu.
     */
    @FXML
    protected void exit(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("title.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sorts the companyList according to a dataType.
     * Calls the sortCompaniesBy() method in Game.
     * @param dataType the dataType which will be used to sort.
     */
    @FXML
    protected void sort(DataType dataType) {
        List<Company> sortedCompanyList = game.sortCompaniesBy(dataType);
        // Switch between forward and reverse sort
        if (sorted) {
            // Reverse sort
            sorted = false;
            Collections.reverse(sortedCompanyList);
        } else {
            // Forward sort
            sorted = true;
        }
        updateChart(sortedCompanyList);
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

    /**
     * Closes the transition pane.
     */
    @FXML
    protected void closeTransition() {
        transitionPane.setVisible(false);
    }

    /**
     * Shows the save name pane.
     */
    @FXML
    protected void showSaveName() {
        gamePane.setOpacity(0.3);
        saveNamePane.setVisible(true);
    }

    /**
     * Closes the save name pane.
     */
    @FXML
    protected void closeSaveName() {
        gamePane.setOpacity(1);
        saveNamePane.setVisible(false);
        try {
            save();
            addText("Save successful!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Shows the exit confirmation pane.
     */
    @FXML
    protected void showExitConfirm() {
        gamePane.setOpacity(0.3);
        exitConfirmPane.setVisible(true);
    }

    /**
     * Closes the exit confirmation pane.
     */
    @FXML
    protected void closeExitConfirm() {
        gamePane.setOpacity(1);
        exitConfirmPane.setVisible(false);
    }
}

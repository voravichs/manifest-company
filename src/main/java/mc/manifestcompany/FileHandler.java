package mc.manifestcompany;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import mc.manifestcompany.company.Company;
import mc.manifestcompany.gamelogic.Game;
import mc.manifestcompany.gui.GameController;
import mc.manifestcompany.gui.Tile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class FileHandler {
    public static void save(Tile[][] tileMap, List<Company> companyList, int turnNum, String saveName) throws IOException {
        // Init files and writer
        String filePath = "saveFiles/" + saveName + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        writer.write("tileMap" + " " + tileMap.length + " " + tileMap.length + "\n");
        // Write contents of tileMap
        for (Tile[] tiles : tileMap) {
            for (Tile tile : tiles) {
                // Format:
                // <tileType> E, 1, 2, 3, 4, or L
                // Empty, players 1 thru 4, or Lost
                switch (tile.getType()) {
                    case EMPTY -> writer.write("E ");
                    case CLAIMED_P1 -> writer.write("1 ");
                    case CLAIMED_P2 -> writer.write("2 ");
                    case CLAIMED_P3 -> writer.write("3 ");
                    case CLAIMED_P4 -> writer.write("4 ");
                    case LOST -> writer.write("L ");
                }
            }
            writer.write("\n");
        }

        writer.write("companies" + "\n");
        // Write contents of company List
        for (Company company:
             companyList) {
            // Format:
            // <PRICE> <MULTIPLIER> <CAPACITY> <COST> <CASH> <TILES>
            HashMap<Enum<DataType>, Integer> stats = company.getStats();
            writer.write(stats.get(DataType.PRICE) + " ");
            writer.write(stats.get(DataType.MULTIPLIER) + " ");
            writer.write(stats.get(DataType.CAPACITY) + " ");
            writer.write(stats.get(DataType.COST) + " ");
            writer.write(stats.get(DataType.CASH) + " ");
            writer.write(stats.get(DataType.TILES) + " ");
            writer.write("\n");
        }

        // Write the turn number
        writer.write("turn " + "\n" + turnNum);

        writer.close();
    }

    /**
     * Loads a save file and returns a game object.
     * @param saveName name of the save
     * @return a game object to load
     * @throws IOException if save file cannot be found
     */
    public static Game load(String saveName) throws IOException {
        // Init files and writer
        String filePath = "saveFiles/" + saveName;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        // Create a new Game class
        String startLine = reader.readLine();
        String[] splitStart = startLine.split(" ");
        int xSize = Integer.parseInt(splitStart[1]);
        int ySize = Integer.parseInt(splitStart[2]);
        Game game = new Game(xSize, ySize);

        // Get parameters for square
        double squareSize = (double) Game.GRID_SIZE_X / xSize;

        // read tileMap
        // init a new tileGrid to load into
        Tile[][] tileGrid = new Tile[xSize][ySize];
        // Loop through the save file's grid
        int x = 0;
        for (int y = 0; y < xSize; y++) {
            String line = reader.readLine();
            String[] splitLine = line.split(" ");
            for (String tile: splitLine) {
                // Set the tile type according to value
                Tile.TileType type = Tile.TileType.EMPTY;
                switch (tile) {
                    case "E" -> {}
                    case "1" -> type = Tile.TileType.CLAIMED_P1;
                    case "2" -> type = Tile.TileType.CLAIMED_P2;
                    case "3" -> type = Tile.TileType.CLAIMED_P3;
                    case "4" -> type = Tile.TileType.CLAIMED_P4;
                    case "L" -> type = Tile.TileType.LOST;
                }
                // Assign that tile to the tileGrid
                tileGrid[x][y] = new Tile(
                        new Rectangle(squareSize,squareSize),
                        new Point2D(x * squareSize, y * squareSize),
                        type);
                x++;
            }
            x = 0;
        }
        // Load the tileGrid, overwriting the default one
        game.loadTileGrid(tileGrid);

        // Read in company parameters
        reader.readLine();
        // Read in the same order as the company list
        List<Company> companyList = game.getCompanyList();
        for (Company company: companyList) {
            HashMap<Enum<DataType>, Integer> stats = new HashMap<>();
            String line = reader.readLine();
            String[] splitLine = line.split(" ");
            stats.put(DataType.PRICE, Integer.valueOf(splitLine[0]));
            stats.put(DataType.MULTIPLIER, Integer.valueOf(splitLine[1]));
            stats.put(DataType.CAPACITY, Integer.valueOf(splitLine[2]));
            stats.put(DataType.COST, Integer.valueOf(splitLine[3]));
            stats.put(DataType.CASH, Integer.valueOf(splitLine[4]));
            stats.put(DataType.TILES, Integer.valueOf(splitLine[5]));
            company.setStats(stats);
        }

        // Read the turn number
        reader.readLine();
        game.setTurnNum(Integer.parseInt(reader.readLine()));

        reader.close();
        return game;
    }
}

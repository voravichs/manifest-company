package mc.manifestcompany;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import mc.manifestcompany.company.Company;
import mc.manifestcompany.gamelogic.Game;
import mc.manifestcompany.gui.Tile;

import java.io.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Stack;

public class FileHandler {
    public static void save(Tile[][] tileMap, List<Company> companyList, int turnNum, List<Integer> marketVals, String saveName) throws IOException {
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
            // <NAME> <PRICE> <MULTIPLIER> <CAPACITY> <COST> <CASH> <TILES>
            EnumMap<DataType, Integer> stats = company.getStats();
            writer.write(company.getName() + ",");
            writer.write(stats.get(DataType.PRICE) + ",");
            writer.write(stats.get(DataType.MULTIPLIER) + ",");
            writer.write(stats.get(DataType.CAPACITY) + ",");
            writer.write(stats.get(DataType.COST) + ",");
            writer.write(stats.get(DataType.CASH) + ",");
            writer.write(stats.get(DataType.TILES) + ",");
            writer.write("\n");
            // Then write the tileStack for this company
            Stack<Point2D> tileStack = company.getTileStack();
            while (!tileStack.isEmpty()) {
                Point2D tile = tileStack.pop();
                writer.write((int)tile.getX() + "," + (int)tile.getY() + " ");
            }
            writer.write("\n");
        }

        // Write the turn number
        writer.write("turn " + "\n" + turnNum + "\n");
        // Write the market demand and price
        writer.write("demand " + "\n" + marketVals.get(0) + "\n");
        writer.write("price " + "\n" + marketVals.get(1) + "\n");

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
        Game game = new Game(xSize, ySize, "", "companies/fastFood.txt");

        // Get parameters for square
        double squareSize = (double) Game.GRID_SIZE_X / xSize;

        // read tileMap
        // init a new tileGrid to load into
        Tile[][] tileGrid = new Tile[xSize][ySize];
        // Loop through the save file's grid
        int y = 0;
        for (int x = 0; x < xSize; x++) {
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
                y++;
            }
            y = 0;
        }
        // Load the tileGrid, overwriting the default one
        game.loadTileGrid(tileGrid);

        // Read in company parameters
        reader.readLine();
        // Read in the same order as the company list
        List<Company> companyList = game.getCompanyList();

        for (Company company: companyList) {
            EnumMap<DataType, Integer> stats = new EnumMap<>(DataType.class);
            String line = reader.readLine();
            String[] splitLine = line.split(",");
            company.setName(splitLine[0]);
            stats.put(DataType.PRICE, Integer.valueOf(splitLine[1]));
            stats.put(DataType.MULTIPLIER, Integer.valueOf(splitLine[2]));
            stats.put(DataType.CAPACITY, Integer.valueOf(splitLine[3]));
            stats.put(DataType.COST, Integer.valueOf(splitLine[4]));
            stats.put(DataType.CASH, Integer.valueOf(splitLine[5]));
            stats.put(DataType.TILES, Integer.valueOf(splitLine[6]));
            company.setStats(stats);
            // Read in tileStack
            Stack<Point2D> reversedTileStack = new Stack<>();
            line = reader.readLine();
            if (line.isBlank()) {
                continue;
            }
            splitLine = line.split(" ");
            for (String coordString: splitLine) {
                String[] splitCoord = coordString.split(",");
                Point2D coord = new Point2D(Double.parseDouble(splitCoord[0]), Double.parseDouble(splitCoord[1]));
                reversedTileStack.push(coord);
            }
            // Add the reverse of the stack to the company
            while (!reversedTileStack.isEmpty()) {
                company.addToStack(reversedTileStack.pop());
            }
        }


        // Read the turn number
        reader.readLine();
        game.setTurnNum(Integer.parseInt(reader.readLine()));

        // Read demand and price
        reader.readLine();
        game.setMarketDemand(Integer.parseInt(reader.readLine()));
        reader.readLine();
        game.setMarketPrice(Integer.parseInt(reader.readLine()));

        reader.close();
        return game;
    }
}

package mc.manifestcompany.gamelogic;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import mc.manifestcompany.DataType;
import mc.manifestcompany.company.*;
import mc.manifestcompany.gui.Tile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Manages the non-GUI, logical components of the game.
 * @author Team Manifest Company
 */
public class Game {
    /* Final Static Variables */
    public static final int GRID_SIZE_X = 400;
    public static final int X_SIZE = 20;
    public static final int Y_SIZE = 20;

    // Direction vectors for BFS
    public static int[] ROW_OFFSET = {-1, 0, 1, 0};
    public static int[] COL_OFFSET = { 0, 1, 0, -1 };

    /* Instance Variables */
    // TileGrid
    private Tile[][] tileGrid;
    private final int xSize, ySize;
    private final double squareSize;
    private final int numTiles;

    // Players and NPCS
    private UserCompany player;
    private Queue<Company> npcQueue;

    // Market demand/price
    // market demand for how much a company can sell, could change if an event happens
    private int marketDemand = 30;
    // market price for the goods, could change if an event happens
    private int marketPrice = 100;

    // Turns
    private int turnNum;

    // Inputs from new game
    private final String playerCompanyName;

    public Game(int xSize, int ySize, String playerCompanyName, String levelChosen) {
        // Creates a tile array of x*y size
        tileGrid = new Tile[xSize][ySize];
        this.xSize = xSize;
        this.ySize = ySize;
        this.numTiles = xSize * ySize;
        squareSize = (double) GRID_SIZE_X / xSize;

        // Initializes the tileGrid
        initTileGrid();

        // initializes the level
        HashMap<String, String> levelCompanies;
        try {
            levelCompanies = initLevel(levelChosen);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // initializes the players
        this.playerCompanyName = playerCompanyName;
        initPlayers(levelCompanies);

        // set the turn number
        this.turnNum = 1;
    }

    /**
     * Initializes the tileGrid to have all empty tiles.
     */
    public void initTileGrid() {
        // Initialize the tileGrid
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                tileGrid[x][y] = new Tile(
                        new Rectangle(squareSize,squareSize),
                        new Point2D(x * squareSize, y * squareSize),
                        Tile.TileType.EMPTY);
            }
        }
    }

    /**
     * Initializes the players by granting initial tiles and stats.
     */
    public void initPlayers(HashMap<String, String> levelCompanies) {
        // Set the corner squares to players
        int arrayEndIdx = (int) squareSize - 1;
        claimTile(0,0, Tile.TileType.CLAIMED_P1);
        claimTile(0,arrayEndIdx, Tile.TileType.CLAIMED_P2);
        claimTile(arrayEndIdx,0, Tile.TileType.CLAIMED_P3);
        claimTile(arrayEndIdx,arrayEndIdx, Tile.TileType.CLAIMED_P4);

        // Set user player
        String playerImagePath = levelCompanies.get("Player");
        this.player = new UserCompany(playerCompanyName, new CompanyActionImpl(), Tile.TileType.CLAIMED_P1, playerImagePath);

        // Set npcs
        this.npcQueue = new ArrayDeque<>();

        // List of tile types for NPCs
        List<Tile.TileType> npcTileTypes = List.of(
                Tile.TileType.CLAIMED_P2,
                Tile.TileType.CLAIMED_P3,
                Tile.TileType.CLAIMED_P4
        );
        int npcIndex = 0;

        for (String company: levelCompanies.keySet()) {
            if (company.equals("Player")) {
                continue;
            }
            Tile.TileType npcTileType = npcTileTypes.get(npcIndex % npcTileTypes.size()); // Assign the correct tile type
            npcIndex++;
            Company npc = new NPCCompany(company, new NPCActionImpl(), npcTileType, levelCompanies.get(company));
            this.npcQueue.add(npc);
        }
    }

    public HashMap<String, String> initLevel(String levelChosen) throws IOException {
        // Read in company names and images into a map
        HashMap<String, String> levelCompanies = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(levelChosen));

        // Read lines until end
        String line = reader.readLine();
        while (line != null) {
            String[] splitLine = line.split(",");
            String name = splitLine[0];
            String imagePath = splitLine[1];
            levelCompanies.put(name,imagePath);
            line = reader.readLine();
        }
        return levelCompanies;
    }

    /**
     * Claims a tile at a given index in the tileGrid for the playerType.
     * @param x x-coordinate of the tile to claim
     * @param y y-coordinate of the tile to claim
     * @param playerType the player to grant the tile to
     */
    public void claimTile(int x, int y, Tile.TileType playerType) {
        tileGrid[x][y].setType(playerType);
    }


    /**
     * Updates all the necessary components when user advances the turn
     */
    public void nextTurn(Tile[][] grid) {
        Turn turn = new TurnImpl(marketDemand, marketPrice);
        int numGoods = turn.randomGoodsSold();

        for (Company npc : npcQueue) {
            NPCCompany npcCompany = (NPCCompany) npc;
            npcCompany.getActions().performRandomAction(npcCompany, grid);
        }

        turn.turn(numGoods, player);
        for (Company npc: npcQueue) {
            turn.turn(numGoods, npc);
        }

        if (!turn.validCompany(player)) {
            // TODO: PLAYER LOST, GAME ENDS
            return;
        }
        // check if any of the NPC went bankrupt, remove from npcQueue if bankrupt
        int numNPCs = npcQueue.size();
        for (int i = 0; i < numNPCs; i++) {
            Company npc = npcQueue.poll();
            if (turn.validCompany(npc)) {
                npcQueue.add(npc);
            }
        }

        Company winner = turn.winner(numTiles, player, npcQueue);
        if (turn.boardFull(numTiles, player, npcQueue) || winner != null) {
            if (winner != null) {
                System.out.println(winner.getName() + " wins!");
            } else {
                System.out.println("Game ended! no player won.");
            }
            // TODO: GAME ENDS
        }

        Event event = new EventImpl();
        Event.EventType eventType = event.randomEvent();
        // TODO: eventType helps NPC make decisions
        if (eventType != Event.EventType.NONE) {
            int [] updated = event.updateMarket(eventType, marketDemand, marketPrice);
            this.marketDemand = updated[0];
            this.marketPrice = updated[1];
        }
    }

    /**
     * called by game controller when user chooses to buy or sell tiles
     * @param num number to dictate user option
     */
    public void tiles (int num) {
        // Assumes that there will be an event handler in game controller that calls this function when user
        // selects to buy or sell tiles
        int amount = 0; // TODO: MISSING NUM TILES INPUT FROM GUI
        switch (num) {
            case 1 -> {
                System.out.println(1);
                player.tiles(amount, "Purchase", this.tileGrid);
            }
            case 2 -> {
                System.out.println(2);
                player.tiles(amount, "Sell", this.tileGrid);
            }
        }
    }

    public List<Company> sortCompaniesBy(DataType dataType) {
        List<Company> companyList = getCompanyList();
        companyList.sort(Company.comparatorBy(dataType));

        return companyList;
    }

    /* ***** GETTERS/SETTERS ***** */

    public UserCompany getPlayer() {
        return this.player;
    }

    public Queue<Company> getNPCs() {
        return this.npcQueue;
    }


    /**
     * @return the 2D grid of tiles
     */
    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    /**
     * Sets the tile grid from a loaded save file
     */
    public void loadTileGrid(Tile[][] tileGrid) {
        this.tileGrid = tileGrid;
    }

    /**
     * @return the list of companies
     */
    public List<Company> getCompanyList() {
        List<Company> companyList = new ArrayList<>(npcQueue);
        companyList.add(player);
        return companyList;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public void setTurnNum(int turnNum) {
        this.turnNum = turnNum;
    }
}

package mc.manifestcompany.gamelogic;

import mc.manifestcompany.company.Company;

import java.util.Queue;

public interface Turn {

    /**
     * generates a random number of goods sold ranging from 0 to demand
     * @return number of goods sold for all companies this round
     */
    int randomGoodsSold();

    /**
     * update company stats based on number of goods sold this round and the company's stats
     * @param numGoods number of goods sold this round
     * @param company the company to update
     */
    void turn(int numGoods, Company company);

    /**
     * check if the company is still a valid player
     * @param company the company to check
     */
    boolean validCompany(Company company);

    /**
     * check if the game is still a valid game
     * @param numTiles total number of tiles on the board
     * @param player player
     * @param npcQueue queue of npcs in the game
     * @return whether the game is still a valid game
     */
    boolean boardFull(int numTiles, Company player, Queue<Company> npcQueue);

    /**
     * gets the winner of the game - a player wins if it has more than half of the tiles
     * @param numTiles total number of tiles on the board
     * @param player player
     * @param npcQueue queue of npcs in the game
     * @return the company that won, or null if the board is filled with no winner
     */
    Company winner(int numTiles, Company player, Queue<Company> npcQueue);


}

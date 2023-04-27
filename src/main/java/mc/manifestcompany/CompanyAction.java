package mc.manifestcompany;

/**
 * Interface for interacting with Companies.
 * Defines the actions which companies can perform.
 * Uses a Mediator design pattern.
 * @author Team Manifest Company
 */
public interface CompanyAction {

    /**
     * invests in a sector of a company. POSSIBLY SPLIT INTO MORE METHODS OR ANOTHER DESIGN PATTERN
     * @param num amount to invest
     * @param sector what sector to invest in
     * @param company the company performing the action
     */
    void invest(int num, String sector, Company company);

    /**
     * handles purchasing or selling tiles
     * @param numTile number of tiles to buy or sell
     * @param method purchase or sell
     * @param company the company performing the action
     */
    void tiles(int numTile, String method, Company company);

    /**
     * handles marketing investment
     * @param amount amount to invest - each $100 leads to a 10% multiplier increase
     * @return whether the action was successful
     */
    boolean investMarketing(int amount);

    /**
     * handles R&D investment
     * @param amount amount to invest - each $100 leads to a $10 price increase
     * @return whether the action was successful
     */
    boolean investRD(int amount);

    /**
     * handles Raw Goods investment
     * @param amount amount to invest - each $100 leads to 1 unit capacity increase
     * @return whether the action was successful
     */
    boolean investGoods(int amount);

    /**
     * handles Human Capital investment
     * @param amount amount to invest - each $100 leads to $3 cost decrease
     * @return whether the action was successful
     */
    boolean investHumanCapital(int amount);

    /**
     * handles purchasing of tiles
     * @param numTiles number of tiles to purchase - each tile is $300
     * @return whether action was successful
     */
    boolean purchaseTiles(int numTiles);

    /**
     * handles selling of tiles
     * @param numTiles number of tiles to sell - each tile is worth $100
     * @return whether action was successful
     */
    boolean sellTiles(int numTiles);

    /**
     * Handles changes to cash on hand
     * @param amount increase or decrease in cash based on whether amount passed in is + or -
     * @return whether the action was successful
     */
    boolean handleCash(int amount);

}

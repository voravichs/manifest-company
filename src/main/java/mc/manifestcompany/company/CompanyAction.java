package mc.manifestcompany.company;

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


}

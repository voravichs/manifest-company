package mc.manifestcompany;

/**
 * Defines an abstract company.
 * Subclasses include a User and NPC company.
 * @author Team Manifest Company
 */
public abstract class Company {
    /**
     * Calls invest method in CompanyAction.
     * @param num amount to invest
     * @param sector sector to invest in
     */
    public abstract void invest(int num, String sector);


    /**
     * Calls tiles method in CompanyAction
     * @param numTiles number of tiles to buy or sell
     * @param method "Purchase" or "Sell" tiles
     */
    public abstract void tiles(int numTiles, String method);


    /**
     * Sets the company name.
     * @param name company name to set
     */
    public abstract void setName(String name);

    /**
     * Gets the company name.
     * @return the company name
     */
    public abstract String getName();

    /**
     * Gets the company's stats.
     * @return the company stats.
     */
    public abstract CompanyStats getStats();

    /**
     * Sets the company's stats
     * @param stats company stats to set
     */
    public abstract void setStats(CompanyStats stats);




}

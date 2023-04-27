package mc.manifestcompany;

/**
 * Interface for interacting with Companies.
 * Defines the statistics of each company.
 * Uses a Mediator design pattern.
 * @author Team Manifest Company
 */
public interface CompanyStats {

    /**
     * @return company's selling price
     */
    public int getPrice();

    /**
     * sets the company's selling price
     * @param amount the amount to set the selling price to
     */
    public void setPrice(int amount);

    /**
     * @return company's quantity multiplier
     */
    public double getMultiplier();

    /**
     * sets the company's quantity multiplier
     * @param amount the amount to set the quantity multiplier to
     */
    public void setMultiplier(double amount);

    /**
     * @return company's production capacity
     */
    public int getCapacity();

    /**
     * sets the company's production capacity
     * @param amount the amount to set the production capacity to
     */
    public void setCapacity(int amount);

    /**
     * @return company's production cost;
     */
    public int getCost();

    /**
     * sets the company's production cost
     * @param amount the amount to set the production cost to
     */
    public void setCost(int amount);

    /**
     * @return company's cash on hand
     */
    public int getCash();

    /**
     * sets the company's cash on hand
     * @param amount the amount to set cash to
     */
    public void setCash(int amount);

    /**
     * sets the company's most recent revenue
     * @param amount most recent revenue to set
     */
    public void setRevenue(int amount);

    /**
     * @return company's most recent revenue
     */
    public int getRevenue();

    /**
     * sets the company's most recent cost of goods sold
     * @param amount most recent cost of goods sold to set
     */
    public void setCogs(int amount);

    /**
     * @return company's most recent cost of goods sold
     */
    public int getCogs();
    /**
     * sets the company's most recent profit
     * @param amount most recent profit to set
     */
    public void setProfit(int amount);

    /**
     * @return company's most recent profit
     */
    public int getProfit();

    /**
     * @return company's number of tiles
     */
    public int getNumTiles();

    /**
     * sets the company's number of tiles
     * @param amount the number of tiles to set
     */
    public void setNumTiles(int amount);




}

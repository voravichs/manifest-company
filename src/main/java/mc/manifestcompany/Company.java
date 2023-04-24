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
     * Sets the company name.
     * @param name company name to set
     */
    public abstract void setName(String name);

    /**
     * Gets the company name.
     * @return the company name
     */
    public abstract String getName();
}

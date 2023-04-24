package mc.manifestcompany;

/**
 * Interface for interacting with Companies.
 * Defines the actions which companies can perform.
 * Uses a Mediator design pattern.
 * @author Team Manifest Company
 */
public interface CompanyAction {

    /**
     * TODO: invests in a sector of a company. POSSIBLY SPLIT INTO MORE METHODS OR ANOTHER DESIGN PATTERN
     * @param num amount to invest
     * @param sector what sector to invest in
     * @param company what company to invest in
     */
    void invest(int num, String sector, Company company);
}

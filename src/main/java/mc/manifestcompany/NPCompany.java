package mc.manifestcompany;

/**
 * Subclass of Player that defines a Non-Playable Company (NPC).
 * TODO: ADD METHODS THAT DIFFERENTIATE THIS FROM USER
 * @author Team Manifest Company
 */
public class NPCompany extends Company {
    private String name;
    private CompanyAction actions;
    private CompanyStats stats;

    public NPCompany(String name, CompanyAction actions, CompanyStats stats) {
        this.name = name;
        this.actions = actions;
        this.stats = stats;
    }
    @Override
    public void invest(int num, String sector) {
        System.out.println("Investing in " + this.name);
        actions.invest(num, sector, this);
    }

    @Override
    public void tiles(int num, String method) {
        System.out.println("Purchasing/Selling tiles in " + this.name);
        actions.tiles(num, method, this);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public CompanyStats getStats() {
        return this.stats;
    }

    @Override
    public void setStats(CompanyStats stats) {
        this.stats = stats;
    }
}

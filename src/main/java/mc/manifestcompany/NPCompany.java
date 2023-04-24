package mc.manifestcompany;

/**
 * Subclass of Player that defines a Non-Playable Company (NPC).
 * TODO: ADD METHODS THAT DIFFERENTIATE THIS FROM USER
 * @author Team Manifest Company
 */
public class NPCompany extends Company {
    private String name;
    private CompanyAction actions;

    public NPCompany(String name, CompanyAction actions) {
        this.name = name;
        this.actions = actions;
    }
    @Override
    public void invest(int num, String sector) {
        System.out.println("Investing in " + this.name);
        actions.invest(num, sector, this);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

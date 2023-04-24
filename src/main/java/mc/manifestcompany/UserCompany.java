package mc.manifestcompany;

/**
 * Subclass of Player that defines a Playable Company of the user.
 * TODO: ADD METHODS THAT DIFFERENTIATE THIS FROM NPC
 * @author Team Manifest Company
 */
public class UserCompany extends Company {
    private String name;
    private CompanyAction actions;

    public UserCompany(String name, CompanyAction actions) {
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

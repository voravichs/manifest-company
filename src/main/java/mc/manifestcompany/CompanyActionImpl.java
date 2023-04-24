package mc.manifestcompany;

/**
 * Implementation of the company actions interface.
 * @author Team Manifest Company
 */
public class CompanyActionImpl implements CompanyAction {

    @Override
    public void invest(int num, String sector, Company company) {
        // TODO: fill this in
        System.out.println("INVESTING: " + num +
                ", in sector: " + sector +
                ", of company: " + company.getName());
    }
}

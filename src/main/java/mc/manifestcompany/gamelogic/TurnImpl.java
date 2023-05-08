package mc.manifestcompany.gamelogic;

import mc.manifestcompany.DataType;
import mc.manifestcompany.company.Company;

import java.util.EnumMap;
import java.util.List;
import java.util.Queue;

public class TurnImpl implements Turn {

    private int marketDemand;
    private int marketPrice;
    public TurnImpl(int demand, int price) {
        this.marketDemand = demand;
        this.marketPrice = price;
    }
    @Override
    public int randomGoodsSold() {
        return (int) (Math.random() * marketDemand);
    }

    @Override
    public void turn(int numGoods, Company company) {
        EnumMap<DataType, Integer> stats = company.getStats();

        // num goods a company can sell
        numGoods = (int) (numGoods * (1.0 + stats.get(DataType.MULTIPLIER) / 10));
        // company can't sell more than market demand or capacity
        numGoods = Math.min(numGoods, marketDemand);
        numGoods = Math.min(numGoods, stats.get(DataType.CAPACITY));
        // company can't sell higher price than market price
        int price = Math.min(stats.get(DataType.PRICE), marketPrice);
        // cost cannot be negative
        int cost = Math.max(stats.get(DataType.COST), 0);

        int revenue = numGoods * price;
        int cogs = numGoods * cost;
        int profit = revenue - cogs;
        company.setRevenue(revenue);
        company.setCogs(cogs);
        company.setProfit(profit);
        company.getStats().put(DataType.CASH, company.getStats().get(DataType.CASH) + profit);
    }

    @Override
    public boolean validCompany(Company company) {
        return (company.getStats().get(DataType.CASH) > 0 && company.getStats().get(DataType.TILES) > 0);
    }

    @Override
    public boolean boardFull(int numTiles, Company player, Queue<Company> npcQueue) {
        int tileCount = player.getStats().get(DataType.TILES);
        for (Company npc : npcQueue) {
            tileCount += npc.getStats().get(DataType.TILES);
        }
        return tileCount >= numTiles;
    }

    @Override
    public Company winner(int numTiles, List<Company> companyList) {
        Company companyWithMostTiles = companyList.get(0);
        for (Company company : companyList) {
            if (company.getStats().get(DataType.TILES)
                    > companyWithMostTiles.getStats().get(DataType.TILES)) {
                companyWithMostTiles = company;
            }
        }
        return companyWithMostTiles;
    }


}

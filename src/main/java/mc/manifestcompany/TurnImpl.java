package mc.manifestcompany;

import java.util.Queue;

public class TurnImpl implements  Turn {

    private int marketDemand;
    private int marketPrice;
    public TurnImpl ( int demand, int price) {
        this.marketDemand = demand;
        this.marketPrice = price;
    }
    @Override
    public int randomGoodsSold() {
        return (int) (Math.random() * marketDemand);
    }

    @Override
    public void turn(int numGoods, Company company) {
        CompanyStats stats = company.getStats();

        numGoods = (int) (numGoods * stats.getMultiplier());  // num goods a company can sell
        numGoods = Math.min(numGoods, marketDemand);          // company can't sell more than market demand or capacity
        numGoods = Math.min(numGoods, stats.getCapacity());
        int price = Math.min(stats.getPrice(), marketPrice);  // company can't sell higher price than market price
        int cost = Math.max(stats.getCost(), 0);              // cost can be negative

        int revenue = numGoods * price;
        int cogs = numGoods * cost;
        int profit = revenue - cogs;
        stats.setRevenue(revenue);
        stats.setCogs(cogs);
        stats.setProfit(profit);
        stats.setCash(stats.getCash() + profit);
        company.setStats(stats);
    }

    @Override
    public boolean validCompany(Company company) {
        CompanyStats stats = company.getStats();
        return (stats.getCash() < 0 && stats.getNumTiles() < 0);
    }

    @Override
    public boolean validGame(int numTiles, Company player, Queue<Company> npcQueue) {
        int tileCount = player.getStats().getNumTiles();
        for (Company npc : npcQueue) {
            tileCount += npc.getStats().getNumTiles();
        }
        return tileCount >= numTiles;
    }

    @Override
    public Company winner(int numTiles, Company player, Queue<Company> npcQueue) {
        int threshold = numTiles / 2;
        if (player.getStats().getNumTiles() >= threshold) {
            return player;
        }
        for (Company npc : npcQueue) {
            if (npc.getStats().getNumTiles() >= threshold) {
                return npc;
            }
        }
        return null;
    }


}

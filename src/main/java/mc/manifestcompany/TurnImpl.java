package mc.manifestcompany;

import java.util.HashMap;
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
        HashMap<Enum<DataType>, Integer> stats = company.getStats();

        numGoods = (int) (numGoods * (1.0 + stats.get(DataType.MULTIPLIER) / 10));  // num goods a company can sell
        numGoods = Math.min(numGoods, marketDemand);          // company can't sell more than market demand or capacity
        numGoods = Math.min(numGoods, stats.get(DataType.CAPACITY));
        int price = Math.min(stats.get(DataType.PRICE), marketPrice);  // company can't sell higher price than market price
        int cost = Math.max(stats.get(DataType.COST), 0);              // cost can be negative

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
    public Company winner(int numTiles, Company player, Queue<Company> npcQueue) {
        int threshold = numTiles / 2;
        if (player.getStats().get(DataType.TILES) >= threshold) {
            return player;
        }
        for (Company npc : npcQueue) {
            if (npc.getStats().get(DataType.TILES) >= threshold) {
                return npc;
            }
        }
        return null;
    }


}

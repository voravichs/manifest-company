package mc.manifestcompany.gamelogic;

public class EventImpl implements Event {
    @Override
    public EventType randomEvent() {
        double rand =  Math.random();
        double cur = 0;
        for (EventType event : EventType.values()) {
            cur += event.getProbability();
            if (cur >= rand) {
                return event;
            }
        }
        return EventType.NONE;
    }

    @Override
    public int[] updateMarket(EventType event, int marketDemand, int marketPrice) {
        int [] updated = {marketDemand, marketPrice};
        switch (event) {
            case PANDEMIC -> marketDemand = marketDemand / 2;
            case EXPANSION -> {
                marketDemand *= 2;
                marketPrice *= 2;
            }
            case RECESSION -> {
                marketDemand /= 2;
                marketPrice /= 2;
            }
            case COMPETITION -> {
                marketDemand *= 2;
                marketPrice /= 4;
            }
            case INNOVATION -> marketPrice *= 2;
            case WAR -> {
                marketDemand = 10;
                marketPrice = 10;
            }
        }
        if (marketDemand == 0) {
            marketDemand = 1;
        }
        if (marketPrice == 0) {
            marketPrice = 1;
        }

        updated[0] = marketDemand;
        updated[1] = marketPrice;
        return updated;
    }
}
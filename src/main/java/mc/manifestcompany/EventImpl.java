package mc.manifestcompany;

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
        switch(event) {
            case PANDEMIC:
                marketDemand = marketDemand / 2;
                break;
            case EXPANSION:
                marketDemand *= 2;
                marketPrice *= 2;
                break;
            case RECESSION:
                marketDemand /= 2;
                marketPrice /= 2;
                break;
            case COMPETITION:
                marketDemand *= 2;
                marketPrice /= 4;
                break;
            case INNOVATION:
                marketPrice *= 2;
                break;
            case WAR:
                marketDemand = 1;
                marketPrice = 1;
                break;
        }
        if (marketDemand == 0 ) { marketDemand = 1; }
        if (marketPrice == 0 ) { marketPrice = 1; }

        updated[0] = marketDemand;
        updated[1] = marketPrice;
        return updated;
    }
}

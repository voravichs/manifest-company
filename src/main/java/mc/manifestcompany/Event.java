package mc.manifestcompany;

import javafx.scene.paint.Color;

public interface Event {
    enum EventType {
        NONE(0.5),
        PANDEMIC(0.03),
        COMPETITION(0.15),
        RECESSION(0.08),
        EXPANSION(0.08),
        INNOVATION(0.15),
        WAR(0.01);

        private final double likelihood;

        EventType(double n) {
            this.likelihood = n;
        }
        public double getProbability() {
            return likelihood;
        }
    }


    /**
     * based on the likelihood of each event, randomly generates an event (or no event)
     * at the end of each turn
     * @return returns enum that represents the event returned
     */
    EventType randomEvent();

    /**
     * updates market demand and market price to reflect the event
     * @param event event that happened this round
     * @param marketDemand current market demand
     * @param marketPrice current market price
     * @return an int [] that holds market demand in index 0 and market price in index 1
     */
    int[] updateMarket(EventType event, int marketDemand, int marketPrice);

}

package mc.manifestcompany.gamelogic;

public interface Event {
    enum EventType {
        NONE(0.5, "The market will be normal this turn."),
        PANDEMIC(0.03, "WHO has reported a worldwide pandemic has broken out!\n" +
                        "Sales will be heavily affected."),
        COMPETITION(0.15, "A hip new startup has started selling similar products!\n" +
                        "This is sure to drive more sales and competition."),
        RECESSION(0.08, "A global recession has hit the market!\n" +
                        "Sales and prices will drop."),
        EXPANSION(0.08, "Investors have funded an acquisition of another company!\n" +
                        "This will greatly increase sales and prices."),
        INNOVATION(0.15,"New technologies have been developed to streamline production!\n" +
                        "The quality of products and prices will increase."),
        WAR(0.01, "A war has broken out between two major countries many companies operate!\n" +
                        "Sales will be almost non-existent this turn.");

        private final double likelihood;

        private final String text;

        EventType(double n, String text) {
            this.likelihood = n;
            this.text = text;
        }
        public double getProbability() {
            return likelihood;
        }
        public String getText() {
            return text;
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
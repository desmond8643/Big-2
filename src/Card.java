public class Card {
    private String rank;
    private String suit;
    private int rankValue;
    private int suitValue;

    // constructor
    Card(String rank, String suit, int rankValue, int suitValue) {
        this.rank = rank;
        this.suit = suit;
        this.rankValue = rankValue;
        this.suitValue = suitValue;
        // "this" is to replace the keyword from the variables
    }

    String name() {
        rank = rank.toLowerCase();
        suit = suit.toLowerCase();
        return this.rank + "-of-" + this.suit;
    }

    void showSize() {
        System.out.println("rank: " + this.rankValue + ", suit: " + this.suitValue);
    }

    public String getRank() {
        return rank;
    }
    public String getSuit() {
        return suit;
    }
    public int getRankValue() {
        return rankValue;
    }
    public int getSuitValue() {
        return suitValue;
    }
}

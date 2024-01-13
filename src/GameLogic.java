import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.Timer;

public class GameLogic {
    private final GamePanel gamePanel;
    final String[] ranks = {"3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2"};
    final String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};
    ArrayList<String>sortedFirstHalf = new ArrayList<>();
    ArrayList<String>sortedSecondHalf = new ArrayList<>();
    ArrayList<Card>sortedFirstHalfObjects = new ArrayList<>();
    ArrayList<Card>sortedSecondHalfObjects = new ArrayList<>();
    Timer timer = new Timer();
    GameLogic(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        addEventListeners();
    }

    private void addEventListeners() {
        gamePanel.confirmButton.addActionListener(e -> {
            gamePanel.sortSelectedCards();

            int selectedCardSize = gamePanel.selectedCards.size();
            String numberOfCards;

            switch (selectedCardSize) {
                case 1, 2, 3, 4 -> {
                    numberOfCards = switch (selectedCardSize) {
                        case 1 -> "Single card";
                        case 2 -> "Pair";
                        case 3 -> "Triples";
                        case 4 -> "Four cards";
                        default -> "Invalid";
                    };
                    if (gamePanel.isSelectedCardValid(numberOfCards)) {
                        gamePanel.confirmButton.setEnabled(false);
                        gamePanel.passButton.setEnabled(false);
                        gamePanel.renderCardInTable();
                        if (!sortedFirstHalfObjects.isEmpty()) {
                            TimerTask computer = new TimerTask() {
                                @Override
                                public void run() {
                                    gamePanel.computerMove(numberOfCards);
                                    System.out.println(sortedSecondHalfObjects.size());
                                    if (!sortedSecondHalfObjects.isEmpty()) {
                                        gamePanel.confirmButton.setEnabled(true);
                                        gamePanel.passButton.setEnabled(true);
                                        reactivatePlayerMouseListener();
                                    } else {
                                        gamePanel.message.setText("Computer wins!");
                                        gamePanel.message.setBounds(680, 310, 600, 600);
                                    }

                                }
                            };
                            gamePanel.message.setText("Computer's turn");
                            gamePanel.message.setBounds(680, 310, 600, 600);
                            timer.schedule(computer, 3000);
                        } else {
                            gamePanel.message.setText("You win!");
                            gamePanel.message.setBounds(725, 310, 600, 600);
                        }

                    } else {
                        gamePanel.message.setText("Invalid");
                        gamePanel.message.setBounds(770, 310, 600, 600);
                    }
                }
                case 5 -> {
                    // check player combination
                    String getPlayerCombination = gamePanel.getFiveCardCombination(gamePanel.selectedCards);
                    if (gamePanel.isSelectedCardValid(getPlayerCombination)) {
                        System.out.println(getPlayerCombination);
                        gamePanel.renderCardInTable();
                        if (!sortedFirstHalfObjects.isEmpty()) {
                            TimerTask computer = new TimerTask() {
                                @Override
                                public void run() {
                                    gamePanel.computerMove(getPlayerCombination);
                                    if (!sortedSecondHalfObjects.isEmpty()) {
                                        gamePanel.confirmButton.setEnabled(true);
                                        gamePanel.passButton.setEnabled(true);
                                        reactivatePlayerMouseListener();
                                    } else {
                                        gamePanel.message.setText("Computer wins!");
                                        gamePanel.message.setBounds(680, 310, 600, 600);
                                    }
                                }
                            };
                            gamePanel.message.setText("Computer's turn");
                            gamePanel.message.setBounds(680, 310, 600, 600);
                            timer.schedule(computer, 3000);
                        } else {
                            gamePanel.message.setText("You win!");
                            gamePanel.message.setBounds(725, 310, 600, 600);
                        }
                    } else {
                        gamePanel.message.setText("Invalid");
                        gamePanel.message.setBounds(770, 310, 600, 600);
                    }
                }
            }
        });

        gamePanel.passButton.addActionListener(e -> {
            gamePanel.confirmButton.setEnabled(false);
            gamePanel.passButton.setEnabled(false);
            TimerTask computer = new TimerTask() {
                @Override
                public void run() {
                    gamePanel.computerRandomMove();
                    System.out.println(sortedSecondHalfObjects.size());
                    if (!sortedSecondHalfObjects.isEmpty()) {
                        gamePanel.confirmButton.setEnabled(true);
                        gamePanel.passButton.setEnabled(true);
                        reactivatePlayerMouseListener();
                    } else {
                        gamePanel.message.setText("Computer wins!");
                        gamePanel.message.setBounds(680, 310, 600, 600);
                    }
                }
            };
            gamePanel.message.setText("Computer's turn");
            gamePanel.message.setBounds(680 , 310, 600, 600);
            timer.schedule(computer, 3000);
        });

        gamePanel.exitItem.addActionListener(e -> {
            System.exit(0);
        });

        gamePanel.playAgainItem.addActionListener(e -> {
            gamePanel.resetGame();
        });
    }

    public void createShuffledCards() {

        ArrayList<Card> cards = new ArrayList<Card>();

        // add all the cards objects with its attribute to the card ArrayList
        for (String suit : suits) {
            for (int value = 0; value < ranks.length; value++) {
                Card card = new Card(ranks[value], suit, value + 1, getSuitValue(suit));
                cards.add(card);
            }
        }
        // Shuffle cards (ArrayList)
        Collections.shuffle(cards);

        // Create a new object of array that stores the shuffled card
        Card[] shuffledCards = new Card[cards.size()];
        cards.toArray(shuffledCards);

        // Create a copy of the first half and second half of shuffled cards
        // 2 hands (2 players)
        Card[] firstHalf = Arrays.copyOfRange(shuffledCards, 0, 13);
        Card[] secondHalf = Arrays.copyOfRange(shuffledCards, 13, 26);

        // forward loop sort
        for (int i = 1; i <= ranks.length; i++) {
            for (int j = 1; j <= suits.length; j++) {
                for (Card card : firstHalf) { //
                    if (card.getSuitValue() == j && card.getRankValue() == i) {
                        sortedFirstHalf.add(card.name());
                        sortedFirstHalfObjects.add(card);
                    }
                }
            }
        }

        for (int i = 1; i <= ranks.length; i++) {
            for (int j = 1; j <= suits.length; j++) {
                for (Card card : secondHalf) {
                    if (card.getSuitValue() == j && card.getRankValue() == i) {
                        sortedSecondHalf.add(card.name());
                        sortedSecondHalfObjects.add(card);
                    }
                }
            }
        }
    }

    private static int getSuitValue(String suit) {
        return switch (suit) {
            case "Diamonds" -> 1;
            case "Clubs" -> 2;
            case "Hearts" -> 3;
            case "Spades" -> 4;
            default -> 0;
        };
    }

    public void reactivatePlayerMouseListener() {
        MouseListener[] playerMouseListeners = new MouseListener[gamePanel.handCards.length];
        int cardHandLength = sortedFirstHalfObjects.size();
        int gap = gamePanel.CARD_WIDTH + (gamePanel.GAME_WIDTH - (cardHandLength * gamePanel.CARD_WIDTH)) / (cardHandLength + 2);

        for (int i = 0; i < gamePanel.handCards.length; i++) {
            final int index = i;
            playerMouseListeners[index] = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (gamePanel.selectedCards.contains(sortedFirstHalfObjects.get(index))) {
                        gamePanel.selectedCards.remove(sortedFirstHalfObjects.get(index));
                        gamePanel.handCards[index].setBounds((index + 1) * gap - 100 , 20, 102, 153);
                        System.out.println(gamePanel.selectedCards);
                    } else if (gamePanel.selectedCards.size() < 5) {
                        gamePanel.selectedCards.add(sortedFirstHalfObjects.get(index));
                        gamePanel.handCards[index].setBounds((index + 1) * gap - 100 , 0, 102, 153);
                        System.out.println(gamePanel.selectedCards);
                    } else {
                        System.out.println("Selected more than 5 cards");
                    }
                }
            };
            gamePanel.handCards[i].addMouseListener(playerMouseListeners[i]);
        }
    }
}
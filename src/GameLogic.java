import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.Timer;

public class GameLogic {
    private final GamePanel gamePanel;
    private ComputerLogic computerLogic;
    int numberOfPass = 0;
    int numberOfComputerPlayers = 2;
    final String[] ranks = {"3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2"};
    final String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};
    ArrayList<String>sortedFirstHalf = new ArrayList<>();
    ArrayList<String>sortedSecondHalf = new ArrayList<>();
    ArrayList<String>sortedThirdHalf = new ArrayList<>();
    ArrayList<Card>sortedFirstHalfObjects = new ArrayList<>();
    ArrayList<Card>sortedSecondHalfObjects = new ArrayList<>();
    ArrayList<Card>sortedThirdHalfObjects = new ArrayList<>();
    Timer timer = new Timer();
    GameLogic(GamePanel gamePanel, ComputerLogic computerLogic) {
        this.gamePanel = gamePanel;
        this.computerLogic = computerLogic;
        addEventListeners();
    }

    public void handleComputerTurns() {
        TimerTask yourTurn = new TimerTask() {
            @Override
            public void run() {
                if (numberOfPass < numberOfComputerPlayers) {
                    gamePanel.passButton.setEnabled(true);
                }
                gamePanel.confirmButton.setEnabled(true);
                gamePanel.reactivateButton.setEnabled(true);
                reactivatePlayerMouseListener();
                gamePanel.message.setText("Your turn");
                gamePanel.message.setBounds(725 , 310, 600, 600);
            }
        };
        TimerTask computer2SetText = new TimerTask() {
            @Override
            public void run() {
                gamePanel.message.setText("Computer 2's turn");
            }
        };
        TimerTask computer2 = new TimerTask() {
            @Override
            public void run() {
                ArrayList<Card> copyTableObjects = new ArrayList<>(gamePanel.cardTableObjects);
                if (numberOfPass == 2) {
                    numberOfPass = 0;
                    computerLogic.computerRandomMove(sortedThirdHalfObjects);
                } else {
                    int cardTableObjectsSize = gamePanel.cardTableObjects.size();
                    String numberOfCards = switch (cardTableObjectsSize) {
                        case 1 -> "Single card";
                        case 2 -> "Pair";
                        case 3 -> "Triples";
                        case 4 -> "Four cards";
                        case 5 -> gamePanel.getFiveCardCombination(gamePanel.cardTableObjects);
                        default -> "Invalid";
                    };
                    computerLogic.computerMove(numberOfCards, sortedThirdHalfObjects);
                }
                gamePanel.computer2Cards.setText(sortedThirdHalfObjects.size() + " cards");
                if (copyTableObjects.equals(gamePanel.cardTableObjects)) {
                    gamePanel.message.setText("Computer 2 Pass");
                    gamePanel.message.setBounds(680, 310, 600, 600);
                    numberOfPass++;
                } else if (sortedThirdHalfObjects.isEmpty()) {
                    gamePanel.message.setText("Computer 2 wins!");
                    gamePanel.message.setBounds(680, 310, 600, 600);
                } else {
                    String numberOfCards = switch (gamePanel.cardTableObjects.size()) {
                        case 1 -> "Single card";
                        case 2 -> "Pair";
                        case 3 -> "Triples";
                        case 4 -> "Four cards";
                        case 5 -> gamePanel.getFiveCardCombination(gamePanel.cardTableObjects);
                        default -> "Invalid";
                    };
                    gamePanel.message.setText("Computer 2 played " + numberOfCards);
                    numberOfPass = 0;
                }
                System.out.println(numberOfPass);
                System.out.println("Computer 2: " + sortedThirdHalfObjects.size());
                if (!sortedThirdHalfObjects.isEmpty()) {
                    timer.schedule(yourTurn, 3000);
                }
            }
        };
        TimerTask computer = new TimerTask() {
            @Override
            public void run() {
                ArrayList<Card> copyTableObjects = new ArrayList<>(gamePanel.cardTableObjects);
                if (numberOfPass == 2) {
                    numberOfPass = 0;
                    computerLogic.computerRandomMove(sortedSecondHalfObjects);
                } else {
                    int cardTableObjectsSize = gamePanel.cardTableObjects.size();
                    String numberOfCards = switch (cardTableObjectsSize) {
                        case 1 -> "Single card";
                        case 2 -> "Pair";
                        case 3 -> "Triples";
                        case 4 -> "Four cards";
                        case 5 -> gamePanel.getFiveCardCombination(gamePanel.cardTableObjects);
                        default -> "Invalid";
                    };
                    computerLogic.computerMove(numberOfCards, sortedSecondHalfObjects);
                }
                gamePanel.computer1Cards.setText(sortedSecondHalfObjects.size() + " cards");
                if (copyTableObjects.equals(gamePanel.cardTableObjects)) {
                    gamePanel.message.setText("Computer 1 Pass");
                    gamePanel.message.setBounds(680, 310, 600, 600);
                    numberOfPass++;
                } else if (sortedSecondHalfObjects.isEmpty()) {
                    gamePanel.message.setText("Computer 1 wins!");
                    gamePanel.message.setBounds(680, 310, 600, 600);
                } else {
                    String numberOfCards = switch (gamePanel.cardTableObjects.size()) {
                        case 1 -> "Single card";
                        case 2 -> "Pair";
                        case 3 -> "Triples";
                        case 4 -> "Four cards";
                        case 5 -> gamePanel.getFiveCardCombination(gamePanel.cardTableObjects);
                        default -> "Invalid";
                    };
                    gamePanel.message.setText("Computer 1 played " + numberOfCards);
                    numberOfPass = 0;
                }
                System.out.println(numberOfPass);
                System.out.println("Computer 1: " + sortedSecondHalfObjects.size());
                if (!sortedSecondHalfObjects.isEmpty()) {
                    timer.schedule(computer2SetText, 3000);
                    timer.schedule(computer2, 6000);
                }
            }
        };

        gamePanel.message.setText("Computer 1's turn");
        gamePanel.message.setBounds(680, 310, 600, 600);
        timer.schedule(computer, 3000);
    }

    private void addEventListeners() {
        gamePanel.confirmButton.addActionListener(e -> {
            sortSelectedCards();

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
                        gamePanel.reactivateButton.setEnabled(false);
                        gamePanel.renderCardInTable();
                        numberOfPass = 0;
                        System.out.println(numberOfPass);
                        if (sortedFirstHalfObjects.isEmpty()) {
                            gamePanel.message.setText("You Win!");
                        } else {
                            handleComputerTurns();
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
                        gamePanel.confirmButton.setEnabled(false);
                        gamePanel.passButton.setEnabled(false);
                        gamePanel.reactivateButton.setEnabled(false);
                        gamePanel.renderCardInTable();
                        numberOfPass = 0;
                        System.out.println(numberOfPass);
                        if (sortedFirstHalfObjects.isEmpty()) {
                            gamePanel.message.setText("You Win!");
                        } else {
                            handleComputerTurns();
                        }
                    } else {
                        gamePanel.message.setText("Invalid");
                        gamePanel.message.setBounds(770, 310, 600, 600);
                    }
                }
            }
        });

        gamePanel.passButton.addActionListener(e -> {
            numberOfPass++;
            System.out.println(numberOfPass);
            gamePanel.confirmButton.setEnabled(false);
            gamePanel.passButton.setEnabled(false);
            gamePanel.reactivateButton.setEnabled(false);

            TimerTask yourTurn = new TimerTask() {
                @Override
                public void run() {
                    if (numberOfPass < 2) {
                        gamePanel.passButton.setEnabled(true);
                    }
                    gamePanel.confirmButton.setEnabled(true);
                    gamePanel.reactivateButton.setEnabled(true);
                    gamePanel.message.setText("Your turn");
                    gamePanel.message.setBounds(725 , 310, 600, 600);
                    reactivatePlayerMouseListener();
                }
            };
            TimerTask computer2SetText = new TimerTask() {
                @Override
                public void run() {
                    gamePanel.message.setText("Computer 2's turn");
                }
            };
            TimerTask computer2 = new TimerTask() {
                @Override
                public void run() {
                    ArrayList<Card> copyTableObjects = new ArrayList<>(gamePanel.cardTableObjects);

                    if (numberOfPass == 2) {
                        numberOfPass = 0;
                        computerLogic.computerRandomMove(sortedThirdHalfObjects);
                    } else {
                        int cardTableObjectsSize = gamePanel.cardTableObjects.size();
                        String numberOfCards = switch (cardTableObjectsSize) {
                            case 1 -> "Single card";
                            case 2 -> "Pair";
                            case 3 -> "Triples";
                            case 4 -> "Four cards";
                            case 5 -> gamePanel.getFiveCardCombination(gamePanel.cardTableObjects);
                            default -> "Invalid";
                        };
                        computerLogic.computerMove(numberOfCards, sortedThirdHalfObjects);
                    }
                    gamePanel.computer2Cards.setText(sortedThirdHalfObjects.size() + " cards");
                    if (copyTableObjects.equals(gamePanel.cardTableObjects)) {
                        gamePanel.message.setText("Computer 2 Pass");
                        gamePanel.message.setBounds(680, 310, 600, 600);
                        numberOfPass++;
                    } else if (sortedThirdHalfObjects.isEmpty()) {
                        gamePanel.message.setText("Computer 2 wins!");
                        gamePanel.message.setBounds(680, 310, 600, 600);
                    } else {
                        String numberOfCards = switch (gamePanel.cardTableObjects.size()) {
                            case 1 -> "Single card";
                            case 2 -> "Pair";
                            case 3 -> "Triples";
                            case 4 -> "Four cards";
                            case 5 -> gamePanel.getFiveCardCombination(gamePanel.cardTableObjects);
                            default -> "Invalid";
                        };
                        gamePanel.message.setText("Computer 2 played " + numberOfCards);
                        numberOfPass = 0;
                    }
                    System.out.println(numberOfPass);
                    System.out.println("Computer 2: " + sortedThirdHalfObjects.size());
                    if (!sortedThirdHalfObjects.isEmpty()) {
                        timer.schedule(yourTurn, 3000);
                    }
                }
            };
            TimerTask computer = new TimerTask() {
                @Override
                public void run() {
                    ArrayList<Card> copyTableObjects = new ArrayList<>(gamePanel.cardTableObjects);
                    if (numberOfPass == 2) {
                        numberOfPass = 0;
                        computerLogic.computerRandomMove(sortedSecondHalfObjects);
                    } else {
                        int cardTableObjectsSize = gamePanel.cardTableObjects.size();
                        String numberOfCards = switch (cardTableObjectsSize) {
                            case 1 -> "Single card";
                            case 2 -> "Pair";
                            case 3 -> "Triples";
                            case 4 -> "Four cards";
                            case 5 -> gamePanel.getFiveCardCombination(gamePanel.cardTableObjects);
                            default -> "Invalid";
                        };
                        computerLogic.computerMove(numberOfCards, sortedSecondHalfObjects);
                    }
                    gamePanel.computer1Cards.setText(sortedSecondHalfObjects.size() + " cards");
                    if (copyTableObjects.equals(gamePanel.cardTableObjects)) {
                        gamePanel.message.setText("Computer 1 Pass");
                        gamePanel.message.setBounds(680, 310, 600, 600);
                        numberOfPass++;
                    } else if (sortedSecondHalfObjects.isEmpty()) {
                        gamePanel.message.setText("Computer 1 wins!");
                        gamePanel.message.setBounds(680, 310, 600, 600);
                    } else {
                        String numberOfCards = switch (gamePanel.cardTableObjects.size()) {
                            case 1 -> "Single card";
                            case 2 -> "Pair";
                            case 3 -> "Triples";
                            case 4 -> "Four cards";
                            case 5 -> gamePanel.getFiveCardCombination(gamePanel.cardTableObjects);
                            default -> "Invalid";
                        };
                        gamePanel.message.setText("Computer 1 played " + numberOfCards);
                        numberOfPass = 0;
                    }
                    System.out.println(numberOfPass);
                    System.out.println("Computer 1: " + sortedSecondHalfObjects.size());
                    if (!sortedSecondHalf.isEmpty()) {
                        timer.schedule(computer2SetText, 3000);
                        timer.schedule(computer2, 6000);
                    }
                }
            };
            gamePanel.message.setText("Computer 1's turn");
            gamePanel.message.setBounds(680, 310, 600, 600);
            timer.schedule(computer, 3000);
        });

        gamePanel.reactivateButton.addActionListener(e -> {
            reactivatePlayerMouseListener();
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
        Card[] thirdHalf = Arrays.copyOfRange(shuffledCards, 26, 39);

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

        for (int i = 1; i <= ranks.length; i++) {
            for (int j = 1; j <= suits.length; j++) {
                for (Card card : thirdHalf) {
                    if (card.getSuitValue() == j && card.getRankValue() == i) {
                        sortedThirdHalf.add(card.name());
                        sortedThirdHalfObjects.add(card);
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

    public void initializePlayerMouseListener() {
        MouseListener[] playerMouseListeners = new MouseListener[gamePanel.handCards.length];

        for (int i = 0; i < gamePanel.handCards.length; i++) {
            final int index = i;
            playerMouseListeners[index] = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (gamePanel.selectedCards.contains(sortedFirstHalfObjects.get(index))) {
                        gamePanel.selectedCards.remove(sortedFirstHalfObjects.get(index));
                        gamePanel.handCards[index].setBounds((index + 1) * gamePanel.gap - 100 , 20, 102, 153);
                        System.out.println(gamePanel.selectedCards);
                    } else if (gamePanel.selectedCards.size() < 5) {
                        gamePanel.selectedCards.add(sortedFirstHalfObjects.get(index));
                        gamePanel.handCards[index].setBounds((index + 1) * gamePanel.gap - 100 , 0, 102, 153);
                        System.out.println(gamePanel.selectedCards);
                    } else {
                        System.out.println("Selected more than 5 cards");
                    }
                }
            };
            gamePanel.handCards[i].addMouseListener(playerMouseListeners[i]);
        }
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

    public void sortSelectedCards() {
        // sort selectedCard
        ArrayList<Card> sortedSelectedCards = new ArrayList<>();
        for (int i = 1; i <= ranks.length; i++) {
            for (int j = 1; j <= suits.length; j++) {
                for (Card selectedCard : gamePanel.selectedCards) {
                    if (selectedCard.getSuitValue() == j && selectedCard.getRankValue() == i) {
                        sortedSelectedCards.add(selectedCard);
                    }
                }
            }
        }
        gamePanel.selectedCards.clear();
        gamePanel.selectedCards.addAll(sortedSelectedCards);
    }
}

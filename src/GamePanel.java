import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel {
    boolean computerPass = false;
    final int CARD_WIDTH = 102;
    final int GAME_WIDTH = 1600;
    final String[] ranks = {"3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2"};
    final String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};
    JFrame frame = new JFrame();
    JLabel label = new JLabel();
    JLabel[] handCards;
    JPanel cardDeck = new JPanel();
    JPanel cardTable = new JPanel();
    JPanel computerCardDeck = new JPanel();
    JButton confirmButton = new JButton();
    JButton passButton = new JButton();
    ArrayList<Card> selectedCards = new ArrayList<>();
    ArrayList<Card> computerSelectedCards = new ArrayList<>();
    ArrayList<Card> cardTableObjects = new ArrayList<>();
    ArrayList<ImageIcon> cardTableImages = new ArrayList<>();
    ArrayList<String>sortedFirstHalf = new ArrayList<>();
    ArrayList<String>sortedSecondHalf = new ArrayList<>();
    ArrayList<Card>sortedFirstHalfObjects = new ArrayList<>();
    ArrayList<Card>sortedSecondHalfObjects = new ArrayList<>();
    Timer timer = new Timer();

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
                for (Card card : firstHalf) {
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
    GamePanel() {
        createShuffledCards();

        ArrayList<ImageIcon> cardImages = new ArrayList<>();
            for (String cardName: sortedFirstHalf) {
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            cardImages.add(new ImageIcon(cardPath));
        }

        handCards = new JLabel[cardImages.size()];
            for (int i = 0; i < cardImages.size(); i++) {
            handCards[i] = new JLabel();
            handCards[i].setIcon(cardImages.get(i));
        }

        ArrayList<ImageIcon> computerCardImages = new ArrayList<>();
            for (String cardName: sortedSecondHalf) {
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            computerCardImages.add(new ImageIcon(cardPath));
        }

        JLabel[] computerHandCards = new JLabel[cardImages.size()];
            for (int i = 0; i< computerCardImages.size(); i++) {
            computerHandCards[i] = new JLabel();
            computerHandCards[i].setIcon(computerCardImages.get(i));
        }

        confirmButton.setText("Confirm");
        confirmButton.setFocusable(false);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 15));
        confirmButton.setBounds(1500, 585, 75, 50);
        confirmButton.setBackground(new Color(235, 243, 232));
        confirmButton.setBorder(BorderFactory.createEmptyBorder());

        passButton.setText("Pass");
        passButton.setFocusable(false);
        passButton.setFont(new Font("Arial", Font.BOLD, 15));
        passButton.setBounds(1400, 585, 75, 50);
        passButton.setBackground(new Color(235, 243, 232));
        passButton.setBorder(BorderFactory.createEmptyBorder());

        frame.setVisible(true);
        frame.setSize(GAME_WIDTH, 900);
        frame.setTitle("Big 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(88, 129, 87));

        label.setText("Big 2");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setVerticalAlignment(JLabel.TOP); // set vertical position of icon+text within label
        label.setHorizontalAlignment(JLabel.CENTER); // set horizontal position of icon+text within label
        label.setBounds(500, 15, 600, 600); // set x, y position within frame as well as dimensions

        label.setHorizontalTextPosition(JLabel.CENTER); // set text LEFT, CENTER, RIGHT of imageIcon
        label.setVerticalTextPosition(JLabel.TOP);

        cardDeck.setLayout(null);
        cardDeck.setBackground(new Color(178, 200, 186));
        cardDeck.setBounds(0, 650, GAME_WIDTH, 250);
        for (int i = 0; i < handCards.length; i++) {
            cardDeck.add(handCards[i]);
        }

        computerCardDeck.setLayout(null);
        computerCardDeck.setBackground(new Color(178, 200, 186));
        computerCardDeck.setBounds(0, 0, GAME_WIDTH, 250);
        for (int i = 0; i< computerHandCards.length; i++) {
            computerCardDeck.add(computerHandCards[i]);
        }

        int cardHandLength = sortedFirstHalfObjects.size();
        int gap = CARD_WIDTH + (GAME_WIDTH - (cardHandLength * CARD_WIDTH)) / (cardHandLength + 2);
        for (int i = 0; i < handCards.length; i++) {
            handCards[i].setBounds((i + 1) * gap - 100, 20, 102, 153);
        }

        int computerCardHandLength = sortedSecondHalfObjects.size();
        int computerCardGap = CARD_WIDTH + (GAME_WIDTH - (computerCardHandLength * CARD_WIDTH)) / (computerCardHandLength + 2);
        for (int i = 0; i < computerHandCards.length; i++) {
            computerHandCards[i].setBounds((i + 1) * computerCardGap - 100, 20, 102, 153);
        }

        cardTable.setLayout(null);
        cardTable.setBackground(new Color(178, 200, 186));
        cardTable.setBounds(0, 325, GAME_WIDTH, 250);

        MouseListener[] playerMouseListeners = new MouseListener[handCards.length];

        for (int i = 0; i < handCards.length; i++) {
            final int index = i;
            playerMouseListeners[index] = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedCards.contains(sortedFirstHalfObjects.get(index))) {
                        selectedCards.remove(sortedFirstHalfObjects.get(index));
                        handCards[index].setBounds((index + 1) * gap - 100 , 20, 102, 153);
                        System.out.println(selectedCards);
                    } else if (selectedCards.size() < 5) {
                        selectedCards.add(sortedFirstHalfObjects.get(index));
                        handCards[index].setBounds((index + 1) * gap - 100 , 0, 102, 153);
                        System.out.println(selectedCards);
                    } else {
                        System.out.println("Selected more than 5 cards");
                    }
                }
            };
            handCards[i].addMouseListener(playerMouseListeners[i]);
        }


    // Check Deck Combination
    // if round 1, diamond of 3 must be included
    // check the selected card and the current card

    // Combinations

    // Single
    // Size: check rank first, then suit

    // Pairs (length = 2, same rank)
    // Size: check rank first, then suit

    // 3 cards (length = 3, same rank)
    // Size: check rank

    // 4 cards (length = 4, same rank)
    // Size: check rank

    // Straight
    // (length = 5 => sort => rank of the previous number = rank of current number - 1)
    // if consist of 1 or 2 (10jqk1, jqk12, qk123, k1234, 12345, 23456) => the rank equation varies
    // Size: if both consist of 1 and 2, check largest 2
    // if player 1 consist 1 and 2, then player 1 larger
    // usual logic, check largest card

    // Flush
    // (length = 5 => sort => 5 cards have the same suit)
    // Size: check suit
    // if both have same suit, check largest card

    // Full house
    // length = 5 => sort => consist of 3 cards same rank and a pair of same rank
    // Size: check 3 cards and compare who has the larger rank

    // Four of a kind
    // length = 5 => sort => consist of 4 cards same rank
    // Size: check 4 cards and compare who has the larger rank

    // Straight Flush
    // length = 5 => sort => rank of the previous number = rank of current number - 1)
    // if consist of 1 or 2 (10jqk1, jqk12, qk123, k1234, 12345, 23456) => the rank equation varies
    // 5 cards have the same suit
    // Size: check suit
    // if same suit, check largest card
        confirmButton.addActionListener(e -> {
            sortSelectedCards();

            int selectedCardSize = selectedCards.size();
            String numberOfCards;
            String fiveCardCombination;

            switch (selectedCardSize) {
                case 1, 2, 3, 4:
                    switch (selectedCardSize) {
                        case 1:
                            numberOfCards = "Single card";
                            break;
                        case 2:
                            numberOfCards = "Pair";
                            break;
                        case 3:
                            numberOfCards = "Triples";
                            break;
                        case 4:
                            numberOfCards = "Four cards";
                            break;
                        default:
                            numberOfCards = "Invalid";
                    }
                    if (isSelectedCardValid(numberOfCards)) {
                        System.out.println(numberOfCards);
                        renderCardInTable();
                        TimerTask computer = new TimerTask() {
                            @Override
                            public void run() {
                                computerMove(numberOfCards);
                                reactivatePlayerMouseListener();

                            }
                        };
                        System.out.println("Computer's turn");
                        timer.schedule(computer, 3000);
                    } else {
                        System.out.println("Invalid");
                    }
                    break;
                case 5:
                    // check player combination
                    String getPlayerCombination = getFiveCardCombination(selectedCards);

                    if (isSelectedCardValid(getPlayerCombination)) {
                        System.out.println(getPlayerCombination);
                        renderCardInTable();
                        TimerTask computer = new TimerTask() {
                            @Override
                            public void run() {
                                computerMove(getPlayerCombination);
                                reactivatePlayerMouseListener();
                            }
                        };
                        System.out.println("Computer's turn");
                        timer.schedule(computer, 3000);
                        break;
                    } else {
                        System.out.println("Invalid");
                    }
                    break;
            }
        });

        passButton.addActionListener(e -> {
            confirmButton.setEnabled(false);
            TimerTask computer = new TimerTask() {
                @Override
                public void run() {
                    computerRandomMove();
                    confirmButton.setEnabled(true);
                }
            };
            System.out.println("Computer's turn");
            timer.schedule(computer, 3000);
        });

        frame.setLayout(null);
        frame.add(cardDeck);
        frame.add(computerCardDeck);
        frame.add(cardTable);
        frame.add(confirmButton);
        frame.add(passButton);
}
    public String getFiveCardCombination(ArrayList<Card> cards) {
    boolean checkStraight = checkStraight(cards);
    boolean checkFlush = checkFlush(cards);
    boolean checkFullHouse = checkFullHouse(cards);
    boolean checkFourOfAKind = checkFourOfAKind(cards);
    if (checkStraight && checkFlush) {
        return "Straight Flush";
    } else if (checkStraight) {
        return "Straight";
    } else if (checkFlush) {
        return "Flush";
    } else if (checkFullHouse) {
        return "Full House";
    } else if (checkFourOfAKind) {
        return "Four of a kind";
    } else {
        return "Invalid";
    }
}
    public void sortSelectedCards() {
        // sort selectedCard
        ArrayList<Card> sortedSelectedCards = new ArrayList<>();
        for (int i = 1; i <= ranks.length; i++) {
            for (int j = 1; j <= suits.length; j++) {
                for (Card selectedCard : selectedCards) {
                    if (selectedCard.getSuitValue() == j && selectedCard.getRankValue() == i) {
                        sortedSelectedCards.add(selectedCard);
                    }
                }
            }
        }
        selectedCards.clear();
        selectedCards.addAll(sortedSelectedCards);
    }
    public boolean checkSingleCard() {
        if (cardTableObjects.isEmpty() || computerPass) {
            computerPass = false;
            return true;
        }
        if (cardTableObjects.size() != selectedCards.size()) {
            return false;
        }
        int tableCardRank = cardTableObjects.get(0).getRankValue();
        int tableCardSuit = cardTableObjects.get(0).getSuitValue();
        int playerCardRank = selectedCards.get(0).getRankValue();
        int playerCardSuit = selectedCards.get(0).getSuitValue();
        if (playerCardRank == tableCardRank && playerCardSuit > tableCardSuit) {
            return true;
        }
        return playerCardRank > tableCardRank;
    }
    public boolean checkPair() {
        // check if the 2 cards have the same rank
        if (selectedCards.get(0).getRankValue() != selectedCards.get(1).getRankValue()) {
            return false;
        }
        if (cardTableObjects.isEmpty() || computerPass) {
            computerPass = false;
            return true;
        }
        if (cardTableObjects.size() != selectedCards.size()) {
            return false;
        }
        // compare player larger card with table larger card
        if (selectedCards.get(1).getRankValue() == cardTableObjects.get(1).getRankValue() && selectedCards.get(1).getSuitValue() == 4) {
            return true;
        }
        return selectedCards.get(1).getRankValue() > cardTableObjects.get(1).getRankValue();
    }
    public boolean checkTriples() {
        boolean sameRank = selectedCards.get(0).getRankValue() == selectedCards.get(1).getRankValue() && selectedCards.get(0).getRankValue() == selectedCards.get(2).getRankValue();
        if (!sameRank) {
            return false;
        }
        if (cardTableObjects.isEmpty() || computerPass) {
            computerPass = false;
            return true;
        }
        if (cardTableObjects.size() != selectedCards.size()) {
            return false;
        }
        return selectedCards.get(2).getRankValue() > cardTableObjects.get(2).getRankValue();
    }

    public boolean checkFourCards() {
        boolean sameRank = selectedCards.get(0).getRankValue() == selectedCards.get(1).getRankValue() && selectedCards.get(0).getRankValue() == selectedCards.get(2).getRankValue()
                && selectedCards.get(0).getRankValue() == selectedCards.get(3).getRankValue();
        if (!sameRank) {
            return false;
        }
        if (cardTableObjects.isEmpty() || computerPass) {
            computerPass = false;
            return true;
        }
        if (cardTableObjects.size() != selectedCards.size()) {
            return false;
        }
        return selectedCards.get(2).getRankValue() > cardTableObjects.get(2).getRankValue();
    }

    public static boolean checkFourOfAKind(ArrayList<Card> cards) {
        int firstCard = cards.get(0).getRankValue();
        int secondCard = cards.get(1).getRankValue();
        int thirdCard = cards.get(2).getRankValue();
        int fourthCard = cards.get(3).getRankValue();
        int fifthCard = cards.get(4).getRankValue();

        if (firstCard == secondCard && secondCard == thirdCard && thirdCard == fourthCard) {
            return true;
        }
        return secondCard == thirdCard && thirdCard == fourthCard && fourthCard == fifthCard;
    }
    public static boolean checkFullHouse(ArrayList<Card> cards) {
        int firstCard = cards.get(0).getRankValue();
        int secondCard = cards.get(1).getRankValue();
        int thirdCard = cards.get(2).getRankValue();
        int fourthCard = cards.get(3).getRankValue();
        int fifthCard = cards.get(4).getRankValue();

        if (firstCard == secondCard && secondCard == thirdCard && fourthCard == fifthCard) {
            return true;
        }
        return firstCard == secondCard && thirdCard == fourthCard && fourthCard == fifthCard;
    }

    public boolean checkStraight(ArrayList<Card> cards) {
        int firstCard = cards.get(0).getRankValue();
        int secondCard = cards.get(1).getRankValue();
        int thirdCard = cards.get(2).getRankValue();
        int fourthCard = cards.get(3).getRankValue();
        int fifthCard = cards.get(4).getRankValue();

        // special case: [A, 2, 3, 4, 5], [2, 3, 4, 5, 6]
        if (firstCard == 1 && secondCard == 2 && thirdCard == 3 && fourthCard == 12 && fifthCard == 13) {
            return true;
        }
        if (firstCard == 1 && secondCard == 2 && thirdCard == 3 && fourthCard == 4 && fifthCard == 13) {
            return true;
        }

        // cases that all -1 rank away from each other
        for (int i = cards.size() - 1; i > 0; i--) {
            if (cards.get(i).getRankValue() - cards.get(i - 1).getRankValue() != 1) {
                return false;
            }
        }
        return true;
        // [3, 4, 5, 6, 7], [4, 5, 6, 7, 8], [5, 6, 7, 8, 9], [6, 7, 8, 9, 10], [7, 8, 9, 10, J], [8, 9, 10, J, Q], [9, 10, J, Q, K], [10, J, Q, K, A], [J, Q, K, A, 2]
    }

    public boolean checkFlush(ArrayList<Card> cards) {
        String[] suitOfSelectedCards = new String[5];
        for (int i = 0; i < cards.size(); i++) {
            suitOfSelectedCards[i] = cards.get(i).getSuit();
        }
        String firstElement = suitOfSelectedCards[0];
        for (int i = 1; i < suitOfSelectedCards.length; i++) {
            if (!suitOfSelectedCards[i].equals(firstElement)) {
                return false;
            }
        }
        return true;
    }

    public boolean compareStraight(ArrayList<Card> cards) {
        // A, 2, 3, 4, 5
        boolean table12345 = cardTableObjects.get(0).getRankValue() == 1 && cardTableObjects.get(3).getRankValue() == 12 && cardTableObjects.get(4).getRankValue() == 13;
        boolean card12345 = cards.get(0).getRankValue() == 1 && cards.get(3).getRankValue() == 12 && cards.get(4).getRankValue() == 13;
        if (card12345 && table12345 && cards.get(4).getSuitValue() > cardTableObjects.get(4).getSuitValue()) {
            return true;
        }
        if (card12345 && !table12345) {
            return true;
        }
        // 2, 3, 4, 5, 6
        boolean table23456 = cardTableObjects.get(0).getRankValue() == 1 && cardTableObjects.get(1).getRankValue() == 2 && cardTableObjects.get(4).getRankValue() == 13;
        boolean card23456 = cards.get(0).getRankValue() == 1 && cards.get(1).getRankValue() == 2 && cards.get(4).getRankValue() == 13;
        if (table23456 && card23456  && cards.get(4).getSuitValue() > cardTableObjects.get(4).getSuitValue()) {
            return true;
        }
        if (card23456 && !table23456) {
            return true;
        }

        // other case
        // find the largest rank and suit in selected and table
        if (cards.get(4).getRankValue() > cardTableObjects.get(4).getRankValue()) {
            return true;
        }
        return cards.get(4).getRankValue() == cardTableObjects.get(4).getRankValue() && cards.get(4).getSuitValue() > cardTableObjects.get(4).getSuitValue();
    }

    public boolean compareFlush(ArrayList<Card> cards) {
        if (cards.get(0).getSuitValue() > cardTableObjects.get(0).getSuitValue()) {
            return true;
        }
        if (cards.get(0).getSuitValue() < cardTableObjects.get(0).getSuitValue()) {
            return false;
        }
        return cards.get(0).getSuitValue() == cardTableObjects.get(0).getSuitValue() && cards.get(4).getRankValue() > cardTableObjects.get(4).getRankValue();
    }
    public boolean isSelectedCardValid(String combination) {
            switch(combination) {
                case "Single card":
                    return checkSingleCard();
                case "Pair":
                    return checkPair();
                case "Triples":
                    return checkTriples();
                case "Four Cards":
                    return checkFourCards();
                case "Straight", "Flush", "Full House", "Four of a kind", "Straight Flush":
                    // if it is a pass or table is empty => valid
                    if (cardTableObjects.isEmpty() || computerPass) {
                        computerPass = false;
                        return true;
                    }

                    // get table combination
                    String tableCombination = getFiveCardCombination(cardTableObjects);

                    // get player and table combination value
                    int playerCombinationValue = getCombinationValue(combination);
                    int tableCombinationValue = getCombinationValue(tableCombination);

                    // Compare player and table combination
                    if (playerCombinationValue > tableCombinationValue) {
                        return true;
                    }
                    if (playerCombinationValue < tableCombinationValue) {
                        return false;
                    }
                    if (combination.equals("Straight")) {
                        return compareStraight(selectedCards);
                    } else if (combination.equals("Flush")) {
                        return compareFlush(selectedCards);
                    }
            }
        return false;
    }
    public void reactivatePlayerMouseListener() {
        MouseListener[] playerMouseListeners = new MouseListener[handCards.length];
        int cardHandLength = sortedFirstHalfObjects.size();
        int gap = CARD_WIDTH + (GAME_WIDTH - (cardHandLength * CARD_WIDTH)) / (cardHandLength + 2);

        for (int i = 0; i < handCards.length; i++) {
            final int index = i;
            playerMouseListeners[index] = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedCards.contains(sortedFirstHalfObjects.get(index))) {
                        selectedCards.remove(sortedFirstHalfObjects.get(index));
                        handCards[index].setBounds((index + 1) * gap - 100 , 20, 102, 153);
                        System.out.println(selectedCards);
                    } else if (selectedCards.size() < 5) {
                        selectedCards.add(sortedFirstHalfObjects.get(index));
                        handCards[index].setBounds((index + 1) * gap - 100 , 0, 102, 153);
                        System.out.println(selectedCards);
                    } else {
                        System.out.println("Selected more than 5 cards");
                    }
                }
            };
            handCards[i].addMouseListener(playerMouseListeners[i]);
        }
    }

    public void computerRandomMove() {
        // check all computer possible move and randomly choose one move
        ArrayList<ArrayList<Card>> possibleMoves = new ArrayList<>();

        // add minimum single card move
        ArrayList<Card> singleCard = new ArrayList<>();
        singleCard.add(sortedSecondHalfObjects.get(0));
        possibleMoves.add(singleCard);

        // add minimum pair card move
        ArrayList<Card> pair = new ArrayList<>();
        for (int i = 0; i < sortedSecondHalfObjects.size() - 1; i++) {
            Card card1 = sortedSecondHalfObjects.get(i);
            Card card2 = sortedSecondHalfObjects.get(i + 1);
            if (card1.getRankValue() == card2.getRankValue()) {
                pair.add(card1);
                pair.add(card2);
                possibleMoves.add(pair);
                break;
            }
        }

        // add minimum triples card move
        ArrayList<Card> triple = new ArrayList<>();
        for (int i = 0; i < sortedSecondHalfObjects.size() - 2; i++) {
            Card card1 = sortedSecondHalfObjects.get(i);
            Card card2 = sortedSecondHalfObjects.get(i + 1);
            Card card3 = sortedSecondHalfObjects.get(i + 2);
            if (card1.getRankValue() == card2.getRankValue() && card1.getRankValue() == card3.getRankValue()) {
                triple.add(card1);
                triple.add(card2);
                triple.add(card3);
                possibleMoves.add(triple);
                break;
            }
        }

        // add minimum four cards move
        ArrayList<Card> fourCards = new ArrayList<>();
        for (int i = 0; i < sortedSecondHalfObjects.size() - 3; i++) {
            Card card1 = sortedSecondHalfObjects.get(i);
            Card card2 = sortedSecondHalfObjects.get(i + 1);
            Card card3 = sortedSecondHalfObjects.get(i + 2);
            Card card4 = sortedSecondHalfObjects.get(i + 3);
            if (card1.getRankValue() == card2.getRankValue() && card1.getRankValue() == card3.getRankValue()
            && card1.getRankValue() == card4.getRankValue()) {
                fourCards.add(card1);
                fourCards.add(card2);
                fourCards.add(card3);
                fourCards.add(card4);
                possibleMoves.add(fourCards);
                break;
            }
        }

        // add minimum straight move
        ArrayList<Card> straight = new ArrayList<>();
        for (int i = 0; i < sortedSecondHalfObjects.size() - 4; i++) {
            if (!straight.isEmpty()) {
                break;
            }
            Card startCard = sortedSecondHalfObjects.get(i);
            ArrayList<Card> tempStraight = new ArrayList<>();
            tempStraight.add(startCard);

            for (int j = i + 1; j < sortedSecondHalfObjects.size(); j++) {
                Card nextCard = sortedSecondHalfObjects.get(j);
                if (nextCard.getRankValue() == tempStraight.get(tempStraight.size() - 1).getRankValue() + 1) {
                    tempStraight.add(nextCard);
                }
                if (tempStraight.size() == 5) {
                    straight.addAll(tempStraight);
                    possibleMoves.add(straight);
                    break;
                }
            }
        }

        ArrayList<Integer> sortedSecondHalfRanks = new ArrayList<>();
        for (Card card : sortedSecondHalfObjects) {
            sortedSecondHalfRanks.add(card.getRankValue());
        }
        if (straight.isEmpty()) {
            // 2, 3, 4, 5, 6
            if (sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1) && sortedSecondHalfRanks.contains(2)
                    && sortedSecondHalfRanks.contains(3) && sortedSecondHalfRanks.contains(4)) {
                int[] cardRanks = {1, 2, 3, 4, 13};
                ArrayList<Card> tempStraight = new ArrayList<>();
                for (int cardRank : cardRanks) {
                    for (Card card : sortedSecondHalfObjects) {
                        if (card.getRankValue() == cardRank) {
                            tempStraight.add(card);
                            break;
                        }
                    }
                }
                straight.addAll(tempStraight);
                possibleMoves.add(straight);
            }
        }
        if (straight.isEmpty()) {
            // 1, 2, 3, 4, 5
            if (sortedSecondHalfRanks.contains(12) && sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1)
                    && sortedSecondHalfRanks.contains(2) && sortedSecondHalfRanks.contains(3)) {
                int[] cardRanks = {1, 2, 3, 12, 13};
                ArrayList<Card> tempStraight = new ArrayList<>();
                for (int cardRank : cardRanks) {
                    for (Card card : sortedSecondHalfObjects) {
                        if (card.getRankValue() == cardRank) {
                            tempStraight.add(card);
                            break;
                        }
                    }
                }
                straight.addAll(tempStraight);
                possibleMoves.add(straight);
            }
        }
        ArrayList<Card> flush = new ArrayList<>(); // check if computer has flush
        // Usual cases
        for (int k = 1; k <= 4; k++) {
            for (int i = 0; i <= sortedSecondHalfObjects.size() - 4; i++) {
                ArrayList<Card> tempFlush = new ArrayList<>();
                for (int j = i; j < sortedSecondHalfObjects.size(); j++) {
                    Card currentCard = sortedSecondHalfObjects.get(j);
                    if (currentCard.getSuitValue() == k) {
                        tempFlush.add(currentCard);
                    }
                    if (tempFlush.size() == 5) {
                        flush.addAll(tempFlush);
                        possibleMoves.add(flush);
                        break;
                    }
                }
                if (tempFlush.size() == 5) {
                    break;
                }
            }
            if (!flush.isEmpty()) {
                break;
            }
        }

        // random order of arraylist and selected the first one for computer move
        if (!flush.isEmpty()) {
            computerSelectedCards.addAll(flush);
            renderComputerCardInTable(computerSelectedCards);
        } else {
            Collections.shuffle(possibleMoves);
            ArrayList<Card> getRandomMove = possibleMoves.get(0);
            computerSelectedCards.addAll(getRandomMove);
            renderComputerCardInTable(computerSelectedCards);
        }
    }
    public void computerSingleCard() {
        computerPass = true;
        // check if there is a single card larger than the card on the table
        for (Card card: sortedSecondHalfObjects) {
            // same rank case
            Card cardOnTable = cardTableObjects.get(0);
            if (card.getRankValue() == cardOnTable.getRankValue() && card.getSuitValue() > cardOnTable.getSuitValue()) {
                computerSelectedCards.add(card);
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            } else if (card.getRankValue() > cardOnTable.getRankValue()) {
                computerSelectedCards.add(card);
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
        }
    }
    public void computerPair() {
        computerPass = true;
        // check if computer has pair
        ArrayList<ArrayList<Card>> pairs = new ArrayList<>();
        for (int i = 0; i < sortedSecondHalfObjects.size() - 1; i++) {
            Card card1 = sortedSecondHalfObjects.get(i);
            Card card2 = sortedSecondHalfObjects.get(i + 1);
            if (card1.getRankValue() == card2.getRankValue()) {
                ArrayList<Card> pair = new ArrayList<>();
                pair.add(card1);
                pair.add(card2);
                pairs.add(pair);
            }
        }
        // check if the pair are larger than the pair on the table
        for (ArrayList<Card> pair: pairs) {
            // if pair has same rank
            if (pair.get(1).getRankValue() == cardTableObjects.get(0).getRankValue() && pair.get(1).getSuitValue() == 4) {
                computerSelectedCards.add(pair.get(0));
                computerSelectedCards.add(pair.get(1));
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            } else if (pair.get(1).getRankValue() > cardTableObjects.get(0).getRankValue()) {
                computerSelectedCards.add(pair.get(0));
                computerSelectedCards.add(pair.get(1));
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
        }
    }
    public void computerTriples() {
        computerPass = true;
        // check if computer has a Triple
        ArrayList<ArrayList<Card>> triples = new ArrayList<>();
        for (int i = 0; i < sortedSecondHalfObjects.size() - 2; i++) {
            Card card1 = sortedSecondHalfObjects.get(i);
            Card card2 = sortedSecondHalfObjects.get(i + 1);
            Card card3 = sortedSecondHalfObjects.get(i + 2);
            if (card1.getRankValue() == card2.getRankValue() && card1.getRankValue() == card3.getRankValue()) {
                ArrayList<Card> triple = new ArrayList<>();
                triple.add(card1);
                triple.add(card2);
                triple.add(card3);
                triples.add(triple);
            }
        }
        // check if the triple are larger than the triple on the table
        for (ArrayList<Card> triple: triples) {
            if (triple.get(0).getRankValue() > cardTableObjects.get(0).getRankValue()) {
                computerSelectedCards.add(triple.get(0));
                computerSelectedCards.add(triple.get(1));
                computerSelectedCards.add(triple.get(2));
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
        }
    }
    public void computerFourCards() {
        computerPass = true;
        // check if computer has a four
        ArrayList<ArrayList<Card>> fours = new ArrayList<>();
        for (int i = 0; i < sortedSecondHalfObjects.size() - 3; i++) {
            Card card1 = sortedSecondHalfObjects.get(i);
            Card card2 = sortedSecondHalfObjects.get(i + 1);
            Card card3 = sortedSecondHalfObjects.get(i + 2);
            Card card4 = sortedSecondHalfObjects.get(i + 3);
            if (card1.getRankValue() == card2.getRankValue() && card1.getRankValue() == card3.getRankValue() && card1.getRankValue() == card4.getRankValue()) {
                ArrayList<Card> four = new ArrayList<>();
                four.add(card1);
                four.add(card2);
                four.add(card3);
                four.add(card4);
                fours.add(four);
            }
        }
        // check if the fours are larger than the triple on the table
        for (ArrayList<Card> four: fours) {
            if (four.get(0).getRankValue() > cardTableObjects.get(0).getRankValue()) {
                computerSelectedCards.add(four.get(0));
                computerSelectedCards.add(four.get(1));
                computerSelectedCards.add(four.get(2));
                computerSelectedCards.add(four.get(3));
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
        }
    }
    public void computerStraight() {
        computerPass = true;
        ArrayList<ArrayList<Card>> straights = new ArrayList<>(); // check if computer has straight

        // Usual cases
        for (int i = 0; i < sortedSecondHalfObjects.size() - 4; i++) {
            Card startCard = sortedSecondHalfObjects.get(i);
            ArrayList<Card> tempStraight = new ArrayList<>();
            tempStraight.add(startCard);

            for (int j = i + 1; j < sortedSecondHalfObjects.size(); j++) {
                Card nextCard = sortedSecondHalfObjects.get(j);
                if (nextCard.getRankValue() == tempStraight.get(tempStraight.size() - 1).getRankValue() + 1) {
                    tempStraight.add(nextCard);
                }
                if (tempStraight.size() == 5) {
                    straights.add(tempStraight);
                    break;
                }
            }
        }

        // special case: 23456 and A2345
        ArrayList<Integer> sortedSecondHalfRanks = new ArrayList<>();
        for (Card card: sortedSecondHalfObjects) {
            sortedSecondHalfRanks.add(card.getRankValue());
        }

        // 2, 3, 4, 5, 6
        if (sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1) && sortedSecondHalfRanks.contains(2)
                && sortedSecondHalfRanks.contains(3) && sortedSecondHalfRanks.contains(4)) {
            int[] cardRanks = {1, 2, 3, 4, 13};
            ArrayList<Card> tempStraight = new ArrayList<>();
            for (int cardRank : cardRanks) {
                for (Card card : sortedSecondHalfObjects) {
                    if (card.getRankValue() == cardRank) {
                        tempStraight.add(card);
                        break;
                    }
                }
            }
            straights.add(tempStraight);
        }

        // 1, 2, 3, 4, 5
        if (sortedSecondHalfRanks.contains(12) && sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1)
                && sortedSecondHalfRanks.contains(2) && sortedSecondHalfRanks.contains(3)) {
            int[] cardRanks = {1, 2, 3, 12, 13};
            ArrayList<Card> tempStraight = new ArrayList<>();
            for (int cardRank : cardRanks) {
                for (Card card : sortedSecondHalfObjects) {
                    if (card.getRankValue() == cardRank) {
                        tempStraight.add(card);
                        break;
                    }
                }
            }
            straights.add(tempStraight);
        }

        // Check if the straight is larger than the straight on table
        boolean table12345 = cardTableObjects.get(0).getRankValue() == 1 && cardTableObjects.get(3).getRankValue() == 12 && cardTableObjects.get(4).getRankValue() == 13;
        boolean table23456 = cardTableObjects.get(0).getRankValue() == 1 && cardTableObjects.get(1).getRankValue() == 2 && cardTableObjects.get(4).getRankValue() == 13;

        // find for smaller straight possibilities first
        for (ArrayList<Card> computerStraight : straights) {
            if (table12345 || table23456) {
                break;
            }
            Card computerCard5 = computerStraight.get(4); // the last card is always the largest card
            Card tableCard5 = cardTableObjects.get(4);
            if (computerCard5.getRankValue() == tableCard5.getRankValue() && computerCard5.getSuitValue() > tableCard5.getSuitValue()) {
                for (int i = 0; i < 5; i++) {
                    computerSelectedCards.add(computerStraight.get(i));
                }
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
            if (computerCard5.getRankValue() > tableCard5.getRankValue()) {
                for (int i = 0; i < 5; i++) {
                    computerSelectedCards.add(computerStraight.get(i));
                }
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
        }

        // 23456
        for (ArrayList<Card> computerStraight: straights) {
            if (table12345 || !computerPass) {
                break;
            }
            if (computerStraight.get(0).getRankValue() == 1 && computerStraight.get(1).getRankValue() == 2 && computerStraight.get(2).getRankValue() == 3
                    && computerStraight.get(3).getRankValue() == 4 && computerStraight.get(4).getRankValue() == 13 ) {
                // check if table also has 2, 3, 4, 5, 6
                if (table23456) {
                    // check who has the largest suit
                    for (Card card: cardTableObjects) {
                        if (computerStraight.get(4).getRankValue() == card.getRankValue() && computerStraight.get(4).getSuitValue() > card.getSuitValue()) {
                            for (int i = 0; i < 5; i++) {
                                computerSelectedCards.add(computerStraight.get(i));
                            }
                            renderComputerCardInTable(computerSelectedCards);
                            computerPass = false;
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        computerSelectedCards.add(computerStraight.get(i));
                    }
                    renderComputerCardInTable(computerSelectedCards);
                    computerPass = false;
                    break;
                }
            }
        }

        // A, 2, 3, 4, 5
        for (ArrayList<Card> computerStraight: straights) {
            if (!computerPass) {
                break;
            }
            if (computerStraight.get(0).getRankValue() == 1 && computerStraight.get(1).getRankValue() == 2 &&
                    computerStraight.get(2).getRankValue() == 3 && computerStraight.get(3).getRankValue() == 12 && computerStraight.get(4).getRankValue() == 13) {
                // check if table also has A, 2, 3, 4, 5
                if (table12345) {
                    // check who has the largest suit
                    if (computerStraight.get(4).getRankValue() == cardTableObjects.get(4).getRankValue() && computerStraight.get(4).getSuitValue() > cardTableObjects.get(4).getSuitValue()) {
                        for (int i = 0; i < 5; i++) {
                            computerSelectedCards.add(computerStraight.get(i));
                        }
                        renderComputerCardInTable(computerSelectedCards);
                        computerPass = false;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < 5; i++) {
                    computerSelectedCards.add(computerStraight.get(i));
                }
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
        }
        // if largest number in straight is larger than straight on table
        // [3, 4, 5, 6, 7], [4, 5, 6, 7, 8], [5, 6, 7, 8, 9], [6, 7, 8, 9, 10], [7, 8, 9, 10, J], [8, 9, 10, J, Q], [9, 10, J, Q, K], [10, J, Q, K, A], [J, Q, K, A, 2]
    }
    public void computerFlush() {
        computerPass = true;
        ArrayList<ArrayList<Card>> flushes = new ArrayList<>(); // check if computer has flush
        // Usual cases
        for (int k = 1; k <= 4; k++) {
            for (int i = 0; i <= sortedSecondHalfObjects.size() - 4; i++) {
                ArrayList<Card> tempFlush = new ArrayList<>();
                for (int j = i; j < sortedSecondHalfObjects.size(); j++) {
                    Card currentCard = sortedSecondHalfObjects.get(j);
                    if (currentCard.getSuitValue() == k) {
                        tempFlush.add(currentCard);
                    }
                    if (tempFlush.size() == 5) {
                        flushes.add(tempFlush);
                        break;
                    }
                }
            }
        }

        if (checkStraight(cardTableObjects)) {
            computerSelectedCards.addAll(flushes.get(0));
            renderComputerCardInTable(computerSelectedCards);
            computerPass = false;
        }

        // find for smaller straight possibilities first
        for (ArrayList<Card> flush: flushes) {
            if (!computerPass) {
                break;
            }
            if (flush.get(0).getSuitValue() == cardTableObjects.get(0).getSuitValue() && flush.get(4).getRankValue() > cardTableObjects.get(4).getRankValue()) {
                for (int i = 0; i < 5; i++) {
                    computerSelectedCards.add(flush.get(i));
                }
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
            if (flush.get(0).getSuitValue() > cardTableObjects.get(0).getSuitValue()) {
                for (int i = 0; i < 5; i++) {
                    computerSelectedCards.add(flush.get(i));
                }
                renderComputerCardInTable(computerSelectedCards);
                computerPass = false;
                break;
            }
        }
    }



    public void computerMove(String combination) {
        switch (combination) {
            case "Single card":
                computerSingleCard();
                break;
            case "Pair":
                computerPair();
                break;
            case "Triples":
                computerTriples();
                break;
            case "Four cards":
                computerFourCards();
                break;
            case "Straight":
                computerStraight();
            case "Flush":
                computerFlush();
        }
        if (computerPass) {
            System.out.println("Pass");
        }
    }

    public void renderComputerCardInTable(ArrayList<Card> computerSelectedCards) {
        cardTable.removeAll();
        cardTable.revalidate();
        cardTable.repaint();
        cardTableImages.clear();

        for (int i = 0; i < computerSelectedCards.size(); i++) {
            String cardName = computerSelectedCards.get(i).name();
            System.out.println(computerSelectedCards.get(i).name());
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            cardTableImages.add(new ImageIcon(cardPath));
        }
        JLabel[] cardTableCards = new JLabel[cardTableImages.size()];
        for (int i = 0; i < cardTableImages.size(); i++) {
            cardTableCards[i] = new JLabel();
            cardTableCards[i].setIcon(cardTableImages.get(i));
            cardTable.add(cardTableCards[i]);
        }
        cardTableObjects.clear();
        cardTableObjects.addAll(computerSelectedCards);

        computerCardDeck.removeAll();
        computerCardDeck.revalidate();
        computerCardDeck.repaint();

        for (Card card: computerSelectedCards) {
            sortedSecondHalfObjects.remove(card);
            sortedSecondHalf.remove(card.name());
        }

        computerSelectedCards.clear();

        ArrayList<ImageIcon> cardImages = new ArrayList<>();
        for (String cardName: sortedSecondHalf) {
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            cardImages.add(new ImageIcon(cardPath));
        }

        JLabel[] handCards = new JLabel[cardImages.size()];
        for (int i = 0; i < cardImages.size(); i++) {
            handCards[i] = new JLabel();
            handCards[i].setIcon(cardImages.get(i));
        }

        for (JLabel handCard : handCards) {
            computerCardDeck.add(handCard);
        }

        int cardHandLength = sortedSecondHalfObjects.size();
        int gap = CARD_WIDTH + (GAME_WIDTH - (cardHandLength * CARD_WIDTH)) / (cardHandLength + 2);
        for (int i = 0; i < handCards.length; i++) {
            handCards[i].setBounds((i + 1) * gap - 100, 20, 102, 153);
        }

        int tableHandLength = cardTableObjects.size();
        int tableCardGap = CARD_WIDTH + (GAME_WIDTH - (tableHandLength * CARD_WIDTH)) / (tableHandLength + 2);
        for (int i = 0; i < cardTableCards.length; i++) {
            cardTableCards[i].setBounds((i + 1) * tableCardGap - 100, 20, 102, 153);
        }
    }

    public void renderCardInTable () {
        cardTable.removeAll();
        cardTable.revalidate();
        cardTable.repaint();
        cardTableImages.clear();
        // add image first
        for (Card selectedCard : selectedCards) {
            String cardName = selectedCard.name();
            System.out.println(selectedCard.name());
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            cardTableImages.add(new ImageIcon(cardPath));
        }
        JLabel[] cardTableCards = new JLabel[cardTableImages.size()];
        for (int i = 0; i < cardTableImages.size(); i++) {
            cardTableCards[i] = new JLabel();
            cardTableCards[i].setIcon(cardTableImages.get(i));
            cardTable.add(cardTableCards[i]);
        }
        cardTableObjects.clear();
        cardTableObjects.addAll(selectedCards);

        cardDeck.removeAll();
        cardDeck.revalidate();
        cardDeck.repaint();

        for (Card card: selectedCards) {
            sortedFirstHalfObjects.remove(card);
            sortedFirstHalf.remove(card.name());
        }

        selectedCards.clear();

        ArrayList<ImageIcon> cardImages = new ArrayList<>();
        for (String cardName: sortedFirstHalf) {
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            cardImages.add(new ImageIcon(cardPath));
        }

        handCards = new JLabel[cardImages.size()];
        for (int i = 0; i < cardImages.size(); i++) {
            handCards[i] = new JLabel();
            handCards[i].setIcon(cardImages.get(i));
        }

        for (JLabel handCard : handCards) {
            cardDeck.add(handCard);
        }

        int cardHandLength = sortedFirstHalfObjects.size();
        int gap = CARD_WIDTH + (GAME_WIDTH - (cardHandLength * CARD_WIDTH)) / (cardHandLength + 2);
        for (int i = 0; i < handCards.length; i++) {
            handCards[i].setBounds((i + 1) * gap - 100, 20, 102, 153);
        }

        int tableHandLength = cardTableObjects.size();
        int tableCardGap = CARD_WIDTH + (GAME_WIDTH - (tableHandLength * CARD_WIDTH)) / (tableHandLength + 2);
        for (int i = 0; i < cardTableCards.length; i++) {
            cardTableCards[i].setBounds((i + 1) * tableCardGap - 100, 20, 102, 153);
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
    public int getCombinationValue(String combination) {
        return switch (combination) {
            case "Straight" -> 1;
            case "Flush" -> 2;
            case "Full House" -> 3;
            case "Four of a kind" -> 4;
            case "Straight Flush" -> 5;
            default -> 0;
        };
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel {
    boolean computerPass = false;
    final int CARD_WIDTH = 102;
    final int GAME_WIDTH = 1600;
    JLabel message = new JLabel();
    JFrame frame = new JFrame();
    JLabel[] handCards;
    JPanel backgroundPanel = new JPanel();
    JPanel cardDeck = new JPanel();
    JPanel cardTable = new JPanel();
    JPanel computerCardDeck = new JPanel();
    JButton confirmButton = new JButton();
    JButton passButton = new JButton();
    JButton reactivateButton = new JButton();
    ArrayList<Card> selectedCards = new ArrayList<>();
    ArrayList<Card> computerSelectedCards = new ArrayList<>();
    ArrayList<Card> cardTableObjects = new ArrayList<>();
    ArrayList<ImageIcon> cardTableImages = new ArrayList<>();
    JMenuBar menuBar = new JMenuBar();
    JMenu optionMenu = new JMenu("Option");
    JMenuItem playAgainItem = new JMenuItem("Play Again (P)");
    JMenuItem exitItem = new JMenuItem("Exit (E)");

    // Computer 1
    JPanel computer1 = new JPanel();
    ImageIcon cardBack = new ImageIcon("src/small-cards/card-back.png");
    JLabel computer1CardBack = new JLabel();
    JLabel computer1Title = new JLabel();
    JLabel computer1Cards = new JLabel();

    // Computer 2
    JPanel computer2 = new JPanel();
    JLabel computer2CardBack = new JLabel();
    JLabel computer2Title = new JLabel();
    JLabel computer2Cards = new JLabel();

    int gap;
    private final GameLogic gameLogic;
    private final ComputerLogic computerLogic;


    GamePanel() {
        computerLogic = new ComputerLogic(this);
        gameLogic = new GameLogic(this, computerLogic);
        computerLogic.setGameLogic(gameLogic);


        gameLogic.createShuffledCards();
        initializeButtons();
        initializeFrame();
        initializeMessage();

        ArrayList<ImageIcon> cardImages = new ArrayList<>();
            for (String cardName: gameLogic.sortedFirstHalf) {
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            cardImages.add(new ImageIcon(cardPath));
        }

        handCards = new JLabel[cardImages.size()];
            for (int i = 0; i < cardImages.size(); i++) {
            handCards[i] = new JLabel();
            handCards[i].setIcon(cardImages.get(i));
        }

        ArrayList<ImageIcon> computerCardImages = new ArrayList<>();
            for (String cardName: gameLogic.sortedSecondHalf) {
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            computerCardImages.add(new ImageIcon(cardPath));
        }

        JLabel[] computerHandCards = new JLabel[cardImages.size()];
            for (int i = 0; i< computerCardImages.size(); i++) {
            computerHandCards[i] = new JLabel();
            computerHandCards[i].setIcon(computerCardImages.get(i));
        }

        JLabel backgroundLabel = new JLabel(new ImageIcon("src/background/poker-background.jpg"));
        backgroundLabel.setBounds(0, 0, GAME_WIDTH, 900);

        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, GAME_WIDTH, 900);
        backgroundPanel.add(backgroundLabel);

        cardDeck.setLayout(null);
        cardDeck.setOpaque(false);
        cardDeck.setBounds(0, 650, GAME_WIDTH, 250);
        for (JLabel handCard : handCards) {
            cardDeck.add(handCard);
        }

        computerCardDeck.setLayout(null);
        computerCardDeck.setOpaque(false);
        computerCardDeck.setBounds(0, 0, GAME_WIDTH, 250);
        for (JLabel computerHandCard : computerHandCards) {
            computerCardDeck.add(computerHandCard);
        }

        computer1CardBack.setIcon(cardBack);
        computer1.setLayout(null);
        computer1.setOpaque(false);
        computer1.setBounds(150, 50, 300, 300);
        computer1.add(computer1CardBack);
        computer1.add(computer1Title);
        computer1.add(computer1Cards);
        computer1CardBack.setBounds(12, 0, 107, 150);
        computer1Title.setText("Computer 1");
        computer1Title.setFont(new Font("Agency FB", Font.BOLD, 30));
        computer1Title.setForeground(Color.WHITE);
        computer1Title.setBounds(10, 160, 150, 40);
        computer1Cards.setText(gameLogic.sortedFirstHalfObjects.size() + "  cards");
        computer1Cards.setFont(new Font("Agency FB", Font.BOLD, 30));
        computer1Cards.setForeground(Color.WHITE);
        computer1Cards.setBounds(10, 200, 150, 40);

        computer2CardBack.setIcon(cardBack);
        computer2.setLayout(null);
        computer2.setOpaque(false);
        computer2.setBounds(450, 50, 300, 300);
        computer2.add(computer2CardBack);
        computer2.add(computer2Title);
        computer2.add(computer2Cards);
        computer2CardBack.setBounds(12, 0, 107, 150);
        computer2Title.setText("Computer 2");
        computer2Title.setFont(new Font("Agency FB", Font.BOLD, 30));
        computer2Title.setForeground(Color.WHITE);
        computer2Title.setBounds(10, 160, 150, 40);
        computer2Cards.setText(gameLogic.sortedThirdHalfObjects.size() + "  cards");
        computer2Cards.setFont(new Font("Agency FB", Font.BOLD, 30));
        computer2Cards.setForeground(Color.WHITE);
        computer2Cards.setBounds(10, 200, 150, 40);

        int cardHandLength = gameLogic.sortedFirstHalfObjects.size();
        gap = CARD_WIDTH + (GAME_WIDTH - (cardHandLength * CARD_WIDTH)) / (cardHandLength + 2);
        for (int i = 0; i < handCards.length; i++) {
            handCards[i].setBounds((i + 1) * gap - 100, 20, 102, 153);
        }

        int computerCardHandLength = gameLogic.sortedSecondHalfObjects.size();
        int computerCardGap = CARD_WIDTH + (GAME_WIDTH - (computerCardHandLength * CARD_WIDTH)) / (computerCardHandLength + 2);
        for (int i = 0; i < computerHandCards.length; i++) {
            computerHandCards[i].setBounds((i + 1) * computerCardGap - 100, 20, 102, 153);
        }

        cardTable.setLayout(null);
        cardTable.setOpaque(false);
        cardTable.setBounds(0, 325, GAME_WIDTH, 250);

        gameLogic.initializePlayerMouseListener();

        optionMenu.setMnemonic(KeyEvent.VK_O);
        playAgainItem.setMnemonic(KeyEvent.VK_P);
        exitItem.setMnemonic(KeyEvent.VK_E);

        optionMenu.add(playAgainItem);
        optionMenu.add(exitItem);
        menuBar.add(optionMenu);
        frame.setJMenuBar(menuBar);
        frame.setLayout(null);
        frame.add(cardDeck);
        frame.add(computer1);
        //frame.add(computerCardDeck);
        frame.add(computer2);
        frame.add(cardTable);
        frame.add(confirmButton);
        frame.add(passButton);
        frame.add(reactivateButton);
        frame.add(message);
        frame.add(backgroundPanel);

}
    private void initializeMessage() {
        message.setFont(new Font("Agency FB", Font.BOLD, 30));
        message.setText("Your turn");
        message.setLayout(null);
        message.setBounds(725 , 310, 600, 600);
        message.setForeground(Color.WHITE);
    }
    private void initializeButtons() {
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

        reactivateButton.setText("Reactivate");
        reactivateButton.setFocusable(false);
        reactivateButton.setFont(new Font("Arial", Font.BOLD, 15));
        reactivateButton.setBounds(1275, 585, 100, 50);
        reactivateButton.setBackground(new Color(235, 243, 232));
        reactivateButton.setBorder(BorderFactory.createEmptyBorder());
    }

    private void initializeFrame() {
        frame.setVisible(true);
        frame.setSize(GAME_WIDTH, 900);
        frame.setTitle("Big 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(88, 129, 87));
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
    public boolean checkSingleCard() {
        if (cardTableObjects.isEmpty() || gameLogic.numberOfPass == 2) {
            gameLogic.numberOfPass = 0;
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
        if (cardTableObjects.isEmpty() || gameLogic.numberOfPass == 2) {
            gameLogic.numberOfPass = 0;
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
        if (cardTableObjects.isEmpty() || gameLogic.numberOfPass == 2) {
            gameLogic.numberOfPass = 0;
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
        if (cardTableObjects.isEmpty() || gameLogic.numberOfPass == 2) {
            gameLogic.numberOfPass = 0;
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
        // [3, 4, 5, 6, 7], [4, 5, 6, 7, 8], [5, 6, 7, 8, 9], [6, 7, 8, 9, 10], [7, 8, 9, 10, J], [8, 9, 10, J, Q], [9, 10, J, Q, K], [10, J, Q, K, A]
        for (int i = cards.size() - 1; i > 0; i--) {
            if (cards.get(i).getRankValue() - cards.get(i - 1).getRankValue() != 1) {
                return false;
            }
        }

        // exclude [J, Q, K, A, 2]
        return fifthCard != 13;
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
    public boolean compareFullHouse(ArrayList<Card> cards) {
        // find the rank with 3 cards in table and selected

        // since it's sorted, let's compare index 0 and 2 33444 or 33344
        int selectedTriple;
        if (cards.get(0).getRankValue() == cards.get(2).getRankValue()) {
            selectedTriple = cards.get(0).getRankValue();
        } else {
            selectedTriple = cards.get(4).getRankValue();
        }

        int tableTriple;
        if (cardTableObjects.get(0).getRankValue() == cardTableObjects.get(2).getRankValue()) {
            tableTriple = cardTableObjects.get(0).getRankValue();
        } else {
            tableTriple = cardTableObjects.get(4).getRankValue();
        }

        return selectedTriple > tableTriple;
    }
    public boolean compareFourOfAKind(ArrayList<Card> cards) {
        int selectedFour;
        if (cards.get(0).getRankValue() == cards.get(3).getRankValue()) {
            selectedFour = cards.get(0).getRankValue();
        } else {
            selectedFour = cards.get(4).getRankValue();
        }

        int tableFour;
        if (cardTableObjects.get(0).getRankValue() == cardTableObjects.get(3).getRankValue()) {
            tableFour = cardTableObjects.get(0).getRankValue();
        } else {
            tableFour = cardTableObjects.get(4).getRankValue();
        }

        return selectedFour > tableFour;
    }
    public boolean compareStraightFlush(ArrayList<Card> cards) {
        int selectedCardSuit = selectedCards.get(0).getSuitValue();
        int tableSuit = cardTableObjects.get(0).getSuitValue();

        if (selectedCardSuit > tableSuit) {
            return true;
        }
        if (selectedCardSuit < tableSuit) {
            return false;
        }
        int tableLast2Sum = cardTableObjects.get(3).getRankValue() + cardTableObjects.get(4).getRankValue();
        // handle exclusive case => 34562
        if (tableLast2Sum == 17 && cardTableObjects.get(4).getRankValue() == 13) {
            tableLast2Sum = 24;
        }
        int selectedLast2Sum = selectedCards.get(3).getRankValue() + selectedCards.get(4).getRankValue();
        if (selectedLast2Sum == 17 && selectedCards.get(4).getRankValue() == 13) {
            selectedLast2Sum = 24;
        }
        return selectedLast2Sum > tableLast2Sum;
    }
    public boolean isSelectedCardValid(String combination) {
        switch (combination) {
            case "Single card" -> {
                return checkSingleCard();
            }
            case "Pair" -> {
                return checkPair();
            }
            case "Triples" -> {
                return checkTriples();
            }
            case "Four cards" -> {
                return checkFourCards();
            }
            case "Straight", "Flush", "Full House", "Four of a kind", "Straight Flush" -> {
                // if it is a pass or table is empty => valid
                if (cardTableObjects.isEmpty() || gameLogic.numberOfPass == 2) {
                    gameLogic.numberOfPass = 0;
                    computerPass = false;
                    return true;
                }

                // get table combination
                String tableCombination = getFiveCardCombination(cardTableObjects);

                // get player and table combination value
                int playerCombinationValue = getCombinationValue(combination);
                int tableCombinationValue = getCombinationValue(tableCombination);

                // Compare player and table combination (big or smaller case)
                if (playerCombinationValue > tableCombinationValue) {
                    return true;
                }
                if (playerCombinationValue < tableCombinationValue) {
                    return false;
                }
                // same case, need to compare
                if (combination.equals("Straight")) {
                    return compareStraight(selectedCards);
                } else if (combination.equals("Flush")) {
                    return compareFlush(selectedCards);
                } else if (combination.equals("Full House")) {
                    return compareFullHouse(selectedCards);
                } else if (combination.equals("Four of a kind")) {
                    return compareFourOfAKind(selectedCards);
                } else {
                    return compareStraightFlush(selectedCards);
                }
            }
        }
        return false;
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
            gameLogic.sortedFirstHalfObjects.remove(card);
            gameLogic.sortedFirstHalf.remove(card.name());
        }

        selectedCards.clear();

        ArrayList<ImageIcon> cardImages = new ArrayList<>();
        for (String cardName: gameLogic.sortedFirstHalf) {
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

        int cardHandLength = gameLogic.sortedFirstHalfObjects.size();
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
    public void resetGame() {
        message.setText("Your Turn");
        passButton.setEnabled(true);
        confirmButton.setEnabled(true);
        reactivateButton.setEnabled(true);

        cardDeck.removeAll();
        cardDeck.revalidate();
        cardDeck.repaint();
        gameLogic.sortedFirstHalf.clear();
        gameLogic.sortedFirstHalfObjects.clear();

        cardTable.removeAll();
        cardTable.revalidate();
        cardTable.repaint();
        cardTableImages.clear();
        cardTableObjects.clear();

        gameLogic.sortedSecondHalf.clear();
        gameLogic.sortedSecondHalfObjects.clear();
        gameLogic.sortedThirdHalf.clear();
        gameLogic.sortedThirdHalfObjects.clear();
        computerCardDeck.removeAll();
        computerCardDeck.revalidate();
        computerCardDeck.repaint();

        gameLogic.createShuffledCards();

        computer1Cards.setText(gameLogic.sortedSecondHalfObjects.size() + " cards");
        computer2Cards.setText(gameLogic.sortedThirdHalfObjects.size() + " cards");

        // Player card reset
        ArrayList<ImageIcon> cardImages = new ArrayList<>();
        for (String cardName: gameLogic.sortedFirstHalf) {
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

        int cardHandLength = gameLogic.sortedFirstHalfObjects.size();
        int gap = CARD_WIDTH + (GAME_WIDTH - (cardHandLength * CARD_WIDTH)) / (cardHandLength + 2);
        for (int i = 0; i < handCards.length; i++) {
            handCards[i].setBounds((i + 1) * gap - 100, 20, 102, 153);
        }

        MouseListener[] playerMouseListeners = new MouseListener[handCards.length];

        for (int i = 0; i < handCards.length; i++) {
            final int index = i;
            playerMouseListeners[index] = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedCards.contains(gameLogic.sortedFirstHalfObjects.get(index))) {
                        selectedCards.remove(gameLogic.sortedFirstHalfObjects.get(index));
                        handCards[index].setBounds((index + 1) * gap - 100 , 20, 102, 153);
                        System.out.println(selectedCards);
                    } else if (selectedCards.size() < 5) {
                        selectedCards.add(gameLogic.sortedFirstHalfObjects.get(index));
                        handCards[index].setBounds((index + 1) * gap - 100 , 0, 102, 153);
                        System.out.println(selectedCards);
                    } else {
                        System.out.println("Selected more than 5 cards");
                    }
                }
            };
            handCards[i].addMouseListener(playerMouseListeners[i]);
        }

        ArrayList<ImageIcon> computerCardImages = new ArrayList<>();
        for (String cardName: gameLogic.sortedSecondHalf) {
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            computerCardImages.add(new ImageIcon(cardPath));
        }

        JLabel[] handCards = new JLabel[computerCardImages.size()];
        for (int i = 0; i < computerCardImages.size(); i++) {
            handCards[i] = new JLabel();
            handCards[i].setIcon(computerCardImages.get(i));
        }

        for (JLabel handCard : handCards) {
            computerCardDeck.add(handCard);
        }

        int computerCardHandLength = gameLogic.sortedSecondHalfObjects.size();
        int computerGap = CARD_WIDTH + (GAME_WIDTH - (computerCardHandLength * CARD_WIDTH)) / (computerCardHandLength + 2);
        for (int i = 0; i < handCards.length; i++) {
            handCards[i].setBounds((i + 1) * computerGap - 100, 20, 102, 153);
        }
    }
}

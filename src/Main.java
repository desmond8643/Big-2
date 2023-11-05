public class Main {
    public static void main(String[] args) {
        // object - an instance of a class that may contain attributes and methods
        // example: phone, desk, computer, coffee cup
        new GamePanel();


//        String[] ranks = {"3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2"};
//        String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"};
//        ArrayList<Card> cards = new ArrayList<Card>();
//
//        // add all the cards objects with its attribute to the card ArrayList
//        for (String suit : suits) {
//            for (int value = 0; value < ranks.length; value++) {
//                Card card = new Card(ranks[value], suit, value + 1, getSuitValue(suit));
//                cards.add(card);
//            }
//        }
//
//        // Shuffle cards (ArrayList)
//        Collections.shuffle(cards);
//
//        // Create a new object of array that stores the shuffled card
//        Card[] shuffledCards = new Card[cards.size()];
//        cards.toArray(shuffledCards);
//
//        // Create a copy of the first half and second half of shuffled cards
//        // 2 hands (2 players)
//        Card[] firstHalf = Arrays.copyOfRange(shuffledCards, 0, 13);
//        Card[] secondHalf = Arrays.copyOfRange(shuffledCards, 13, 26);
//
//        // Sort hand
//        ArrayList<String>sortedFirstHalf = new ArrayList<>();
//        ArrayList<Card>sortedFirstHalfObjects = new ArrayList<>();
//        // forward loop sort
//        for (int i = 1; i <= ranks.length; i++) {
//            for (int j = 1; j <= suits.length; j++) {
//                for (int k = 0; k < firstHalf.length; k++) {
//                    if (firstHalf[k].getSuitValue() == j && firstHalf[k].getRankValue() == i) {
//                        sortedFirstHalf.add(firstHalf[k].name());
//                        sortedFirstHalfObjects.add(firstHalf[k]);
//                    }
//                }
//            }
//        }
//
//        ArrayList<String>sortedSecondHalf = new ArrayList<>();
//        ArrayList<Card>sortedSecondHalfObjects = new ArrayList<>();
//
//        for (int i = 1; i <= ranks.length; i++) {
//            for (int j = 1; j <= suits.length; j++) {
//                for (int k = 0; k < secondHalf.length; k++) {
//                    if (secondHalf[k].getSuitValue() == j && secondHalf[k].getRankValue() == i) {
//                        sortedSecondHalf.add(secondHalf[k].name());
//                        sortedSecondHalfObjects.add(secondHalf[k]);
//                    }
//                }
//            }
//        }
//
//        // For the card object array, it can print the attributes by indicating the index of the card
////        System.out.println(firstHalf[0].getRankValue());
////        System.out.println(firstHalf[0].getSuitValue());
////        System.out.println(firstHalf[0].name());
//
//        ArrayList<Card> selectedCards = new ArrayList<>();
//
//        ArrayList<Card> cardTableObjects = new ArrayList<>();
//        ArrayList<ImageIcon> cardTableImages = new ArrayList<>();
//        boolean playerTurn = true;
//
//        JFrame frame = new JFrame();
//        JLabel label = new JLabel();
//
//        ArrayList<ImageIcon> cardImages = new ArrayList<>();
//        for (String cardName: sortedFirstHalf) {
//            String cardPath = String.format("src/small-cards/%s.png", cardName);
//            cardImages.add(new ImageIcon(cardPath));
//        }
//
//        JLabel[] handCards = new JLabel[cardImages.size()];
//        for (int i = 0; i < cardImages.size(); i++) {
//            handCards[i] = new JLabel();
//            handCards[i].setIcon(cardImages.get(i));
//        }
//
//        ArrayList<ImageIcon> computerCardImages = new ArrayList<>();
//        for (String cardName: sortedSecondHalf) {
//            String cardPath = String.format("src/small-cards/%s.png", cardName);
//            computerCardImages.add(new ImageIcon(cardPath));
//        }
//
//        JLabel[] computerHandCards = new JLabel[cardImages.size()];
//        for (int i = 0; i< computerCardImages.size(); i++) {
//            computerHandCards[i] = new JLabel();
//            computerHandCards[i].setIcon(computerCardImages.get(i));
//        }
//
////        String card1Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(0));
////        ImageIcon card1 = new ImageIcon(card1Path);
////
////        String card2Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(1));
////        ImageIcon card2 = new ImageIcon(card2Path);
////
////        String card3Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(2));
////        ImageIcon card3 = new ImageIcon(card3Path);
////
////        String card4Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(3));
////        ImageIcon card4 = new ImageIcon(card4Path);
////
////        String card5Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(4));
////        ImageIcon card5 = new ImageIcon(card5Path);
////
////        String card6Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(5));
////        ImageIcon card6 = new ImageIcon(card6Path);
////
////        String card7Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(6));
////        ImageIcon card7 = new ImageIcon(card7Path);
////
////        String card8Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(7));
////        ImageIcon card8 = new ImageIcon(card8Path);
////
////        String card9Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(8));
////        ImageIcon card9 = new ImageIcon(card9Path);
////
////        String card10Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(9));
////        ImageIcon card10 = new ImageIcon(card10Path);
////
////        String card11Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(10));
////        ImageIcon card11 = new ImageIcon(card11Path);
////
////        String card12Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(11));
////        ImageIcon card12 = new ImageIcon(card12Path);
////
////        String card13Path = String.format("src/small-cards/%s.png", sortedFirstHalf.get(12));
////        ImageIcon card13 = new ImageIcon(card13Path);
////
////        JLabel handCard1 = new JLabel();
////        JLabel handCard2 = new JLabel();
////        JLabel handCard3 = new JLabel();
////        JLabel handCard4 = new JLabel();
////        JLabel handCard5 = new JLabel();
////        JLabel handCard6 = new JLabel();
////        JLabel handCard7 = new JLabel();
////        JLabel handCard8 = new JLabel();
////        JLabel handCard9 = new JLabel();
////        JLabel handCard10 = new JLabel();
////        JLabel handCard11 = new JLabel();
////        JLabel handCard12 = new JLabel();
////        JLabel handCard13 = new JLabel();
//
//        JPanel cardDeck = new JPanel();
//        JPanel cardTable = new JPanel();
//        JPanel computerCardDeck = new JPanel();
//        JButton pauseButton = new JButton();
//        JButton confirmButton = new JButton();
//
//        pauseButton.setText("⏸");
//        pauseButton.setFocusable(false); // get rid the box surrounding the text
//        pauseButton.setFont(new Font("", Font.BOLD, 25));
//        pauseButton.setBounds(1500, 12, 75, 50);
//        pauseButton.setBackground(new Color(235, 243, 232));
//        pauseButton.setBorder(BorderFactory.createEmptyBorder());
//
//        confirmButton.setText("Confirm");
//        confirmButton.setFocusable(false);
//        confirmButton.setFont(new Font("Arial", Font.BOLD, 15));
//        confirmButton.setBounds(1500, 585, 75, 50);
//        confirmButton.setBackground(new Color(235, 243, 232));
//        confirmButton.setBorder(BorderFactory.createEmptyBorder());
//
//        final int GAME_WIDTH = 1600;
//        frame.setVisible(true);
//        frame.setSize(GAME_WIDTH, 900);
//        frame.setTitle("Big 2");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//        frame.getContentPane().setBackground(new Color(88, 129, 87));
//
////        handCard1.setIcon(card1);
////        handCard1.setBounds(50, 450, 102, 153);
////        handCard2.setIcon(card2);
////        handCard3.setIcon(card3);
////        handCard4.setIcon(card4);
////        handCard5.setIcon(card5);
////        handCard6.setIcon(card6);
////        handCard7.setIcon(card7);
////        handCard8.setIcon(card8);
////        handCard9.setIcon(card9);
////        handCard10.setIcon(card10);
////        handCard11.setIcon(card11);
////        handCard12.setIcon(card12);
////        handCard13.setIcon(card13);
//
//        label.setText("Big 2");
//        label.setFont(new Font("Arial", Font.BOLD, 30));
//        label.setVerticalAlignment(JLabel.TOP); // set vertical position of icon+text within label
//        label.setHorizontalAlignment(JLabel.CENTER); // set horizontal position of icon+text within label
//        label.setBounds(500, 15, 600, 600); // set x, y position within frame as well as dimensions
//
//        label.setHorizontalTextPosition(JLabel.CENTER); // set text LEFT, CENTER, RIGHT of imageIcon
//        label.setVerticalTextPosition(JLabel.TOP);
//
//        // label.setForeground(new Color(Color.red));
//        // label.setIconTextGap(-25); // set gap of text to image
//        // label.setBackground(color.gray); // set background color (label like to take as much room as possible
//        // label.setOpaque(true); // set label background transparent
//
//        // Border border = BorderFactory.createLineBorder(color.green,3);
//        // label.setBorder(border);
//
//        cardDeck.setLayout(null);
//        cardDeck.setBackground(new Color(178, 200, 186));
//        cardDeck.setBounds(0, 650, GAME_WIDTH, 250);
////        cardDeck.setLayout(new BorderLayout());
//        for (int i = 0; i < handCards.length; i++) {
//            cardDeck.add(handCards[i]);
//        }
//
//        computerCardDeck.setLayout(null);
//        computerCardDeck.setBackground(new Color(178, 200, 186));
//        computerCardDeck.setBounds(0, 0, GAME_WIDTH, 250);
//        for (int i = 0; i< computerHandCards.length; i++) {
//            computerCardDeck.add(computerHandCards[i]);
//        }
//
////        cardDeck.add(handCard1);
////        cardDeck.add(handCard2);
////        cardDeck.add(handCard3);
////        cardDeck.add(handCard4);
////        cardDeck.add(handCard5);
////        cardDeck.add(handCard6);
////        cardDeck.add(handCard7);
////        cardDeck.add(handCard8);
////        cardDeck.add(handCard9);
////        cardDeck.add(handCard10);
////        cardDeck.add(handCard11);
////        cardDeck.add(handCard12);
////        cardDeck.add(handCard13);
//        final int CARD_WIDTH = 102;
//        int cardHandLength = sortedFirstHalfObjects.size();
//        int gap = CARD_WIDTH + (GAME_WIDTH - (cardHandLength * CARD_WIDTH)) / (cardHandLength + 2);
//        for (int i = 0; i < handCards.length; i++) {
//            handCards[i].setBounds((i + 1) * gap - 100, 20, 102, 153);
//        }
//
//        int computerCardHandLength = sortedSecondHalfObjects.size();
//        int computerCardGap = CARD_WIDTH + (GAME_WIDTH - (computerCardHandLength * CARD_WIDTH)) / (computerCardHandLength + 2);
//        for (int i = 0; i < computerHandCards.length; i++) {
//            computerHandCards[i].setBounds((i + 1) * computerCardGap - 100, 20, 102, 153);
//        }
//
////        handCard1.setBounds(gap - 100 , 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard2.setBounds(2 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard3.setBounds(3 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard4.setBounds(4 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard5.setBounds(5 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard6.setBounds(6 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard7.setBounds(7 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard8.setBounds(8 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard9.setBounds(9 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard10.setBounds(10 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard11.setBounds(11 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard12.setBounds(12 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
////        handCard13.setBounds(13 * gap - 100, 20, 102, 153); // position is correspond to the cardDeck panel
//
//        cardTable.setLayout(null);
//        cardTable.setBackground(new Color(178, 200, 186));
//        cardTable.setBounds(0, 325, GAME_WIDTH, 250);
//
//        MouseListener[] playerMouseListeners = new MouseListener[handCards.length];
//
//        for (int i = 0; i < handCards.length; i++) {
//            final int index = i;
//            playerMouseListeners[index] = new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    if (selectedCards.contains(sortedFirstHalfObjects.get(index))) {
//                        selectedCards.remove(sortedFirstHalfObjects.get(index));
//                        handCards[index].setBounds((index + 1) * gap - 100 , 20, 102, 153);
//                        System.out.println(selectedCards);
//                    } else if (selectedCards.size() < 5) {
//                        selectedCards.add(sortedFirstHalfObjects.get(index));
//                        handCards[index].setBounds((index + 1) * gap - 100 , 0, 102, 153);
//                        System.out.println(selectedCards);
//                    } else {
//                        System.out.println("Selected more than 5 cards");
//                    }
//                }
//            };
//            handCards[i].addMouseListener(playerMouseListeners[i]);
//        }
//
//
//        // Check Deck Combination
//        // if round 1, diamond of 3 must be included
//        // check the selected card and the current card
//
//        // Combinations
//
//        // Single
//        // Size: check rank first, then suit
//
//        // Pairs (length = 2, same rank)
//        // Size: check rank first, then suit
//
//        // 3 cards (length = 3, same rank)
//        // Size: check rank
//
//        // 4 cards (length = 4, same rank)
//        // Size: check rank
//
//        // Straight
//        // (length = 5 => sort => rank of the previous number = rank of current number - 1)
//        // if consist of 1 or 2 (10jqk1, jqk12, qk123, k1234, 12345, 23456) => the rank equation varies
//        // Size: if both consist of 1 and 2, check largest 2
//        // if player 1 consist 1 and 2, then player 1 larger
//        // usual logic, check largest card
//
//        // Flush
//        // (length = 5 => sort => 5 cards have the same suit)
//        // Size: check suit
//        // if both have same suit, check largest card
//
//        // Full house
//        // length = 5 => sort => consist of 3 cards same rank and a pair of same rank
//        // Size: check 3 cards and compare who has the larger rank
//
//        // Four of a kind
//        // length = 5 => sort => consist of 4 cards same rank
//        // Size: check 4 cards and compare who has the larger rank
//
//        // Straight Flush
//        // length = 5 => sort => rank of the previous number = rank of current number - 1)
//        // if consist of 1 or 2 (10jqk1, jqk12, qk123, k1234, 12345, 23456) => the rank equation varies
//        // 5 cards have the same suit
//        // Size: check suit
//        // if same suit, check largest card
//
//        confirmButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String firstCardRank;
//                String secondCardRank;
//                String thirdCardRank;
//                String fourthCardRank;
//                switch (selectedCards.size()) {
//                    case 1:
//                        System.out.println("Single card");
//
//                        break;
//                    case 2:
//                        firstCardRank = selectedCards.get(0).getRank();
//                        secondCardRank = selectedCards.get(1).getRank();
//                        if (firstCardRank.equals(secondCardRank)) {
//                            System.out.println("Pair");
//                        } else {
//                            System.out.println("invalid");
//                        }
//                        break;
//                    case 3:
//                        firstCardRank = selectedCards.get(0).getRank();
//                        secondCardRank = selectedCards.get(1).getRank();
//                        thirdCardRank = selectedCards.get(2).getRank();
//                        if (firstCardRank.equals(secondCardRank) && firstCardRank.equals(thirdCardRank)) {
//                            System.out.println("Triples");
//                        } else {
//                            System.out.println("invalid");
//                        }
//                        break;
//                    case 4:
//                        firstCardRank = selectedCards.get(0).getRank();
//                        secondCardRank = selectedCards.get(1).getRank();
//                        thirdCardRank = selectedCards.get(2).getRank();
//                        fourthCardRank = selectedCards.get(3).getRank();
//                        if (firstCardRank.equals(secondCardRank) && firstCardRank.equals(thirdCardRank) && firstCardRank.equals(fourthCardRank)) {
//                            System.out.println("Four cards");
//                        } else {
//                            System.out.println("invalid");
//                        }
//                        break;
//                    case 5:
//                        boolean checkStraight = checkStraight(selectedCards);
//                        boolean checkFlush = checkFlush(selectedCards);
//                        if (checkStraight && checkFlush) {
//                            System.out.println("Straight Flush");
//                            break;
//                        }
//                        if (checkStraight) {
//                            System.out.println("Straight");
//                            break;
//                        }
//                        if (checkFlush) {
//                            System.out.println("Flush");
//                            break;
//                        }
//                        boolean checkFullHouse = checkFullHouse(selectedCards);
//                        if (checkFullHouse) {
//                            System.out.println("Full House");
//                            break;
//                        }
//                        boolean checkFourOfAKind = checkFourOfAKind(selectedCards);
//                        if (checkFourOfAKind) {
//                            System.out.println("Four of a kind");
//                            break;
//                        }
//
//                        System.out.println("Invalid");
//                        break;
//                }
//
//                // Straight Flush
//                // length = 5 => sort => rank of the previous number = rank of current number - 1)
//                // if consist of 1 or 2 (10jqk1, jqk12, qk123, k1234, 12345, 23456) => the rank equation varies
//                // 5 cards have the same suit
//                // Size: check suit
//                // if same suit, check largest card
//            }
//        });
//
//        frame.setLayout(null);
//        // frame.add(label);
//        frame.add(cardDeck);
//        frame.add(computerCardDeck);
//        frame.add(cardTable);
//        frame.add(pauseButton);
//        frame.add(confirmButton);
////        frame.pack();
//
//
//    }
//
//    public void renderCardInTable () {
//        cardTable.removeAll();
//        cardTableImages.clear();
//        // add image first
//        for (int i = 0; i < selectedCards.size(); i++) {
//            String cardName = selectedCards.get(i).name();
//            System.out.println(selectedCards.get(i).name());
//            String cardPath = String.format("src/small-cards/%s.png", cardName);
//            cardTableImages.add(new ImageIcon(cardPath));
//        }
//        JLabel[] cardTableCards = new JLabel[cardTableImages.size()];
//        for (int i = 0; i < cardTableImages.size(); i++) {
//            cardTableCards[i] = new JLabel();
//            cardTableCards[i].setIcon(cardTableImages.get(i));
//            cardTable.add(cardTableCards[i]);
//        }
//        cardTableObjects.clear();
//        cardTableObjects.addAll(selectedCards);
//
//        int tableHandLength = cardTableObjects.size();
//        int tableCardGap = CARD_WIDTH + (GAME_WIDTH - (tableHandLength * CARD_WIDTH)) / (tableHandLength + 2);
//        for (int i = 0; i < cardTableCards.length; i++) {
//            cardTableCards[i].setBounds((i + 1) * tableCardGap - 100, 20, 102, 153);
//        }
//    }
//
//    public static boolean checkFourOfAKind(ArrayList<Card> selectedCards) {
//        ArrayList<Integer> rankOfSelectedCards = new ArrayList<>();
//        for (Card card: selectedCards) {
//            rankOfSelectedCards.add(card.getRankValue());
//        }
//        rankOfSelectedCards.sort(Comparator.naturalOrder());
//        int firstCard = rankOfSelectedCards.get(0);
//        int secondCard = rankOfSelectedCards.get(1);
//        int thirdCard = rankOfSelectedCards.get(2);
//        int fourthCard = rankOfSelectedCards.get(3);
//        int fifthCard = rankOfSelectedCards.get(4);
//
//        if (firstCard == secondCard && secondCard == thirdCard && thirdCard == fourthCard) {
//            return true;
//        }
//        return secondCard == thirdCard && thirdCard == fourthCard && fourthCard == fifthCard;
//    }
//    public static boolean checkFullHouse(ArrayList<Card> selectedCards) {
//        ArrayList<Integer> rankOfSelectedCards = new ArrayList<>();
//        for (Card card: selectedCards) {
//            rankOfSelectedCards.add(card.getRankValue());
//        }
//        rankOfSelectedCards.sort(Comparator.naturalOrder());
//
//        int firstCard = rankOfSelectedCards.get(0);
//        int secondCard = rankOfSelectedCards.get(1);
//        int thirdCard = rankOfSelectedCards.get(2);
//        int fourthCard = rankOfSelectedCards.get(3);
//        int fifthCard = rankOfSelectedCards.get(4);
//
//        if (firstCard == secondCard && secondCard == thirdCard && fourthCard == fifthCard) {
//            return true;
//        }
//        return firstCard == secondCard && thirdCard == fourthCard && fourthCard == fifthCard;
//    }
//
//    public static boolean checkStraight(ArrayList<Card> selectedCards) {
//        ArrayList<Integer> rankOfSelectedCards = new ArrayList<>();
//        for (Card card: selectedCards) {
//            rankOfSelectedCards.add(card.getRankValue());
//        }
//        rankOfSelectedCards.sort(Comparator.naturalOrder());
//        int firstCard = rankOfSelectedCards.get(0);
//        int secondCard = rankOfSelectedCards.get(1);
//        int thirdCard = rankOfSelectedCards.get(2);
//        int fourthCard = rankOfSelectedCards.get(3);
//        int fifthCard = rankOfSelectedCards.get(4);
//
//        // special case
//        // A, 2, 3, 4, 5
//        // 2, 3, 4, 5, 6
//        if (firstCard == 1 && secondCard == 2 && thirdCard == 3 && fourthCard == 12 && fifthCard == 13) {
//            return true;
//        }
//        if (firstCard == 1 && secondCard == 2 && thirdCard == 3 && fourthCard == 4 && fifthCard == 13) {
//            return true;
//        }
//
//        // cases that all -1 rank away from each other
//        for (int i = rankOfSelectedCards.size() - 1; i > 0; i--) {
//            if (rankOfSelectedCards.get(i) - rankOfSelectedCards.get(i - 1) != 1) {
//                return false;
//            }
//        }
//        return true;
//        // 3, 4, 5, 6, 7
//        // 4, 5, 6, 7, 8
//        // 5, 6, 7, 8, 9
//        // 6, 7, 8, 9, 10
//        // 7, 8, 9, 10, J
//        // 8, 9, 10, J, Q
//        // 9, 10, J, Q, K
//        // 10, J, Q, K, A
//        // J, Q, K, A, 2
//    }
//
//    public static boolean checkFlush(ArrayList<Card> selectedCards) {
//        String[] suitOfSelectedCards = new String[5];
//        for (int i = 0; i < selectedCards.size(); i++) {
//            suitOfSelectedCards[i] = selectedCards.get(i).getSuit();
//        }
//        String firstElement = suitOfSelectedCards[0];
//        for (int i = 1; i < suitOfSelectedCards.length; i++) {
//            if (!suitOfSelectedCards[i].equals(firstElement)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private static int getSuitValue(String suit) {
//        return switch (suit) {
//            case "Diamonds" -> 1;
//            case "Clubs" -> 2;
//            case "Hearts" -> 3;
//            case "Spades" -> 4;
//            default -> 0;
//        };
    }
}


import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class ComputerLogic {
    private final GamePanel gamePanel;
    private GameLogic gameLogic;
    ComputerLogic(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }
    public void computerRandomMove(ArrayList<Card> sortedObjects) {
        // check all computer possible move and randomly choose one move
        ArrayList<ArrayList<Card>> possibleMoves = new ArrayList<>();

        // add minimum single card move
        ArrayList<Card> singleCard = new ArrayList<>();
        singleCard.add(sortedObjects.get(0));
        possibleMoves.add(singleCard);

        // add minimum pair card move
        ArrayList<Card> pair = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 1; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
            if (card1.getRankValue() == card2.getRankValue()) {
                pair.add(card1);
                pair.add(card2);
                possibleMoves.add(pair);
                break;
            }
        }

        // add minimum triples card move
        ArrayList<ArrayList<Card>> triples = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 2; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
            Card card3 = sortedObjects.get(i + 2);
            if (card1.getRankValue() == card2.getRankValue() && card1.getRankValue() == card3.getRankValue()) {
                ArrayList<Card> triple = new ArrayList<>();
                triple.add(card1);
                triple.add(card2);
                triple.add(card3);
                triples.add(triple);
            }
        }
        if (!triples.isEmpty()) {
            possibleMoves.add(triples.get(0)); // only add the smallest triple
        }

        // add minimum four cards move
        ArrayList<ArrayList<Card>> fours = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 3; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
            Card card3 = sortedObjects.get(i + 2);
            Card card4 = sortedObjects.get(i + 3);
            if (card1.getRankValue() == card2.getRankValue() && card1.getRankValue() == card3.getRankValue()
                    && card1.getRankValue() == card4.getRankValue()) {
                ArrayList<Card> four = new ArrayList<>();
                four.add(card1);
                four.add(card2);
                four.add(card3);
                four.add(card4);
                fours.add(four);
            }
        }
        if (!fours.isEmpty()) {
            possibleMoves.add(fours.get(0));
        }

        // add minimum straight move
        ArrayList<Card> straight = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 4; i++) {
            if (!straight.isEmpty()) {
                break;
            }
            Card startCard = sortedObjects.get(i);
            ArrayList<Card> tempStraight = new ArrayList<>();
            tempStraight.add(startCard);

            for (int j = i + 1; j < sortedObjects.size(); j++) {
                Card nextCard = sortedObjects.get(j);
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
        for (Card card : sortedObjects) {
            sortedSecondHalfRanks.add(card.getRankValue());
        }
        if (straight.isEmpty()) {
            // 2, 3, 4, 5, 6
            if (sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1) && sortedSecondHalfRanks.contains(2)
                    && sortedSecondHalfRanks.contains(3) && sortedSecondHalfRanks.contains(4)) {
                int[] cardRanks = {1, 2, 3, 4, 13};
                ArrayList<Card> tempStraight = new ArrayList<>();
                for (int cardRank : cardRanks) {
                    for (Card card : sortedObjects) {
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
                    for (Card card : sortedObjects) {
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
            for (int i = 0; i <= sortedObjects.size() - 4; i++) {
                ArrayList<Card> tempFlush = new ArrayList<>();
                for (int j = i; j < sortedObjects.size(); j++) {
                    Card currentCard = sortedObjects.get(j);
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

        // add minimum full house move
        ArrayList<Card> fullHouse = new ArrayList<>();
        for (ArrayList<Card> triple: triples) {
            if (!pair.isEmpty()) {
                if (triple.get(0).getRankValue() > pair.get(0).getRankValue()) {
                    fullHouse.add(pair.get(0));
                    fullHouse.add(pair.get(1));
                    fullHouse.add(triple.get(0));
                    fullHouse.add(triple.get(1));
                    fullHouse.add(triple.get(2));
                    possibleMoves.add(fullHouse);
                    break;
                } else if (triple.get(0).getRankValue() < pair.get(0).getRankValue()) {
                    fullHouse.add(triple.get(0));
                    fullHouse.add(triple.get(1));
                    fullHouse.add(triple.get(2));
                    fullHouse.add(pair.get(0));
                    fullHouse.add(pair.get(1));
                    possibleMoves.add(fullHouse);
                    break;
                }
            }
        }

        ArrayList<Card> fourOfAKind = new ArrayList<>();
        if (!fours.isEmpty() && !singleCard.isEmpty() && sortedObjects.size() > 4) {
            ArrayList<Card> four = fours.get(0);
            if (four.get(0).getRankValue() == singleCard.get(0).getRankValue()) {
                for (Card card: sortedObjects) {
                    if (four.get(0).getRankValue() != card.getRankValue()) {
                        singleCard.clear();
                        singleCard.add(card);
                    }
                }
            }
            if (four.get(0).getRankValue() > singleCard.get(0).getRankValue()) {
                fourOfAKind.add(singleCard.get(0));
                fourOfAKind.add(four.get(0));
                fourOfAKind.add(four.get(1));
                fourOfAKind.add(four.get(2));
                fourOfAKind.add(four.get(3));
                possibleMoves.add(fourOfAKind);
            }
            if (four.get(0).getRankValue() < singleCard.get(0).getRankValue()) {
                fourOfAKind.add(four.get(0));
                fourOfAKind.add(four.get(1));
                fourOfAKind.add(four.get(2));
                fourOfAKind.add(four.get(3));
                fourOfAKind.add(singleCard.get(0));
                possibleMoves.add(fourOfAKind);
            }
        }

        // add minimum straight move
        ArrayList<Card> straightFlush = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 4; i++) {
            if (!straightFlush.isEmpty()) {
                break;
            }
            Card startCard = sortedObjects.get(i);
            ArrayList<Card> tempStraightFlush = new ArrayList<>();
            tempStraightFlush.add(startCard);

            for (int j = i + 1; j < sortedObjects.size(); j++) {
                Card nextCard = sortedObjects.get(j);
                if (nextCard.getRankValue() == tempStraightFlush.get(tempStraightFlush.size() - 1).getRankValue() + 1 &&
                        nextCard.getSuitValue() == tempStraightFlush.get(tempStraightFlush.size() - 1).getSuitValue()) {
                    tempStraightFlush.add(nextCard);
                }
                if (tempStraightFlush.size() == 5) {
                    straightFlush.addAll(tempStraightFlush);
                    possibleMoves.add(straightFlush);
                    break;
                }
            }
        }

        if (straightFlush.isEmpty()) {
            // 2, 3, 4, 5, 6
            if (sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1) && sortedSecondHalfRanks.contains(2)
                    && sortedSecondHalfRanks.contains(3) && sortedSecondHalfRanks.contains(4)) {
                int[] cardRanks = {1, 2, 3, 4, 13};
                ArrayList<Card> tempStraightFlush = new ArrayList<>();
                for (int cardRank : cardRanks) {
                    for (Card card : sortedObjects) {
                        if (card.getRankValue() == cardRank) {
                            tempStraightFlush.add(card);
                            break;
                        }
                    }
                }
                int card1Suit = tempStraightFlush.get(0).getSuitValue();
                int card2Suit = tempStraightFlush.get(1).getSuitValue();
                int card3Suit = tempStraightFlush.get(2).getSuitValue();
                int card4Suit = tempStraightFlush.get(3).getSuitValue();
                int card5Suit = tempStraightFlush.get(4).getSuitValue();

                if (card1Suit == card2Suit && card1Suit == card3Suit && card1Suit == card4Suit && card1Suit == card5Suit) {
                    straightFlush.addAll(tempStraightFlush);
                    possibleMoves.add(straightFlush);
                }
            }
        }

        if (straightFlush.isEmpty()) {
            // 1, 2, 3, 4, 5
            if (sortedSecondHalfRanks.contains(12) && sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1)
                    && sortedSecondHalfRanks.contains(2) && sortedSecondHalfRanks.contains(3)) {
                int[] cardRanks = {1, 2, 3, 12, 13};
                ArrayList<Card> tempStraightFlush = new ArrayList<>();
                for (int cardRank : cardRanks) {
                    for (Card card : sortedObjects) {
                        if (card.getRankValue() == cardRank) {
                            tempStraightFlush.add(card);
                            break;
                        }
                    }
                }
                int card1Suit = tempStraightFlush.get(0).getSuitValue();
                int card2Suit = tempStraightFlush.get(1).getSuitValue();
                int card3Suit = tempStraightFlush.get(2).getSuitValue();
                int card4Suit = tempStraightFlush.get(3).getSuitValue();
                int card5Suit = tempStraightFlush.get(4).getSuitValue();

                if (card1Suit == card2Suit && card1Suit == card3Suit && card1Suit == card4Suit && card1Suit == card5Suit) {
                    straightFlush.addAll(tempStraightFlush);
                    possibleMoves.add(straightFlush);
                }
            }
        }

        // random order of arraylist and selected the first one for computer move
        if (!straightFlush.isEmpty()) {
            gamePanel.computerSelectedCards.addAll(straightFlush);
            renderComputerCardInTable(gamePanel.computerSelectedCards);
        } else {
            Collections.shuffle(possibleMoves);
            ArrayList<Card> getRandomMove = possibleMoves.get(0);
            gamePanel.computerSelectedCards.addAll(getRandomMove);
            renderComputerCardInTable(gamePanel.computerSelectedCards);
        }
        gamePanel.message.setText("Your turn");
        gamePanel.message.setBounds(725 , 310, 600, 600);
    }
    public void computerSingleCard(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        // check if there is a single card larger than the card on the table
        for (Card card: sortedObjects) {
            // same rank case
            Card cardOnTable = gamePanel.cardTableObjects.get(0);
            if (card.getRankValue() == cardOnTable.getRankValue() && card.getSuitValue() > cardOnTable.getSuitValue()) {
                gamePanel.computerSelectedCards.add(card);
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            } else if (card.getRankValue() > cardOnTable.getRankValue()) {
                gamePanel.computerSelectedCards.add(card);
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            }
        }
    }
    public void computerPair(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        // check if computer has pair
        ArrayList<ArrayList<Card>> pairs = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 1; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
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
            if (pair.get(1).getRankValue() == gamePanel.cardTableObjects.get(0).getRankValue() && pair.get(1).getSuitValue() == 4) {
                gamePanel.computerSelectedCards.add(pair.get(0));
                gamePanel.computerSelectedCards.add(pair.get(1));
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            } else if (pair.get(1).getRankValue() > gamePanel.cardTableObjects.get(0).getRankValue()) {
                gamePanel.computerSelectedCards.add(pair.get(0));
                gamePanel.computerSelectedCards.add(pair.get(1));
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            }
        }
    }
    public void computerTriples(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        // check if computer has a Triple
        ArrayList<ArrayList<Card>> triples = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 2; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
            Card card3 = sortedObjects.get(i + 2);
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
            if (triple.get(0).getRankValue() > gamePanel.cardTableObjects.get(0).getRankValue()) {
                gamePanel.computerSelectedCards.add(triple.get(0));
                gamePanel.computerSelectedCards.add(triple.get(1));
                gamePanel.computerSelectedCards.add(triple.get(2));
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            }
        }
    }
    public void computerFourCards(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        // check if computer has a four
        ArrayList<ArrayList<Card>> fours = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 3; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
            Card card3 = sortedObjects.get(i + 2);
            Card card4 = sortedObjects.get(i + 3);
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
            if (four.get(0).getRankValue() > gamePanel.cardTableObjects.get(0).getRankValue()) {
                gamePanel.computerSelectedCards.add(four.get(0));
                gamePanel.computerSelectedCards.add(four.get(1));
                gamePanel.computerSelectedCards.add(four.get(2));
                gamePanel.computerSelectedCards.add(four.get(3));
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            }
        }
    }
    public void computerStraight(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        ArrayList<ArrayList<Card>> straights = new ArrayList<>(); // check if computer has straight

        // Usual cases
        for (int i = 0; i < sortedObjects.size() - 4; i++) {
            Card startCard = sortedObjects.get(i);
            ArrayList<Card> tempStraight = new ArrayList<>();
            tempStraight.add(startCard);

            for (int j = i + 1; j < sortedObjects.size(); j++) {
                Card nextCard = sortedObjects.get(j);
                if (nextCard.getRankValue() == tempStraight.get(tempStraight.size() - 1).getRankValue() + 1) {
                    tempStraight.add(nextCard);
                }
                if (tempStraight.size() == 5 && tempStraight.get(4).getRankValue() != 13) {
                    straights.add(tempStraight);
                    break;
                }
            }
        }

        // special case: 23456 and A2345
        ArrayList<Integer> sortedSecondHalfRanks = new ArrayList<>();
        for (Card card: sortedObjects) {
            sortedSecondHalfRanks.add(card.getRankValue());
        }

        // 2, 3, 4, 5, 6
        if (sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1) && sortedSecondHalfRanks.contains(2)
                && sortedSecondHalfRanks.contains(3) && sortedSecondHalfRanks.contains(4)) {
            int[] cardRanks = {1, 2, 3, 4, 13};
            ArrayList<Card> tempStraight = new ArrayList<>();
            for (int cardRank : cardRanks) {
                for (Card card : sortedObjects) {
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
                for (Card card : sortedObjects) {
                    if (card.getRankValue() == cardRank) {
                        tempStraight.add(card);
                        break;
                    }
                }
            }
            straights.add(tempStraight);
        }

        // Check if the straight is larger than the straight on table
        // Check the sum of the last 2 rank value and if they have the same rank value, check the last card suit value

        // get table last 2 sum first
        int tableLast2Sum = gamePanel.cardTableObjects.get(3).getRankValue() + gamePanel.cardTableObjects.get(4).getRankValue();
        // handle exclusive case => 34562
        if (tableLast2Sum == 17 && gamePanel.cardTableObjects.get(4).getRankValue() == 13) {
            tableLast2Sum = 24;
        }

        for (ArrayList<Card> computerStraight: straights) {
            int comLast2Sum = computerStraight.get(3).getRankValue() + computerStraight.get(4).getRankValue();
            if (comLast2Sum > tableLast2Sum) {
                for (int i = 0; i < 5; i++) {
                    gamePanel.computerSelectedCards.add(computerStraight.get(i));
                }
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            }
            if (comLast2Sum == tableLast2Sum && computerStraight.get(4).getSuitValue() > gamePanel.cardTableObjects.get(4).getSuitValue()) {
                for (int i = 0; i < 5; i++) {
                    gamePanel.computerSelectedCards.add(computerStraight.get(i));
                }
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            }
        }

        // if largest number in straight is larger than straight on table
        // [3, 4, 5, 6, 7], [4, 5, 6, 7, 8], [5, 6, 7, 8, 9], [6, 7, 8, 9, 10], [7, 8, 9, 10, J], [8, 9, 10, J, Q], [9, 10, J, Q, K], [10, J, Q, K, A], [J, Q, K, A, 2]
    }
    public void computerFlush(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        ArrayList<ArrayList<Card>> flushes = new ArrayList<>(); // check if computer has flush
        // Usual cases
        for (int k = 1; k <= 4; k++) {
            for (int i = 0; i <= sortedObjects.size() - 4; i++) {
                ArrayList<Card> tempFlush = new ArrayList<>();
                for (int j = i; j < sortedObjects.size(); j++) {
                    Card currentCard = sortedObjects.get(j);
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

        if (gamePanel.checkStraight(gamePanel.cardTableObjects) && !flushes.isEmpty()) {
            gamePanel.computerSelectedCards.addAll(flushes.get(0));
            renderComputerCardInTable(gamePanel.computerSelectedCards);
            gamePanel.computerPass = false;
        }

        // find for smaller flush possibilities first
        for (ArrayList<Card> flush: flushes) {
            if (!gamePanel.computerPass) {
                break;
            }
            if (flush.get(0).getSuitValue() == gamePanel.cardTableObjects.get(0).getSuitValue() && flush.get(4).getRankValue() > gamePanel.cardTableObjects.get(4).getRankValue()) {
                for (int i = 0; i < 5; i++) {
                    gamePanel.computerSelectedCards.add(flush.get(i));
                }
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            }
            if (flush.get(0).getSuitValue() > gamePanel.cardTableObjects.get(0).getSuitValue()) {
                for (int i = 0; i < 5; i++) {
                    gamePanel.computerSelectedCards.add(flush.get(i));
                }
                renderComputerCardInTable(gamePanel.computerSelectedCards);
                gamePanel.computerPass = false;
                break;
            }
        }
    }
    public void computerFullHouse(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        // check computer possible triples
        ArrayList<ArrayList<Card>> triples = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 2; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
            Card card3 = sortedObjects.get(i + 2);
            if (card1.getRankValue() == card2.getRankValue() && card1.getRankValue() == card3.getRankValue()) {
                ArrayList<Card> triple = new ArrayList<>();
                triple.add(card1);
                triple.add(card2);
                triple.add(card3);
                triples.add(triple);
            }
        }
        // check if computer has pair and only get the smallest one
        ArrayList<Card> pair = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 1; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
            if (card1.getRankValue() == card2.getRankValue()) {
                // check if it is repeated with possible triples
                for (ArrayList<Card> triple: triples) {
                    if (card1.getRankValue() != triple.get(0).getRankValue()) {
                        pair.add(card1);
                        pair.add(card2);
                        break;
                    }
                }
            }
        }

        // check computer has a triple and pair
        boolean fullHouse = !triples.isEmpty() && !pair.isEmpty();

        // if table is straight or flush and computer has a full house
        if ((gamePanel.checkStraight(gamePanel.cardTableObjects) || gamePanel.checkFlush(gamePanel.cardTableObjects)) && fullHouse) {
            for (ArrayList<Card> triple: triples) {
                if (triple.get(0).getRankValue() != pair.get(0).getRankValue()) {
                    int comTriple = triple.get(0).getRankValue();
                    // add the smaller pair/triples first
                    if (comTriple > pair.get(0).getRankValue()) {
                        gamePanel.computerSelectedCards.add(pair.get(0));
                        gamePanel.computerSelectedCards.add(pair.get(1));
                        gamePanel.computerSelectedCards.add(triple.get(0));
                        gamePanel.computerSelectedCards.add(triple.get(1));
                        gamePanel.computerSelectedCards.add(triple.get(2));
                    } else {
                        gamePanel.computerSelectedCards.add(triple.get(0));
                        gamePanel.computerSelectedCards.add(triple.get(1));
                        gamePanel.computerSelectedCards.add(triple.get(2));
                        gamePanel.computerSelectedCards.add(pair.get(0));
                        gamePanel.computerSelectedCards.add(pair.get(1));
                    }
                    renderComputerCardInTable(gamePanel.computerSelectedCards);
                    gamePanel.computerPass = false;
                    break;
                }
            }
        }

        // find for smaller triple possibilities first
        if (gamePanel.computerPass) {
            int tableTriple;
            if (gamePanel.cardTableObjects.get(0).getRankValue() == gamePanel.cardTableObjects.get(2).getRankValue()) {
                tableTriple = gamePanel.cardTableObjects.get(0).getRankValue();
            } else {
                tableTriple = gamePanel.cardTableObjects.get(4).getRankValue();
            }

            for (ArrayList<Card> triple: triples) {
                int comTriple = triple.get(0).getRankValue();
                if (comTriple > tableTriple && fullHouse) {
                    // add the smaller pair/triples first
                    if (comTriple > pair.get(0).getRankValue()) { // only have larger or smaller pair will execute computer move
                        gamePanel.computerSelectedCards.add(pair.get(0));
                        gamePanel.computerSelectedCards.add(pair.get(1));
                        gamePanel.computerSelectedCards.add(triple.get(0));
                        gamePanel.computerSelectedCards.add(triple.get(1));
                        gamePanel.computerSelectedCards.add(triple.get(2));
                        renderComputerCardInTable(gamePanel.computerSelectedCards);
                        gamePanel.computerPass = false;
                        break;
                    } else if (comTriple < pair.get(0).getRankValue()) {
                        gamePanel.computerSelectedCards.add(triple.get(0));
                        gamePanel.computerSelectedCards.add(triple.get(1));
                        gamePanel.computerSelectedCards.add(triple.get(2));
                        gamePanel.computerSelectedCards.add(pair.get(0));
                        gamePanel.computerSelectedCards.add(pair.get(1));
                        renderComputerCardInTable(gamePanel.computerSelectedCards);
                        gamePanel.computerPass = false;
                        break;
                    }
                }
            }
        }
    }
    public void computerFourOfAKind(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        // find four cards
        ArrayList<ArrayList<Card>> fours = new ArrayList<>();
        for (int i = 0; i < sortedObjects.size() - 3; i++) {
            Card card1 = sortedObjects.get(i);
            Card card2 = sortedObjects.get(i + 1);
            Card card3 = sortedObjects.get(i + 2);
            Card card4 = sortedObjects.get(i + 3);
            if (card1.getRankValue() == card2.getRankValue() && card1.getRankValue() == card3.getRankValue()
                    && card1.getRankValue() == card4.getRankValue()) {
                ArrayList<Card> four = new ArrayList<>();
                four.add(card1);
                four.add(card2);
                four.add(card3);
                four.add(card4);
                fours.add(four);
            }
        }

        // get the smallest possible single card but not equal to the 4 cards combination
        ArrayList<Card> singleCard = new ArrayList<>();
        if (!fours.isEmpty()) {
            for (Card card : sortedObjects) {
                for (ArrayList<Card> four : fours) {
                    if (four.get(0).getRankValue() != card.getRankValue()) {
                        singleCard.add(card);
                        break;
                    }
                }
                if (!singleCard.isEmpty()) {
                    break;
                }
            }
        }

        boolean fourOfAKind = !fours.isEmpty() && !singleCard.isEmpty();

        // if table is straight or flush or full house and computer has a four of a kind
        if ((gamePanel.checkStraight(gamePanel.cardTableObjects) || gamePanel.checkFlush(gamePanel.cardTableObjects) || gamePanel.checkFullHouse(gamePanel.cardTableObjects)) && fourOfAKind) {
            ArrayList<Card> smallestFour = fours.get(0);
            // add the smaller pair/triples first
            if (smallestFour.get(0).getRankValue() > singleCard.get(0).getRankValue()) {
                gamePanel.computerSelectedCards.add(singleCard.get(0));
                gamePanel.computerSelectedCards.add(smallestFour.get(0));
                gamePanel.computerSelectedCards.add(smallestFour.get(1));
                gamePanel.computerSelectedCards.add(smallestFour.get(2));
                gamePanel.computerSelectedCards.add(smallestFour.get(3));
            } else {
                gamePanel.computerSelectedCards.add(smallestFour.get(0));
                gamePanel.computerSelectedCards.add(smallestFour.get(1));
                gamePanel.computerSelectedCards.add(smallestFour.get(2));
                gamePanel.computerSelectedCards.add(smallestFour.get(3));
                gamePanel.computerSelectedCards.add(singleCard.get(0));
            }
            renderComputerCardInTable(gamePanel.computerSelectedCards);
            gamePanel.computerPass = false;
        }

        // find the smallest possibility
        if (gamePanel.computerPass && fourOfAKind) {
            int tableFour;
            if (gamePanel.cardTableObjects.get(0).getRankValue() == gamePanel.cardTableObjects.get(3).getRankValue()) {
                tableFour = gamePanel.cardTableObjects.get(0).getRankValue();
            } else {
                tableFour = gamePanel.cardTableObjects.get(4).getRankValue();
            }

            for (ArrayList<Card> four: fours) {
                int comFour = four.get(0).getRankValue();
                if (comFour > tableFour) {
                    if (comFour > singleCard.get(0).getRankValue()) {
                        gamePanel.computerSelectedCards.add(singleCard.get(0));
                        gamePanel.computerSelectedCards.add(four.get(0));
                        gamePanel.computerSelectedCards.add(four.get(1));
                        gamePanel.computerSelectedCards.add(four.get(2));
                        gamePanel.computerSelectedCards.add(four.get(3));
                    } else {
                        gamePanel.computerSelectedCards.add(four.get(0));
                        gamePanel.computerSelectedCards.add(four.get(1));
                        gamePanel.computerSelectedCards.add(four.get(2));
                        gamePanel.computerSelectedCards.add(four.get(3));
                        gamePanel.computerSelectedCards.add(singleCard.get(0));
                    }
                    renderComputerCardInTable(gamePanel.computerSelectedCards);
                    gamePanel.computerPass = false;
                    break;
                }
            }
        }
    }
    public void computerStraightFlush(ArrayList<Card> sortedObjects) {
        gamePanel.computerPass = true;
        ArrayList<ArrayList<Card>> straightFlushes = new ArrayList<>(); // check if computer has straight

        // Usual cases
        for (int i = 0; i < sortedObjects.size() - 4; i++) {
            Card startCard = sortedObjects.get(i);
            ArrayList<Card> tempStraightFlush = new ArrayList<>();
            tempStraightFlush.add(startCard);

            for (int j = i + 1; j < sortedObjects.size(); j++) {
                Card nextCard = sortedObjects.get(j);
                if (nextCard.getRankValue() == tempStraightFlush.get(tempStraightFlush.size() - 1).getRankValue() + 1 &&
                        nextCard.getSuitValue() == tempStraightFlush.get(tempStraightFlush.size() - 1).getSuitValue()) {
                    tempStraightFlush.add(nextCard);
                }
                if (tempStraightFlush.size() == 5 && tempStraightFlush.get(4).getRankValue() != 13) {
                    straightFlushes.add(tempStraightFlush);
                    break;
                }
            }
        }

        // special case: 23456 and A2345
        ArrayList<Integer> sortedSecondHalfRanks = new ArrayList<>();
        for (Card card: sortedObjects) {
            sortedSecondHalfRanks.add(card.getRankValue());
        }

        // 2, 3, 4, 5, 6
        if (sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1) && sortedSecondHalfRanks.contains(2)
                && sortedSecondHalfRanks.contains(3) && sortedSecondHalfRanks.contains(4)) {
            int[] cardRanks = {1, 2, 3, 4, 13};
            ArrayList<Card> tempStraightFlush = new ArrayList<>();
            for (int cardRank : cardRanks) {
                for (Card card : sortedObjects) {
                    if (card.getRankValue() == cardRank) {
                        tempStraightFlush.add(card);
                        break;
                    }
                }
            }
            int card1 = tempStraightFlush.get(0).getSuitValue();
            int card2 = tempStraightFlush.get(1).getSuitValue();
            int card3 = tempStraightFlush.get(2).getSuitValue();
            int card4 = tempStraightFlush.get(3).getSuitValue();
            int card5 = tempStraightFlush.get(4).getSuitValue();
            if (card1 == card2 && card1 == card3 && card1 == card4 && card1 == card5) {
                straightFlushes.add(tempStraightFlush);
            }
        }

        // 1, 2, 3, 4, 5
        if (sortedSecondHalfRanks.contains(12) && sortedSecondHalfRanks.contains(13) && sortedSecondHalfRanks.contains(1)
                && sortedSecondHalfRanks.contains(2) && sortedSecondHalfRanks.contains(3)) {
            int[] cardRanks = {1, 2, 3, 12, 13};
            ArrayList<Card> tempStraightFlush = new ArrayList<>();
            for (int cardRank : cardRanks) {
                for (Card card : sortedObjects) {
                    if (card.getRankValue() == cardRank) {
                        tempStraightFlush.add(card);
                        break;
                    }
                }
            }
            int card1 = tempStraightFlush.get(0).getSuitValue();
            int card2 = tempStraightFlush.get(1).getSuitValue();
            int card3 = tempStraightFlush.get(2).getSuitValue();
            int card4 = tempStraightFlush.get(3).getSuitValue();
            int card5 = tempStraightFlush.get(4).getSuitValue();
            if (card1 == card2 && card1 == card3 && card1 == card4 && card1 == card5) {
                straightFlushes.add(tempStraightFlush);
            }
        }

        boolean straightFlush = !straightFlushes.isEmpty();

        if ((gamePanel.checkStraight(gamePanel.cardTableObjects) || gamePanel.checkFlush(gamePanel.cardTableObjects) || gamePanel.checkFullHouse(gamePanel.cardTableObjects) || gamePanel.checkFourOfAKind(gamePanel.cardTableObjects)) && straightFlush) {
            gamePanel.computerSelectedCards.add(straightFlushes.get(0).get(0));
            gamePanel.computerSelectedCards.add(straightFlushes.get(0).get(1));
            gamePanel.computerSelectedCards.add(straightFlushes.get(0).get(2));
            gamePanel.computerSelectedCards.add(straightFlushes.get(0).get(3));
            gamePanel.computerSelectedCards.add(straightFlushes.get(0).get(4));
            renderComputerCardInTable(gamePanel.computerSelectedCards);
            gamePanel.computerPass = false;
        }

        if (gamePanel.computerPass && straightFlush) {
            // Check if the straight flush is larger than the straight flush on table
            // Check the sum of the last 2 rank value and if they have the same rank value, check the last card suit value

            // get table last 2 sum first
            int tableLast2Sum = gamePanel.cardTableObjects.get(3).getRankValue() + gamePanel.cardTableObjects.get(4).getRankValue();
            // handle exclusive case => 34562
            if (tableLast2Sum == 17 && gamePanel.cardTableObjects.get(4).getRankValue() == 13) {
                tableLast2Sum = 24;
            }

            for (ArrayList<Card> computerStraightFlush : straightFlushes) {
                if (computerStraightFlush.get(0).getSuitValue() > gamePanel.cardTableObjects.get(0).getSuitValue()) {
                    for (int i = 0; i < 5; i++) {
                        gamePanel.computerSelectedCards.add(computerStraightFlush.get(i));
                    }
                    renderComputerCardInTable(gamePanel.computerSelectedCards);
                    gamePanel.computerPass = false;
                    break;
                }
                if (computerStraightFlush.get(0).getSuitValue() == gamePanel.cardTableObjects.get(0).getSuitValue()) {
                    int comLast2Sum = computerStraightFlush.get(3).getRankValue() + computerStraightFlush.get(4).getRankValue();
                    if (comLast2Sum > tableLast2Sum) {
                        for (int i = 0; i < 5; i++) {
                            gamePanel.computerSelectedCards.add(computerStraightFlush.get(i));
                        }
                        renderComputerCardInTable(gamePanel.computerSelectedCards);
                        gamePanel.computerPass = false;
                        break;
                    }
                }
            }
        }
    }
    public void computerMove(String combination, ArrayList<Card> sortedObjects) {
        switch (combination) {
            case "Single card" -> computerSingleCard(sortedObjects);
            case "Pair" -> computerPair(sortedObjects);
            case "Triples" -> computerTriples(sortedObjects);
            case "Four cards" -> computerFourCards(sortedObjects);
            case "Straight" -> {
                computerStraight(sortedObjects);
                if (gamePanel.computerPass) {
                    computerFlush(sortedObjects);
                }
                if (gamePanel.computerPass) {
                    computerFullHouse(sortedObjects);
                }
                if (gamePanel.computerPass) {
                    computerFourOfAKind(sortedObjects);
                }
                if (gamePanel.computerPass) {
                    computerStraightFlush(sortedObjects);
                }
            }
            case "Flush" -> {
                computerFlush(sortedObjects);
                if (gamePanel.computerPass) {
                    computerFullHouse(sortedObjects);
                }
                if (gamePanel.computerPass) {
                    computerFourOfAKind(sortedObjects);
                }
                if (gamePanel.computerPass) {
                    computerStraightFlush(sortedObjects);
                }
            }
            case "Full House" -> {
                computerFullHouse(sortedObjects);
                if (gamePanel.computerPass) {
                    computerFourOfAKind(sortedObjects);
                }
                if (gamePanel.computerPass) {
                    computerStraightFlush(sortedObjects);
                }
            }
            case "Four of a kind" -> {
                computerFourOfAKind(sortedObjects);
                if (gamePanel.computerPass) {
                    computerStraightFlush(sortedObjects);
                }
            }
            case "Straight Flush" -> computerStraightFlush(sortedObjects);
        }
    }
    public void renderComputerCardInTable(ArrayList<Card> computerSelectedCards) {
        gamePanel.cardTable.removeAll();
        gamePanel.cardTable.revalidate();
        gamePanel.cardTable.repaint();
        gamePanel.cardTableImages.clear();

        for (int i = 0; i < computerSelectedCards.size(); i++) {
            String cardName = computerSelectedCards.get(i).name();
            System.out.println(computerSelectedCards.get(i).name());
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            gamePanel.cardTableImages.add(new ImageIcon(cardPath));
        }
        JLabel[] cardTableCards = new JLabel[gamePanel.cardTableImages.size()];
        for (int i = 0; i < gamePanel.cardTableImages.size(); i++) {
            cardTableCards[i] = new JLabel();
            cardTableCards[i].setIcon(gamePanel.cardTableImages.get(i));
            gamePanel.cardTable.add(cardTableCards[i]);
        }
        gamePanel.cardTableObjects.clear();
        gamePanel.cardTableObjects.addAll(computerSelectedCards);

        gamePanel.computerCardDeck.removeAll();
        gamePanel.computerCardDeck.revalidate();
        gamePanel.computerCardDeck.repaint();

        for (Card card: computerSelectedCards) {
            gameLogic.sortedSecondHalfObjects.remove(card);
            gameLogic.sortedSecondHalf.remove(card.name());
            gameLogic.sortedThirdHalfObjects.remove(card);
            gameLogic.sortedThirdHalf.remove(card.name());
        }

        computerSelectedCards.clear();

        ArrayList<ImageIcon> cardImages = new ArrayList<>();
        for (String cardName: gameLogic.sortedSecondHalf) {
            String cardPath = String.format("src/small-cards/%s.png", cardName);
            cardImages.add(new ImageIcon(cardPath));
        }

        JLabel[] handCards = new JLabel[cardImages.size()];
        for (int i = 0; i < cardImages.size(); i++) {
            handCards[i] = new JLabel();
            handCards[i].setIcon(cardImages.get(i));
        }

        for (JLabel handCard : handCards) {
            gamePanel.computerCardDeck.add(handCard);
        }

        int cardHandLength = gameLogic.sortedSecondHalfObjects.size();
        int gap = gamePanel.CARD_WIDTH + (gamePanel.GAME_WIDTH - (cardHandLength * gamePanel.CARD_WIDTH)) / (cardHandLength + 2);
        for (int i = 0; i < handCards.length; i++) {
            handCards[i].setBounds((i + 1) * gap - 100, 20, 102, 153);
        }

        int tableHandLength = gamePanel.cardTableObjects.size();
        int tableCardGap = gamePanel.CARD_WIDTH + (gamePanel.GAME_WIDTH - (tableHandLength * gamePanel.CARD_WIDTH)) / (tableHandLength + 2);
        for (int i = 0; i < cardTableCards.length; i++) {
            cardTableCards[i].setBounds((i + 1) * tableCardGap - 100, 20, 102, 153);
        }
    }
}

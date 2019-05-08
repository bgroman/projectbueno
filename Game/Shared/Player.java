package Shared;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private Card lastDrewCard;
    private String name;

    public Player(String name) {
        hand = new ArrayList<>();
        this.name = name;
    }

    public boolean playCard(Card playCard) {
        //returns true and removes card from hand if card is in hand, else returns false
        return hand.remove(playCard);
    }

    public Card getCard(int index) {
        return hand.get(index);
    }

    public void drawCard(Card drawCard) {
        hand.add(drawCard);
        lastDrewCard = drawCard;
    }

    public Card getLastDrewCard() {
        return lastDrewCard;
    }

    public void printCards() {
        int count = 0;
        for (Card c : hand) {
            System.out.println(count + ") " + c.toString());
            count++;
        }
    }

    public int getHandLength() {
        return hand.size();
    }

    public ArrayList getHand(){
        return hand;
    }

    public String getName() {
        return name;
    }

    public boolean handContainsCard(Card card){
        return hand.contains(card);
    }

    public void updateHand(ArrayList<Card> hand){
        this.hand = hand;
    }
}

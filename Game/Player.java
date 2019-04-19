package Game;

import java.util.LinkedList;

public class Player {
    LinkedList<Card> hand;
    String name;

    public Player(String name){
        hand = new LinkedList<>();
        this.name = name;
    }

    public boolean playCard(Card playCard){
        //returns true and removes card from hand if card is in hand, else returns false
        return hand.remove(playCard);
    }

    public void drawCard(Card drawCard){
        hand.addFirst(drawCard);
    }

    public void printCards(){
        for(Card c : hand)
            System.out.println(c.toString());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Shared;

import java.util.LinkedList;

/**
 *
 * @author alawren3
 */
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

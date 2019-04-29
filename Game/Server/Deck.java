/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Server;

import java.util.Collections;
import java.util.LinkedList;
import Game.Shared.*;

public class Deck {
    LinkedList<Card> drawPile;
    LinkedList<Card> playedCardsPile;

    public Deck(){
        //could add a for loop around addCards() to add multiple decks with a parameter controller how many multiples
        drawPile = new LinkedList<>();
        playedCardsPile = new LinkedList<>();
        addCards();
    }

    public void addCards() {
        //kind of messy, need to separate into 2 cases, wild or color suit
        //then inside of the if/else put the right number of cards for that suit
        //1 zero, 2 of every other card per every color suit and then 4 wild and 4 wild draw for wild suit
        for (Suit s : Suit.values()) {
            for (CardType t : CardType.values()) {
                if(s != Suit.Wild){
                    switch (t) {
                        case Zero:
                            drawPile.addFirst(new Card(t,s));
                            break;
                        case Wild:
                            break;
                        case WildDraw:
                            break;
                        default:
                            drawPile.addFirst(new Card(t,s));
                            drawPile.addFirst(new Card(t,s));
                            break;
                    }
                }
                else{
                    switch (t){
                        case Wild:
                            drawPile.addFirst(new Card(t,s));
                            drawPile.addFirst(new Card(t,s));
                            drawPile.addFirst(new Card(t,s));
                            drawPile.addFirst(new Card(t,s));
                            break;
                        case WildDraw:
                            drawPile.addFirst(new Card(t,s));
                            drawPile.addFirst(new Card(t,s));
                            drawPile.addFirst(new Card(t,s));
                            drawPile.addFirst(new Card(t,s));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(drawPile);
    }

    public Card getTopCard(){
        return playedCardsPile.getFirst();
    }

    public Card drawCard(){
        //if no more cards to draw, do work
        //give first card
        if(drawPile.size() == 0){
            Card topCard = playedCardsPile.removeFirst();
            drawPile = playedCardsPile;
            playedCardsPile = new LinkedList<>();
            playedCardsPile.addFirst(topCard);
            shuffle();
        }
        return drawPile.removeFirst();
    }

    public boolean playCard(Card newCard){
        if(Card.checkMove(playedCardsPile.getFirst(), newCard)){
            playedCardsPile.addFirst(newCard);
            return true;
        }
        else
            return false;
    }
}
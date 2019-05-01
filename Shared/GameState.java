package Shared;

import java.util.ArrayList;

public class GameState {
    public CyclicLinkedList<Player> players;//list of players
    public ArrayList<Card> hand;
    public Player currPlayer;//current player
    public boolean skip;//if current player is being skipped
    public Card currCard;

    public GameState(CyclicLinkedList<Player> players, ArrayList<Card> hand, Player currPlayer, Card currCard, boolean skip){
        this.players = players;
        this.hand = hand;
        this.currPlayer = currPlayer;
        this.currCard = currCard;
        this.skip = skip;
    }
}

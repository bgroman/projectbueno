package Shared;

public class Card {
    private CardType value;
    private Suit suit;

    public Card(CardType value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Suit getSuit(){
        return suit;
    }

    public CardType getCardVal(){
        return value;
    }

    public void setSuit(Suit s){
        suit = s;
    }

    @Override
    public String toString(){
        return suit.toString() + " " + value.toString();
    }
}

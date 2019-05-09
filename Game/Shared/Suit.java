/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Shared;

public enum Suit {
    Red(0),
    Green(1),
    Yellow(2),
    Blue(3),
    Wild(4);

    private final byte byteCode;

    private Suit(int byteCode){
        this.byteCode = (byte)byteCode;
    }

    public byte getByte(){
        return byteCode;
    }

    public static Suit fromByte(byte byteCode){
        for(Suit suit : values()){
            if (suit.byteCode == byteCode)
                return suit;
        }
        throw new IllegalArgumentException();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Shared;


public enum CardType {
    Zero(0),
    One(1),
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    DrawTwo(10),
    Skip(11),
    Reverse(12),
    Wild(13),
    WildDraw(14);

    private final byte byteCode;

    private CardType(int byteCode){
        this.byteCode = (byte)byteCode;
    }

    public byte getByte(){
        return byteCode;
    }
}


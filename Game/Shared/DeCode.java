/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Shared;

import java.nio.ByteBuffer;

/**
 *
 * @author alawren3
 */
public class DeCode {

    public int opcode;
    public String ip;
    public byte[] cardData;

    public DeCode(byte[] unParsedData, int usefulData){
        ByteBuffer bb = ByteBuffer.wrap(unParsedData);
        opcode = bb.getInt();
        if(opcode == 1){
            byte[] unparsedString = new byte[usefulData-4];
            bb.get(unparsedString);
            ip = new String(unparsedString);
        }else if(opcode == 5 ||opcode == 6 ||opcode == 7 ||opcode == 11){
            cardData = new byte[usefulData-4];
            if(cardData.length>0){
                bb.get(cardData);
            }
        }
    }

}

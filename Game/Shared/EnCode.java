/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Shared;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author alawren3
 */
public class EnCode {

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    /*
    for codes: 0, 2, 3, 4, 8, 9, 10, 12
     */
    public EnCode(int ackOpCode)throws IOException{
        stream.write(intToBytes(ackOpCode));
    }

    /*
    for codes: 1
     */

    public EnCode(String ip)throws IOException{
        stream.write(intToBytes(1));
        stream.write(ip.getBytes());
    }

    /*
    for codes: 5, 6, 7, 11
     */

    public EnCode(byte[] cardData, int opCode)throws IOException{
        stream.write(intToBytes(opCode));
        if(cardData!=null) {
            stream.write(cardData);
        }
    }

    private byte[] intToBytes(int i){
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    public byte[] getHeader(){
        return stream.toByteArray();
    }

    public int getSize(){
        return stream.toByteArray().length;
    }

}

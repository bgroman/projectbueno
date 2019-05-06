/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;
import java.nio.ByteBuffer;
import Game.Shared.Constants;

/**
 *
 * @author alawren3
 */
public class UniCastProtocol {
    private static Constants C = new Constants();
    private static DatagramSocket socket;
    private static DatagramPacket receiver;
    private static DatagramPacket sender;

    UniCastProtocol(int port, String address, int timeOutInMill){
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(timeOutInMill);
            sender = new DatagramPacket(new byte[C.MAXDATASIZE],C.MAXDATASIZE, InetAddress.getByName(address),port);
            receiver = new DatagramPacket(new byte[C.MAXDATASIZE],C.MAXDATASIZE);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] data){
        sender.setData(data);sender.setPort(data.length);
        try {
            socket.send(sender);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*notes
    using InterruptedIOException to signify that you didnt get the packet you wanted
    in the amount of time you expected.
     */
    public byte[] recieve(int attempts) throws InterruptedIOException {
        int count = 0;

        try {
            socket.receive(receiver);
            byte[] data = new byte[receiver.getLength()];
            ByteBuffer bb = ByteBuffer.wrap(receiver.getData());
            bb.get(data);
            return data;
        } catch(SocketTimeoutException e){
            count++;
            if (count>=attempts){
                throw new InterruptedIOException();
            }
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    
}

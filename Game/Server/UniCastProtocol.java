/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Server;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

import Game.Shared.Constants;

/**
 *
 * @author alawren3
 */
public class UniCastProtocol {

    private static DatagramSocket socket;
    private static DatagramPacket receiver;
    private static DatagramPacket sender;
    private InetAddress lastReceivedAddress;

    UniCastProtocol(int port){
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    /*
    this is the primary send method, the other is if we dont have the inetaddress;
     */
    public void send(byte[] data, InetAddress address, int port){
        sender = new DatagramPacket(data,data.length,address,port);
        try {
            socket.send(sender);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] data, String address, int port){
        try {
            sender = new DatagramPacket(data,data.length,InetAddress.getByName(address),port);
            socket.send(sender);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] receive(int size){
        try {
            receiver = new DatagramPacket(new byte[size],size);
            socket.receive(receiver);
            byte[] data = new byte[receiver.getLength()];
            ByteBuffer bb = ByteBuffer.wrap(receiver.getData());
            bb.get(data);
            lastReceivedAddress = receiver.getAddress();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public int getPort(){
        return receiver.getPort();
    }

    public String getAddress(){
        return receiver.getAddress().getHostName();
    }

    public InetAddress getLastReceivedAddress(){
        return lastReceivedAddress;
    }
}

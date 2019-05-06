/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Server;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author alawren3
 */
public class MultiCastProtocol {
    private static final String SERVER_IP_GROUP = "356.25.0.0";
    private DatagramSocket socket;

    MultiCastProtocol(int port){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] data, int port){
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(SERVER_IP_GROUP), port);
            socket.send(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerIpGroup(){return SERVER_IP_GROUP;}

}

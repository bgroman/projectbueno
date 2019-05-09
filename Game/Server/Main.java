/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Server;

import Game.Shared.*;

import java.net.InetAddress;

/**
 *
 * @author alawren3
 */
public class Main {
    static int port = 2705;//random port for now
    static MultiCastProtocol multiCastProtocol;
    static UniCastProtocol uniCastProtocol;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        multiCastProtocol = new MultiCastProtocol(port);
        uniCastProtocol = new UniCastProtocol(port);
        runGame(runLobby());

    }

    //lobby stuff
    public static CyclicLinkedList<Player> runLobby(){


    }

    //game stuff
    public static void runGame(CyclicLinkedList<Player> players){
        int multiple = players.size() > 5 ? 4 : 2;
        ServerGame game = new ServerGame(players, multiple);
        while(game.checkGameRunning()){
            game.nextTurn();
            //send next players turn ack
            multiCastProtocol.send((new EnCode(8)).getHeader(), port);

            PlayerMove playerMove = receiveCard(game.getCurrPlayer().getInetAddress());

            if(playerMove.draw){//player wants to draw a card
                uniCastProtocol.send((new EnCode(game.drawCard(),11)).getHeader(), game.getCurrPlayer().getInetAddress(), port);

                playerMove = receiveCard(game.getCurrPlayer().getInetAddress());
                if(deCode.opcode == 5){//player wants to play drawn card
                    if(game.playDrawCard(new PlayerMove(deCode.card))){//check if player can play drawn card
                        multiCastProtocol.send((new EnCode(deCode.card, 6)).getHeader(), port);//if true send multicast of card played
                    }else{
                        multiCastProtocol.send((new EnCode(6)).getHeader(), port);//else send null multicast, signalling no card was played
                    }
                }else{//player sent wrong message, going to assume it doesn't want to play drawn card
                    multiCastProtocol.send((new EnCode(6)).getHeader(), port);//else send null multicast, signalling no card was played
                }
            }else if (playerMove.wildSuit == null){//non wild card being played
                if()

            }

            game.playCard()




        }
    }

    private static PlayerMove receiveCard(InetAddress inetAddress){
        InetAddress lastMessageAddress = null;
        try {
            lastMessageAddress = InetAddress.getByName("0.0.0.0");
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
        int opcode = 0;
        PlayerMove playerMove = null;
        //if the message opcode is 5 and it came from the right client, then break from loop
        while(!lastMessageAddress.getHostAddress().equals(inetAddress.getHostAddress()) && !(opcode == 5)){
            DeCode deCode = new DeCode(uniCastProtocol.receive(1400));
            lastMessageAddress = uniCastProtocol.getLastReceivedAddress();
            opcode = deCode.opcode;

            switch (opcode){
                //opcode 0-3 should never be received at this point in the game
                case 4://keep alive
                    uniCastProtocol.send((new EnCode(4)).getHeader(),lastMessageAddress,port);
                    break;
                case 5:
                    if(deCode.card == null){//null card means draw
                        playerMove = new PlayerMove(true);
                    }else {
                        playerMove = new PlayerMove(deCode.card);
                    }
                    break;
                case 9:
                    //handle disconnect
                    break;
                case 10:
                    //handle uno
                    break;
                case 13://wild
                    playerMove = new PlayerMove(deCode.card, deCode.wildSuit);
                    break;
                default://opcode 0-3,6-8,11,12 shouldn't be received here
                    break;
            }
        }
        return playerMove;
    }
}

package Shared;


public class GameReply {
    public Card drawCard;//draw card for current player
    public boolean validMove;//if move was valid

    public GameReply(boolean validMove){
        this.validMove = validMove;
        drawCard = null;
    }

    public GameReply(boolean validMove, Card drawCard){
        this.validMove = validMove;
        this.drawCard = drawCard;
    }
}

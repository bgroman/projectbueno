package Client;

import Shared.*;

public class ClientGame {
    private boolean drawTwo = false;
    private boolean drawFour = false;
    private boolean skip = false;
    private Player currPlayer;
    private Player me;
    private Card currCard;
    private CyclicLinkedList<Player> players;

    public ClientGame(GameState gameState) {
        me = new Player("me");
        me.updateHand(gameState.hand);
        currPlayer = gameState.currPlayer;
        currCard = gameState.currCard;
        skip = gameState.skip;
        players = gameState.players;
    }

    public void updateGameState(GameState gameState){
        me = new Player("me");
        me.updateHand(gameState.hand);
        currPlayer = gameState.currPlayer;
        currCard = gameState.currCard;
        skip = gameState.skip;
        players = gameState.players;
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    //these methods should more or less be executed in series by the server
    //increment to next players turn and reset skip
    public void nextTurn(Player currPlayer) {
        this.currPlayer = currPlayer;
        players.next();
        if(!currPlayer.equals(players.current())){
            //this shouldn't happen
        }
        skip = false;
    }

    public boolean checkSkip() {
        return skip;
    }

    public void drawCard(Card card){
        me.drawCard(card);
    }

    public void drawCardArray(Card[] cards){
        for(Card card:cards){
            me.drawCard(card);
        }
    }

    //if player move card is null, then draw card
    public GameReply playCard(Card card) {
        if (currPlayer.handContainsCard(card)) {
            if (ClientGame.checkMove(currCard, card)) {
                //case for successfully played card
                resolveMove(playerMove);
                return new GameReply(true);
            }
        }
        //case for card not in hand or not valid move
        return new GameReply(false);
    }

    public GameReply playDrawCard(PlayerMove playerMove) {
        if (playerMove.card.equals(currPlayer.getLastDrewCard())) {
            if (ClientGame.checkMove(deck.getTopCard(), playerMove.card)) {
                resolveMove(playerMove);
                return new GameReply(true);
            }
        }
        return new GameReply(false);
    }

    private void resolveWild(Suit wildSuit) {
        Card blankWild = new Card(CardType.Wild, wildSuit);
        deck.addWild(blankWild);
    }

    private void resolveMove(PlayerMove playerMove) {
        deck.playCard(playerMove.card);
        currPlayer.playCard(playerMove.card);
        //handle action cards
        switch (playerMove.card.getCardVal()) {
            case Wild:
                resolveWild(playerMove.wildSuit);
                break;
            case WildDraw:
                resolveWild(playerMove.wildSuit);
                drawFour = true;
                break;
            case Reverse:
                players.flipDirection();
                break;
            case DrawTwo:
                drawTwo = true;
                skip = true;
                break;
            case Skip:
                skip = true;
                break;
            default:
                break;
        }
    }

    private static boolean checkMove(Card topCard, Card newCard) {
        //if wild card then move is always valid
        //if the new cards suit or value matches the top cards then move is valid
        //else move is false
        if (newCard.getCardVal() == CardType.Wild || newCard.getCardVal() == CardType.WildDraw)
            return true;
        else if (newCard.getCardVal() == topCard.getCardVal() || newCard.getSuit() == topCard.getSuit())
            return true;
        else
            return false;
    }

   /* public void run() {
        Scanner sc = new Scanner(System.in);
        //deal cards


        while (true) {
            Player p = players.next();
            //skip will always be true if drawTwo or drawFour is true, so only checking skip for now
            if (!skip) {
                System.out.println("\n" + p.getName() + "'s turn");
                System.out.println("Top card is " + deck.getTopCard().toString() + "\n");
                System.out.println("Your cards are:");
                p.printCards();

                boolean moveValid = false;
                while (!moveValid) {
                    System.out.println("Enter the index of the card you wish to play or type draw");
                    String move = sc.nextLine();

                    if (move.equalsIgnoreCase("draw")) {
                        Card drawCard = deck.drawCard();
                        p.drawCard(drawCard);
                        System.out.println(drawCard.toString() + " drawn");
                        System.out.println("Type yes to play card draw");
                        move = sc.nextLine();
                        if (move.equalsIgnoreCase("yes")) {
                            if (ClientGame.checkMove(deck.getTopCard(), drawCard)) {
                                p.playCard(drawCard);
                                deck.playCard(drawCard);
                            }
                        }
                        moveValid = true;
                    } else if (isNumeric(move)) {
                        int cardNum = Integer.parseInt(move);
                        Card playCard = p.getCard(cardNum);

                        if (playCard.getSuit() == Suit.Wild) {
                            p.playCard(playCard);//remove from player hand
                            deck.playCard(playCard);//add to deck
                            //create temporary card based on desired suit and add to deck using special deck.addWild() method
                            //the deck will handle removing the blank card when the next card is played.
                            Card blankWild = null;
                            while (blankWild == null) {
                                System.out.println("What color would you like to change to?");
                                String newColor = sc.nextLine();
                                switch (newColor) {
                                    case "green":
                                        blankWild = new Card(CardType.Wild, Suit.Green);
                                        break;
                                    case "red":
                                        blankWild = new Card(CardType.Wild, Suit.Red);
                                        break;
                                    case "blue":
                                        blankWild = new Card(CardType.Wild, Suit.Blue);
                                        break;
                                    case "yellow":
                                        blankWild = new Card(CardType.Wild, Suit.Yellow);
                                        break;
                                    default:
                                        System.out.println("Invalid suit, pick green, red, blue, or yellow");
                                        break;
                                }
                            }
                            deck.addWild(blankWild);

                            if (playCard.getCardVal() == CardType.WildDraw) {
                                skip = true;
                                drawFour = true;
                            }
                            moveValid = true;

                        } else if (ClientGame.checkMove(deck.getTopCard(), playCard)) {
                            p.playCard(playCard);
                            deck.playCard(playCard);
                            switch (playCard.getCardVal()) {
                                case Reverse:
                                    players.flipDirection();
                                    break;
                                case DrawTwo:
                                    drawTwo = true;
                                    skip = true;
                                    break;
                                case Skip:
                                    skip = true;
                                    break;
                                default:
                                    break;
                            }
                            moveValid = true;
                        } else {
                            System.out.println("Move not valid, try again.");
                        }

                    } else if (move.equalsIgnoreCase("end")) {
                        moveValid = true;
                    } else {
                        System.out.println("Command not valid, try again.");
                    }

                    if (p.getHandLength() == 0) {
                        System.out.println(p.getName() + " won");
                        return;
                    }
                }
                //handle skip and stuff
            } else {
                System.out.println("\n" + p.getName() + "'s turn was skipped");
                if (drawTwo) {
                    Card temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);

                    temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);
                    drawTwo = false;
                } else if (drawFour) {
                    Card temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);

                    temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);

                    temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);

                    temp = deck.drawCard();
                    System.out.println(p.getName() + " drew " + temp.toString());
                    p.drawCard(temp);
                    drawFour = false;
                }
                skip = false;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of players");
        int playersNum = sc.nextInt();
        sc.nextLine();
        Player p = new Player("tempRemove");
        CyclicLinkedList<Player> tempPlayers = new CyclicLinkedList<Player>(p);
        for (int i = 1; i <= playersNum; i++) {
            System.out.println("Enter player " + i + "'s name");
            tempPlayers.add(new Player(sc.nextLine()));
        }
        tempPlayers.remove(p);
        ClientGame game = new ClientGame(tempPlayers, 1);
        game.run();
    }*/

}

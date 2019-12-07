package com.example.hive.Hive;

import android.view.View;

import com.example.hive.R;
import com.example.hive.game.GameComputerPlayer;
import com.example.hive.game.GameMainActivity;
import com.example.hive.game.infoMessage.GameInfo;

import java.util.ArrayList;

/**
 * Smart Computer Player which sends moves more competitively
 * than that of the regular computer player
 *
 * @author Erik Liu
 */

public class HiveSmartComputerPlayer extends GameComputerPlayer {

    /* instance variables */
    private final int BLACK_TURN = 0;
    private final int WHITE_TURN = 1;

    private int randomLocation;
    private int freeSpaces;
    private int spaceCount;
    private int randomBug;
    private int bugCounter;

    int startX = 0;
    int startY = 0;

    //gets an array list that holds only pieces of the same color
    private ArrayList<HiveGameState.piece> hand;


    // the android activity that we are running
    private GameMainActivity myActivity;

    //Hive Surface View
    private HiveView surfaceView;
    private ArrayList<HiveGameState.piece> myBugList = new ArrayList<>();

    /**
     * Constructor which takes in the name of the ComputerPlayer
     * @param name
     */
    public HiveSmartComputerPlayer(String name){super(name);}

    /**getHand
     *
     * gets pieces of the same color from all the avaliable pieces
     *
     * @param list full bug list array
     * @param color color that pieces that you want
     * @return
     */
    public ArrayList<HiveGameState.piece> getHand(ArrayList<HiveGameState.piece> list, int color){
        ArrayList<HiveGameState.piece> tempHand = new ArrayList<>();

        //collects all the black pieces and return an array list that only contains black pieces
        if(color == BLACK_TURN){
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) == HiveGameState.piece.BANT || list.get(i) == HiveGameState.piece.BBEE
                        || list.get(i) == HiveGameState.piece.BBEETLE || list.get(i) == HiveGameState.piece.BGHOPPER
                        || list.get(i) == HiveGameState.piece.BSPIDER){
                    tempHand.add(list.get(i));
                }
            }
        }
        //colelcts all the white pieces and return an array list that only contains white pieces
        else if(color == WHITE_TURN){
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) == HiveGameState.piece.WANT || list.get(i) == HiveGameState.piece.WBEE
                        || list.get(i) == HiveGameState.piece.WBEETLE || list.get(i) == HiveGameState.piece.WGHOPPER
                        || list.get(i) == HiveGameState.piece.WSPIDER){
                    tempHand.add(list.get(i));
                }
            }
        }

        return tempHand;
    }
    /**numOfTargets
     *
     * goes through the board and see how many targets are on the board
     *
     * @param game
     * @return
     */
    public int numOfTargets(HiveGameState game){
        int count = 0;
        //goes through the board looking for targets
        for (int i = 1; i < game.board.length - 1; i++) {
            for (int j = 1; j < game.board[j].length - 1; j++) {
                if (game.board[i][j] == HiveGameState.piece.TARGET) {
                    count += 1;
                }
            }
        }

        return count;
    }


    /**getPiecesX
     *
     * finds the x coordinate of a certain piece on the board
     *
     * @param game
     * @param bug
     * @param boardNum
     * @return
     */
    public int getPieceX(HiveGameState game, HiveGameState.piece bug, int boardNum){
        int xCoord;
        int count = 1;
        for(int i = 1; i < game.board.length -1; i++){
            for(int j = 1; j < game.board[j].length -1; j++){
                if(game.board[i][j] == bug){
                    if(count == boardNum){
                        xCoord = i;
                        return xCoord;
                    }
                    else{
                        count += 1;
                    }
                }
            }
        }
        return 0;
    }

    /**getPiecesY
     *
     * finds the y coordinate of a certain piece on the board
     *
     * @param game
     * @param bug
     * @param boardNum
     * @return
     */
    public int getPieceY(HiveGameState game, HiveGameState.piece bug, int boardNum){
        int yCoord;
        int count = 1;
        for(int i = 1; i < game.board.length -1; i++){
            for(int j = 1; j < game.board[j].length -1; j++){
                if(game.board[i][j] == bug){
                    if(count == boardNum){
                        yCoord = j;
                        return yCoord;
                    }
                    else{
                        count += 1;
                    }
                }
            }
        }
        return 0;
    }

    /**numPieceAround
     *
     * finds how many pieces are around a certain piece
     * mostly used as a helper method for the ai to decide between offense and defense
     *
     * @param game
     * @param bug
     * @param boardNum
     * @return
     */
    public int numPieceAround (HiveGameState game, HiveGameState.piece bug, int boardNum){
        int beeX, beeY;
        beeX = getPieceX(game, bug, boardNum);
        beeY = getPieceY(game, bug, boardNum);
        int count = 0;

        if(beeY%2 == 0){
            if(game.board[beeX-1][beeY-1] == HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX-1][beeY] == HiveGameState.piece.EMPTY)
                count += 1;
            if(game.board[beeX][beeY+1] == HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX+1][beeY] == HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX+1][beeY-1] == HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX][beeY-1] == HiveGameState.piece.EMPTY)
                count +=1;
            return count;

        }
        else{
            if(game.board[beeX-1][beeY-1] != HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX-1][beeY] != HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX][beeY+1] != HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX+1][beeY] != HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX+1][beeY-1] != HiveGameState.piece.EMPTY)
                count +=1;
            if(game.board[beeX][beeY-1] != HiveGameState.piece.EMPTY)
                count +=1;
            return count;
        }
    }


    /**ghopperMove
     *
     * helper method to add targets to the board for spaces the grass hopper can move
     * (uses recurrsion)
     *
     * @param x of the grasshopper
     * @param y of the grasshopper
     * @param game HiveGameState
     * @param direction for checks in
     *
     * 0 for top left and goes clockwise until 5 for plain left
     *
     */
    public void ghopperMove(int x, int y, HiveGameState game, int direction){
        if(x-1 < 0 || y-1 < 0 || x+1 > game.board.length - 1 || y+1 > game.board[y].length -1){
            return;
        }
        switch(direction){
            case 0:
                if(y%2 == 0) {
                    if (game.board[x - 1][y - 1] == HiveGameState.piece.EMPTY) {
                        game.board[x - 1][y - 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        ghopperMove(x - 1, y - 1, game, 0);
                        return;
                    }
                }
                else{
                    if (game.board[x - 1][y] == HiveGameState.piece.EMPTY) {
                        game.board[x - 1][y] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        ghopperMove(x - 1, y, game, 0);
                        return;
                    }
                }

            case 1:
                if(y%2 == 0) {
                    if (game.board[x - 1][y] == HiveGameState.piece.EMPTY) {
                        game.board[x - 1][y] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        ghopperMove(x - 1, y, game, 1);
                        return;
                    }
                }
                else{
                    if (game.board[x - 1][y + 1] == HiveGameState.piece.EMPTY) {
                        game.board[x - 1][y + 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        ghopperMove(x - 1, y + 1, game, 1);
                        return;
                    }
                }

            case 2:
                if(game.board[x][y+1] == HiveGameState.piece.EMPTY){
                    game.board[x][y+1] = HiveGameState.piece.TARGET;
                    return;
                }
                else{
                    ghopperMove(x, y+1, game, 2);
                    return;
                }

            case 3:
                if(y%2 == 0) {
                    if (game.board[x + 1][y] == HiveGameState.piece.EMPTY) {
                        game.board[x + 1][y] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        ghopperMove(x + 1, y, game, 3);
                        return;
                    }
                }
                else{
                    if (game.board[x + 1][y + 1] == HiveGameState.piece.EMPTY) {
                        game.board[x + 1][y + 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        ghopperMove(x + 1, y + 1, game, 3);
                        return;
                    }
                }

            case 4:
                if(y%2 == 0) {
                    if (game.board[x + 1][y - 1] == HiveGameState.piece.EMPTY) {
                        game.board[x + 1][y - 1] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        ghopperMove(x + 1, y - 1, game, 4);
                        return;
                    }
                }
                else{
                    if (game.board[x + 1][y] == HiveGameState.piece.EMPTY) {
                        game.board[x + 1][y] = HiveGameState.piece.TARGET;
                        return;
                    }
                    else{
                        ghopperMove(x + 1, y, game, 4);
                        return;
                    }
                }

            case 5:
                if(game.board[x][y-1] == HiveGameState.piece.EMPTY){
                    game.board[x][y-1] = HiveGameState.piece.TARGET;
                    return;
                }
                else{
                    ghopperMove(x, y-1, game, 5);
                    return;
                }
        }
    }

    /**spiderMove
     *
     * helper method to find all the spots that a spider can move to using recursion
     *
     * @param x to check
     * @param y to check
     * @param game
     * @param iteration how far it currently is away
     */
    public void spiderMove(int x, int y, HiveGameState game, int iteration){
        if(x-1 < 0 || y-1 < 0 || x+1 > 11 || y+1 > 11){
            return;
        }

        if(iteration == 3){
            if(game.board[x][y] == HiveGameState.piece.EMPTY){
                if(y%2 == 0){
                    if(game.board[x-1][y-1] != HiveGameState.piece.EMPTY ||
                            game.board[x-1][y] != HiveGameState.piece.EMPTY ||
                            game.board[x][y+1] != HiveGameState.piece.EMPTY ||
                            game.board[x+1][y] != HiveGameState.piece.EMPTY ||
                            game.board[x+1][y-1] != HiveGameState.piece.EMPTY ||
                            game.board[x][y-1] != HiveGameState.piece.EMPTY){

                        game.board[x][y] = HiveGameState.piece.TARGET;
                    }
                }
                else{
                    if(game.board[x-1][y-1] != HiveGameState.piece.EMPTY ||
                            game.board[x-1][y] != HiveGameState.piece.EMPTY ||
                            game.board[x][y+1] != HiveGameState.piece.EMPTY ||
                            game.board[x+1][y] != HiveGameState.piece.EMPTY ||
                            game.board[x+1][y-1] != HiveGameState.piece.EMPTY ||
                            game.board[x][y-1] != HiveGameState.piece.EMPTY){

                        game.board[x][y] = HiveGameState.piece.TARGET;
                    }
                }
            }
        }

        if(y%2 == 0){
            spiderMove(x-1, y-1, game, iteration +1);
            spiderMove(x-1, y, game, iteration +1);
            spiderMove(x, y+1, game, iteration +1);
            //spiderMove(x+1, y, game, iteration +1);
            //spiderMove(x+1, y-1, game, iteration +1);
            //spiderMove(x, y-1, game, iteration +1);
        }
        else{
            spiderMove(x-1, y, game, iteration +1);
            spiderMove(x-1, y+1, game, iteration +1);
            spiderMove(x, y+1, game, iteration +1);
            //spiderMove(x+1, y+1, game, iteration +1);
            //spiderMove(x+1, y, game, iteration +1);
            //spiderMove(x, y-1, game, iteration +1);
        }
    }

    /**
     *
     * for the smart ai, the order that the pieces that are played are preset, but their location
     * is random. As for the move pieces, which piece and where it moves to is random but the piece
     * it selects based on offense and defense is not random.
     *
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        //TODO You will implement this method to receive state objects from the game
        if(info instanceof HiveGameState){
            sleep(400);
            HiveGameState test = new HiveGameState((HiveGameState) info);

            myBugList = test.getBugList();
            freeSpaces = 0;
            spaceCount = 0;
            randomBug = 0;

            //checks if it is the players turn
            if(test.getTurn() == playerNum) {
                //place piece

                //creates an array list of useable pieces to place
                hand = getHand(myBugList, BLACK_TURN);
                if(hand.size() > 0) {

                    int l,k;
                    for(l = 1; l < test.board.length-1; l++){
                        for(k = 1; k < test.board[k].length-1; k++){
                            if(test.board[l][k] != HiveGameState.piece.EMPTY){
                                test.makeTarget(l, k);
                            }
                        }
                    }

                    freeSpaces = numOfTargets(test);


                    randomLocation = (int) (Math.random() * freeSpaces);
                    randomBug = (int) (Math.random()*hand.size());

                    //if no pieces on the board, place a piece in the middle
                    if(freeSpaces == 0){
                        HivePlacePieceAction placePiece = new HivePlacePieceAction(this, 6, 6, hand.get(randomBug));
                        game.sendAction(placePiece);
                        return;
                    }

                    //if there are pieces already on the board randomly place a piece at a random
                    //location
                    for (int i = 1; i < test.board.length - 1; i++) {
                        for (int j = 1; j < test.board[j].length - 1; j++) {
                            if (test.board[i][j] == HiveGameState.piece.TARGET) {
                                spaceCount += 1;
                            }
                            if ((spaceCount == randomLocation) && test.checkIslandsPlace(i, j)) {
                                HiveGameState.piece bug;
                                if(hand.contains(HiveGameState.piece.BSPIDER)){
                                    bug = HiveGameState.piece.BSPIDER;
                                }
                                else if(hand.contains(HiveGameState.piece.BBEE)){
                                    bug = HiveGameState.piece.BBEE;
                                }
                                else if(hand.contains(HiveGameState.piece.BGHOPPER)){
                                    bug = HiveGameState.piece.BGHOPPER;
                                }
                                else if(hand.contains(HiveGameState.piece.BANT)){
                                    bug = HiveGameState.piece.BANT;
                                }
                                else{
                                    bug = HiveGameState.piece.BBEETLE;
                                }
                                HivePlacePieceAction placePiece = new HivePlacePieceAction(this, i, j, bug);
                                game.sendAction(placePiece);
                                return;

                            }
                        }
                    }
                }
                else {
                    //move piece

                    boolean validMove = false;

                    int defense = numPieceAround(test, HiveGameState.piece.BBEE, 1);
                    //loops until it finds an action that it can send that will be valid
                    while (!validMove) {

                        //randomly select a piece for either offense or defense
                        if(defense < 3) {
                            randomBug = (int) (Math.random() * 8);
                        }
                        else{
                            randomBug = (int) (Math.random() * 3);
                        }
                        bugCounter = 0;

                        //randomly select a bug from the board based on offense and defense
                        for (int i = 1; i < test.board.length - 1; i++) {
                            for (int j = 1; j < test.board[j].length - 1; j++) {
                                if(defense < 3) {
                                    if (test.board[i][j] == HiveGameState.piece.BANT ||
                                            test.board[i][j] == HiveGameState.piece.BSPIDER ||
                                            test.board[i][j] == HiveGameState.piece.BGHOPPER) {
                                        if (!(test.checkSurround(i, j))) {
                                            if (bugCounter == randomBug) {
                                                startX = i;
                                                startY = j;
                                            }
                                            bugCounter += 1;
                                        }
                                    }
                                }
                                else{
                                    if (test.board[i][j] == HiveGameState.piece.BBEE ||
                                            test.board[i][j] == HiveGameState.piece.BBEETLE) {
                                        if (!(test.checkSurround(i, j))) {
                                            if (bugCounter == randomBug) {
                                                startX = i;
                                                startY = j;
                                            }
                                            bugCounter += 1;
                                        }
                                    }
                                }
                            }
                        }

                        //sets the target for the piece that was chosen
                        switch (test.board[startX][startY]) {
                            case BBEE:
                                test.makeTarget(startX, startY);
                                break;
                            case BBEETLE:
                                test.makeTarget(startX, startY);
                                break;
                            case BANT:
                                for(int i = 1; i < test.board.length - 1; i ++){
                                    for(int j = 1; j < test.board[j].length - 1; j++){
                                        test.makeTarget(startX, startY);
                                    }
                                }
                                break;
                            case BGHOPPER:
                                ghopperMove(startX, startY, test, 0);//check upleft
                                ghopperMove(startX, startY, test, 1);//check upright
                                ghopperMove(startX, startY, test, 2);//check right
                                ghopperMove(startX, startY, test, 3);//check downright
                                ghopperMove(startX, startY, test, 4);//check downleft
                                ghopperMove(startX, startY, test, 5);//check left
                                break;
                            case BSPIDER:
                                spiderMove(startX, startY, test, 1);
                                break;
                        }
                        if (numOfTargets(test) > 0) {
                            validMove = true;
                        }
                    }
                    //picks a random target location
                    randomLocation = (int)(Math.random()*numOfTargets(test));
                    spaceCount = 0;

                    for (int i = 1; i < test.board.length - 1; i++) {
                        for (int j = 1; j < test.board[j].length - 1; j++) {
                            if (test.board[i][j] == HiveGameState.piece.TARGET) {
                                if((spaceCount == randomLocation)  && test.checkIslands(startX, startY, i, j)){
                                    HiveMoveAction moveAction = new HiveMoveAction(this, startX, startY, i, j);
                                    game.sendAction(moveAction);
                                    return;
                                }
                                spaceCount += 1;
                            }
                        }
                    }

                }

            }


        }
    }//receiveInfo

    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.hive_layout);

        //Initialize the widget reference member variables


    }//setAsGui


}

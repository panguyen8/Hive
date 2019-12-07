package com.example.hive.Hive;

import android.view.View;

import com.example.hive.R;
import com.example.hive.game.GameComputerPlayer;
import com.example.hive.game.GameMainActivity;
import com.example.hive.game.infoMessage.GameInfo;
import com.example.hive.game.infoMessage.GameState;

import java.util.ArrayList;

import static com.example.hive.Hive.HiveGameState.piece.WSPIDER;

public class HiveComputerPlayer extends GameComputerPlayer {

    /* instance variables */
    private final int BLACK_TURN = 0;
    private final int WHITE_TURN = 1;

    //variables that hold counters and random ints for the ai to use
    private int randomLocation;
    private int freeSpaces;
    private int spaceCount;
    private int randomBug;
    private int bugCounter;

    //used in the move piece action since original coordinates are needed
    int startX = 0;
    int startY = 0;

    //gets an array list that hold only pieces of the same color
    private ArrayList<HiveGameState.piece> hand;

    // the android activity that we are running
    private GameMainActivity myActivity;

    //Hive Surface View
    private ArrayList<HiveGameState.piece> myBugList = new ArrayList<>();


    public HiveComputerPlayer(String name){super(name);}

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

        //collect all the black pieces and return an array list that only contains black pieces
        if(color == BLACK_TURN){
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) == HiveGameState.piece.BANT || list.get(i) == HiveGameState.piece.BBEE
                || list.get(i) == HiveGameState.piece.BBEETLE || list.get(i) == HiveGameState.piece.BGHOPPER
                || list.get(i) == HiveGameState.piece.BSPIDER){
                    tempHand.add(list.get(i));
                }
            }
        }
        //collect all the white ppieces and return an array list that only contains white pieces
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
                //if there is a target incriment the counter by one
                if (game.board[i][j] == HiveGameState.piece.TARGET) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /**moveablePiece
     *
     * checks to see how many pieces on the board can be moved so when a random piece is picked
     * it is able to move
     *
     * @param game
     * @return
     */
    public int moveablePiece(HiveGameState game){
        int count = 0;
        //go through the entire board
        for(int i = 1; i < game.board.length - 1; i++){
            for(int j = 1; j < game.board[j].length - 1; j++) {
                //look for black pieces
                if(game.board[i][j] == HiveGameState.piece.BANT ||
                        game.board[i][j] == HiveGameState.piece.BBEE ||
                        game.board[i][j] == HiveGameState.piece.BBEETLE ||
                        game.board[i][j] == HiveGameState.piece.BGHOPPER ||
                        game.board[i][j] == HiveGameState.piece.BSPIDER){
                    //if there is a black piece look if the piece is surrounded
                    if(!(game.checkSurround(i, j))){
                        count += 1;
                    }
                }
            }
        }
        return count;
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
        //checks for out of bounds
        if(x-1 < 0 || y-1 < 0 || x+1 > game.board.length - 1 || y+1 > game.board[y].length -1){
            return;
        }
        //using the direction, goes in one direction until there is an opening, if theres an opening,
        //swtich it from empty to target
        //mod opperators are for the offset of the board
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
        //checks for out of bounds
        if(x-1 < 0 || y-1 < 0 || x+1 > 11 || y+1 > 11){
            return;
        }

        //base case, if the function has been called three times looks if the piece is attached
        //to the hive and is a valid place to move
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

        //recursive case, calls the spiderMove method again
        //half the cases are removed since it caused a stack overflow
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

    /**recieveInfo
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

                //places piece

                //creates an array list of useable pieces to place
                hand = getHand(myBugList, playerNum);
                //if nothing to place -> skip place piece and go to move piece
                if(hand.size() > 0) {

                    int l,k;
                    for(l = 1; l < test.board.length-1; l++){
                        for(k = 1; k < test.board[k].length-1; k++){
                            if(test.board[l][k] != HiveGameState.piece.EMPTY){
                                test.makeTarget(l, k);
                            }
                        }
                    }



                    freeSpaces = numOfTargets(test);//looks at how many free spaces there are
                    randomLocation = (int) (Math.random() * freeSpaces);//randomly picks a free space
                    randomBug = (int) (Math.random()*hand.size());//randomly pick a bug from your hand




                    //if no pieces on the board, place a piece in the middle
                    if(freeSpaces == 0){
                        HiveButtonAction buttonAction = new HiveButtonAction(this, hand.get(randomBug));
                        game.sendAction(buttonAction);
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
                            if (spaceCount == randomLocation) {
                                HivePlacePieceAction placePiece = new HivePlacePieceAction(this, i, j, hand.get(randomBug));
                                game.sendAction(placePiece);
                                return;

                            }
                        }
                    }
                }
                else {
                    //move piece

                    boolean validMove = false;
                    //loops until it finds an action that it can send that will be valid
                    while (!validMove) {
                        //randomly pick a bug
                        randomBug = (int) (Math.random() * moveablePiece(test));

                        bugCounter = 0;

                        //randomly select a bug from the board
                        for (int i = 1; i < test.board.length - 1; i++) {
                            for (int j = 1; j < test.board[j].length - 1; j++) {
                                if (test.board[i][j] == HiveGameState.piece.BBEE ||
                                        test.board[i][j] == HiveGameState.piece.BBEE ||
                                        test.board[i][j] == HiveGameState.piece.BBEETLE ||
                                        test.board[i][j] == HiveGameState.piece.BGHOPPER ||
                                        test.board[i][j] == HiveGameState.piece.BSPIDER) {
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

                        //look at what piece was selected and set targets for valid spaces to move
                        //to
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
                        //if there is at least one target (one valid move leave the loop)
                        if (numOfTargets(test) > 0) {
                            validMove = true;
                        }
                    }
                    //pick a random valid location to move
                    randomLocation = (int)(Math.random()*numOfTargets(test));
                    spaceCount = 0;

                        //finds the location of the valid action and send the HiveMoveAction
                        for (int i = 1; i < test.board.length - 1; i++) {
                            for (int j = 1; j < test.board[j].length - 1; j++) {
                                if (test.board[i][j] == HiveGameState.piece.TARGET) {
                                    if(spaceCount == randomLocation){
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

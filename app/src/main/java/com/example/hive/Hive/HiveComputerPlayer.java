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

    private int randomLocation;
    private int freeSpaces;
    private int spaceCount;
    private int randomBug;
    private int piecesLeft;
    private int bugCounter;

    int startX = 0;
    int startY = 0;
    int endX = 0;
    int endY = 0;
    HiveGameState.piece bug;

    private ArrayList<HiveGameState.piece> hand;


    // the android activity that we are running
    private GameMainActivity myActivity;

    //Hive Surface View
    private HiveView surfaceView;
    private ArrayList<HiveGameState.piece> myBugList = new ArrayList<>();


    public HiveComputerPlayer(String name){super(name);}

    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    public ArrayList<HiveGameState.piece> getHand(ArrayList<HiveGameState.piece> list, int color){
        ArrayList<HiveGameState.piece> tempHand = new ArrayList<>();

        if(color == BLACK_TURN){
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) == HiveGameState.piece.BANT || list.get(i) == HiveGameState.piece.BBEE
                || list.get(i) == HiveGameState.piece.BBEETLE || list.get(i) == HiveGameState.piece.BGHOPPER
                || list.get(i) == HiveGameState.piece.BSPIDER){
                    tempHand.add(list.get(i));
                }
            }
        }
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

    public int numOfTargets(HiveGameState game){
        int count = 0;
        for (int i = 1; i < game.board.length - 1; i++) {
            for (int j = 1; j < game.board[j].length - 1; j++) {
                if (game.board[i][j] == HiveGameState.piece.TARGET) {
                    count += 1;
                }
            }
        }

        return count;
    }

    public int moveablePiece(HiveGameState game){
        int count = 0;
        for(int i = 1; i < game.board.length - 1; i++){
            for(int j = 1; j < game.board[j].length - 1; j++) {
                if(game.board[i][j] == HiveGameState.piece.BANT ||
                        game.board[i][j] == HiveGameState.piece.BBEE ||
                        game.board[i][j] == HiveGameState.piece.BBEETLE ||
                        game.board[i][j] == HiveGameState.piece.BGHOPPER ||
                        game.board[i][j] == HiveGameState.piece.BSPIDER){
                    if(!(game.checkSurround(i, j))){
                        count += 1;
                    }
                }
            }
        }
        return count;
    }

    /**
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
        //left and rights
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
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        //TODO You will implement this method to receive state objects from the game
        if(info instanceof HiveGameState){
            HiveGameState test = new HiveGameState((HiveGameState) info);

            myBugList = test.getBugList();
            freeSpaces = 0;
            spaceCount = 0;
            randomBug = 0;


            if(test.getTurn() == playerNum) {
                //place piece
                hand = getHand(myBugList, BLACK_TURN);
                if(hand.size() > 0) {

                    for (int i = 1; i < test.board.length - 1; i++) {
                        for (int j = 1; j < test.board[j].length - 1; j++) {

                        }
                    }
                    freeSpaces = numOfTargets(test);

                    randomLocation = (int) (Math.random() * freeSpaces);
                    randomBug = (int) (Math.random()*hand.size());

                    if(freeSpaces == 0){
                        HivePlacePieceAction placePiece = new HivePlacePieceAction(this, 6, 6, hand.get(randomBug));
                        game.sendAction(placePiece);
                        return;
                    }

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
                    while (!validMove) {
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

                        switch (test.board[startX][startY]) {
                            case BBEE:
                                test.makeTarget(startX, startY);
                                break;
                            case BBEETLE:
                                break;
                            case BANT:
                                for(int i = 0; i < test.board.length; i ++){
                                    for(int j = 0; j < test.board[j].length; j++){
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
                        randomLocation = (int)(Math.random()*numOfTargets(test));
                        spaceCount = 0;

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

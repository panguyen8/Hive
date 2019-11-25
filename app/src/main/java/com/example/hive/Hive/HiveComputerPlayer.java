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

    public void beeMove(HiveGameState game){
        spaceCount = 0;
        game.makeTarget(startX, startY);
        randomLocation = (int)(Math.random()*numOfTargets(game));
        bug = HiveGameState.piece.BBEE;

        for (int i = 1; i < game.board.length - 1; i++) {
            for (int j = 1; j < game.board[j].length - 1; j++) {
                if (game.board[i][j] == HiveGameState.piece.TARGET) {
                    if (spaceCount == randomLocation) {
                        endX = i;
                        endY = j;
                    }
                    spaceCount += 1;
                }

            }
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
                    randomBug = (int) (Math.random() * moveablePiece(test));


                    bugCounter = 0;

                    for (int i = 1; i < test.board.length - 1; i++) {
                        for (int j = 1; j < test.board[j].length - 1; j++) {

                            if (test.board[i][j] == HiveGameState.piece.BBEE) {
                                startX = i;
                                startY = j;
                            }

                        /*
                        if(test.board[i][j] == HiveGameState.piece.BANT ||
                                test.board[i][j] == HiveGameState.piece.BBEE ||
                                test.board[i][j] == HiveGameState.piece.BBEETLE ||
                                test.board[i][j] == HiveGameState.piece.BGHOPPER ||
                                test.board[i][j] == HiveGameState.piece.BSPIDER){
                            if(!(test.checkSurround(i, j))) {
                                if(bugCounter == randomBug){
                                    startX = i;
                                    startY = j;
                                }
                                bugCounter += 1;
                            }
                        }

                        */
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

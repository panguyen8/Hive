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

    private int randomX;
    private int randomY;


    // the android activity that we are running
    private GameMainActivity myActivity;

    //Hive Surface View
    private HiveView surfaceView;
    private ArrayList<HiveGameState.piece> myBugList = new ArrayList<>();
    private HiveGameState.piece[][] board = new HiveGameState.piece[20][20];


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
            board = test.getBoard();

            randomX = (int) (Math.random()*19);
            randomY = (int) (Math.random()*19);
            if(test.getTurn() == playerNum) {
                if(playerNum == WHITE_TURN)
                {
                    if(myBugList.contains(WSPIDER)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, WSPIDER);
                        game.sendAction(placePieceAction);
                    }
                    else if(myBugList.contains(HiveGameState.piece.WBEE)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.WBEE);
                        game.sendAction(placePieceAction);
                    }
                    else if(myBugList.contains(HiveGameState.piece.WGHOPPER)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.WGHOPPER);
                        game.sendAction(placePieceAction);
                    }
                    else if(myBugList.contains(HiveGameState.piece.WANT)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.WANT);
                        game.sendAction(placePieceAction);
                    }
                    else if(myBugList.contains(HiveGameState.piece.WBEETLE)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.WBEETLE);
                        game.sendAction(placePieceAction);
                    }

                    for(;;){
                        if(board[randomX][randomY] == HiveGameState.piece.WSPIDER || board[randomX][randomY] == HiveGameState.piece.WBEE ||
                                board[randomX][randomY] == HiveGameState.piece.WGHOPPER || board[randomX][randomY] == HiveGameState.piece.WANT ||
                                board[randomX][randomY] == HiveGameState.piece.WBEETLE){
                            int randomSpace = (int)(Math.random()*5);
                            if(randomY%2 == 1){
                                switch(randomSpace) {
                                    case(0):
                                        HiveMoveAction moveUpLeft = new HiveMoveAction(this, randomX, randomY, randomX, randomY - 1);
                                        break;
                                    case(1):
                                        HiveMoveAction moveUpRight = new HiveMoveAction(this, randomX, randomY, randomX + 1, randomY - 1);
                                        break;
                                    case(2):
                                        HiveMoveAction moveLeft = new HiveMoveAction(this, randomX, randomY, randomX - 1, randomY);
                                        break;
                                    case(3):
                                        HiveMoveAction moveRight = new HiveMoveAction(this, randomX, randomY, randomX + 1, randomY);
                                        break;
                                    case(4):
                                        HiveMoveAction moveDownLeft = new HiveMoveAction(this, randomX, randomY, randomX, randomY + 1);
                                        break;
                                    case(5):
                                        HiveMoveAction moveDownRight = new HiveMoveAction(this, randomX, randomY, randomX + 1, randomY + 1);
                                        break;
                                }
                            }
                            else{
                                switch(randomSpace) {
                                    case(0):
                                        HiveMoveAction moveUpLeft = new HiveMoveAction(this, randomX, randomY, randomX - 1, randomY - 1);
                                        break;
                                    case(1):
                                        HiveMoveAction moveUpRight = new HiveMoveAction(this, randomX, randomY, randomX, randomY - 1);
                                        break;
                                    case(2):
                                        HiveMoveAction moveLeft = new HiveMoveAction(this, randomX, randomY, randomX - 1, randomY);
                                        break;
                                    case(3):
                                        HiveMoveAction moveRight = new HiveMoveAction(this, randomX, randomY, randomX + 1, randomY);
                                        break;
                                    case(4):
                                        HiveMoveAction moveDownLeft = new HiveMoveAction(this, randomX, randomY, randomX - 1, randomY + 1);
                                        break;
                                    case(5):
                                        HiveMoveAction moveDownRight = new HiveMoveAction(this, randomX, randomY, randomX, randomY + 1);
                                        break;
                                }
                            }
                            randomX = (int)(Math.random()*19);
                            randomY = (int)(Math.random()*19);
                        }
                    }
                }
                else if(playerNum == BLACK_TURN)
                {
                    if(myBugList.contains(HiveGameState.piece.BSPIDER)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.BSPIDER);
                        game.sendAction(placePieceAction);
                    }
                    else if(myBugList.contains(HiveGameState.piece.BBEE)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.BBEE);
                        game.sendAction(placePieceAction);
                    }
                    else if(myBugList.contains(HiveGameState.piece.BGHOPPER)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.BGHOPPER);
                        game.sendAction(placePieceAction);
                    }
                    else if(myBugList.contains(HiveGameState.piece.BANT)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.BANT);
                        game.sendAction(placePieceAction);
                    }
                    else if(myBugList.contains(HiveGameState.piece.BBEETLE)) {
                        HivePlacePieceAction placePieceAction = new HivePlacePieceAction(this, randomX, randomY, HiveGameState.piece.BBEETLE);
                        game.sendAction(placePieceAction);
                    }
                    for(;;){
                        if(board[randomX][randomY] == HiveGameState.piece.BSPIDER || board[randomX][randomY] == HiveGameState.piece.BBEE ||
                                board[randomX][randomY] == HiveGameState.piece.BGHOPPER || board[randomX][randomY] == HiveGameState.piece.BANT ||
                                board[randomX][randomY] == HiveGameState.piece.BBEETLE){
                            int randomSpace = (int)(Math.random()*5);
                            if(randomY%2 == 1){
                                switch(randomSpace) {
                                    case(0):
                                        HiveMoveAction moveUpLeft = new HiveMoveAction(this, randomX, randomY, randomX, randomY - 1);
                                        break;
                                    case(1):
                                        HiveMoveAction moveUpRight = new HiveMoveAction(this, randomX, randomY, randomX + 1, randomY - 1);
                                        break;
                                    case(2):
                                        HiveMoveAction moveLeft = new HiveMoveAction(this, randomX, randomY, randomX - 1, randomY);
                                        break;
                                    case(3):
                                        HiveMoveAction moveRight = new HiveMoveAction(this, randomX, randomY, randomX + 1, randomY);
                                        break;
                                    case(4):
                                        HiveMoveAction moveDownLeft = new HiveMoveAction(this, randomX, randomY, randomX, randomY + 1);
                                        break;
                                    case(5):
                                        HiveMoveAction moveDownRight = new HiveMoveAction(this, randomX, randomY, randomX + 1, randomY + 1);
                                        break;
                                }
                            }
                            else{
                                switch(randomSpace) {
                                    case(0):
                                        HiveMoveAction moveUpLeft = new HiveMoveAction(this, randomX, randomY, randomX - 1, randomY - 1);
                                        break;
                                    case(1):
                                        HiveMoveAction moveUpRight = new HiveMoveAction(this, randomX, randomY, randomX, randomY - 1);
                                        break;
                                    case(2):
                                        HiveMoveAction moveLeft = new HiveMoveAction(this, randomX, randomY, randomX - 1, randomY);
                                        break;
                                    case(3):
                                        HiveMoveAction moveRight = new HiveMoveAction(this, randomX, randomY, randomX + 1, randomY);
                                        break;
                                    case(4):
                                        HiveMoveAction moveDownLeft = new HiveMoveAction(this, randomX, randomY, randomX - 1, randomY + 1);
                                        break;
                                    case(5):
                                        HiveMoveAction moveDownRight = new HiveMoveAction(this, randomX, randomY, randomX, randomY + 1);
                                        break;
                                }
                            }
                            randomX = (int)(Math.random()*19);
                            randomY = (int)(Math.random()*19);
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

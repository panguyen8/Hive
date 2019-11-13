package com.example.hive.Hive;

import android.view.View;

import com.example.hive.R;
import com.example.hive.game.GameComputerPlayer;
import com.example.hive.game.GameMainActivity;
import com.example.hive.game.infoMessage.GameInfo;
import com.example.hive.game.infoMessage.GameState;

public class HiveComputerPlayer extends GameComputerPlayer {

    /* instance variables */

    // the android activity that we are running
    private GameMainActivity myActivity;

    //Hive Surface View
    private HiveView surfaceView;

    private int chosenPiece;
    private int board[][] = new int[20][20];

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

    }//receiveInfo

    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.hive_layout);

        //Initialize the widget reference member variables


    }//setAsGui

    public void availableMoves(GameState state) {
        if (chosenPiece == 2) {
            //iterate through the entire array
            for (int i = 1; i < 20-1; i++) {
                for (int j = 1; j < 20-1; j++) {
                    //check the empty spots of the board
                    /*
                    if (state.board[j][i] == 0) {
                        //if the board spot is empty, check surrounding spaces for certain move restrictions
                        if (board[j][i] == 1) {
                            //turn
                            board[j][i] == 2;
                        }
                    }

                     */
                }
            }
        }
    }
}

package com.example.hive.Hive;

import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hive.R;
import com.example.hive.game.GameHumanPlayer;
import com.example.hive.game.GameMainActivity;
import com.example.hive.game.infoMessage.GameInfo;
import com.example.hive.game.infoMessage.GameState;
import com.example.hive.game.utilities.Logger;

public class HiveHumanPlayer extends GameHumanPlayer {

    /* instance variables */

    // the android activity that we are running
    private GameMainActivity myActivity;

    //Hive Surface View
    private HiveView surfaceView;

    private int chosenPiece;
    private int board[][] = new int[20][20];

    /**
     * constructor does nothing extra
     */
    public HiveHumanPlayer(String name) {
        super(name);
    }

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

    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return true;
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        int x = (int) event.getX();
        int y = (int) event.getY();
        Point p = surfaceView.mapPixelToSquare(x, y);

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game
        if (p == null) {
            surfaceView.flash(Color.RED, 50);
        } else {
            HiveMoveAction action = new HiveMoveAction(this, p.y, p.x);
            Logger.log("onTouch", "Human player sending TTTMA ...");
            game.sendAction(action);
            surfaceView.invalidate();
        }

        // register that we have handled the event
        return true;

    }

    public boolean onTouchEvent(MotionEvent e) {
            //gets the coordinates of mouse click
            float x = e.getX();
            float y = e.getY();

            //turns the coordinates into ints and into single digits
            int xPiece = ((int) x) / 100;
            int yPiece = ((int) y) / 100;

            //if mouse click goes beyond parameters, return false
            if (xPiece > 3 || yPiece > 3) {
                return false;
            }


            //initialize xEnd and yEnd coordinates
            int xEnd = 3;
            int yEnd = 3;
            //find zero
            for (int yDir = 0; yDir < 4; yDir++) {
                for (int xDir = 0; xDir < 4; xDir++) {
                    if (puzzle.boardNormal[xDir][yDir] == 0) {
                        xEnd = xDir;
                        yEnd = yDir;
                    }
                }
            }

            //will only move if correct piece is touched
            invalidate();

            return true;
        }


    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
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
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    //check the empty spots of the board
                    if (state.board[j][i] == 0) {
                        //if the board spot is empty, check surrounding spaces for certain move restrictions
                        if (board[j][i] == 1) {
                            //turn
                            board[j][i] == 2;
                        }
                    }
                }
            }
        }
    }

}

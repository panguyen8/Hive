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

public class HiveHumanPlayer extends GameHumanPlayer implements View.OnClickListener{

    protected boolean moveReady = false;
    protected int xStart = 0;
    protected int yStart = 0;
    protected int xEnd = 0;
    protected int yEnd = 0;


    /* instance variables */

    // the android activity that we are running
    private GameMainActivity myActivity;

    //Hive Surface View
    private HiveView surfaceView;

    /**
     * constructor does nothing extra
     */
    public HiveHumanPlayer(String name) {

        super(name);
    }

    /**
     * OnClick Method for placing pieces
     *
     * @param v
     */
    public void onClick(View v) {
        switch(v.getId()) {
            //case
                //condition
            HivePlacePieceAction action = new HivePlacePieceAction(this, )
        }
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
        if (moveReady = false) {
            // ignore if not an "up" event
            if (event.getAction() != MotionEvent.ACTION_UP) return true;
            // get the x and y coordinates of the touch-location;
            // convert them to square coordinates (where both
            // values are in the range 0..2)
            xStart = (int) event.getX();
            yStart = (int) event.getY();

            Point p = surfaceView.mapPixelToSquare(xStart, yStart);

            // if the location did not map to a legal square, flash
            // the screen; otherwise, create and send an action to
            // the game
            if (p == null) {
                //surfaceView.flash(Color.RED, 50);
            } else {
                HiveSelectedPieceAction action = new HiveSelectedPieceAction(this, xStart, yStart);
                game.sendAction(action);
                surfaceView.invalidate();
            }
        } else {
            xEnd = (int) event.getX();
            yEnd = (int) event.getY();

            Point p = surfaceView.mapPixelToSquare(xStart, yStart);

            HiveMoveAction action = new HiveMoveAction(this, xStart, yStart, xEnd, yEnd);
            game.sendAction(action);
            surfaceView.invalidate();
        }

        // register that we have handled the event
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


}

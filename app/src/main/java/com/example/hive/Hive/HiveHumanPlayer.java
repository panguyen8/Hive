package com.example.hive.Hive;

import android.view.MotionEvent;
import android.view.View;

import com.example.hive.R;
import com.example.hive.game.GameHumanPlayer;
import com.example.hive.game.GameMainActivity;
import com.example.hive.game.infoMessage.GameInfo;
import com.example.hive.game.infoMessage.IllegalMoveInfo;
import com.example.hive.game.utilities.Logger;

public class HiveHumanPlayer extends GameHumanPlayer implements View.OnTouchListener{

    private final int QUEEN = 1;
    private final int GRASSHOPPER = 2;
    private final int SPIDER = 3;
    private final int BEETLE = 4;
    private final int ANT = 5;

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
        HiveButtonAction action = new HiveButtonAction(this, 0);
        switch(v.getId()) {
            case R.id.Queen:
                action = new HiveButtonAction(this,QUEEN);
                break;
            case R.id.Spider:
                action = new HiveButtonAction(this, SPIDER);
                break;
            case R.id.Grasshopper:
                action = new HiveButtonAction(this, GRASSHOPPER);
                break;
            case R.id.Ant:
                action = new HiveButtonAction(this, ANT);
                break;
            case R.id.Beetle:
                action = new HiveButtonAction(this, BEETLE);
                break;
        }
        game.sendAction(action);
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
        if (surfaceView == null) {
            return;
        }

        if (info instanceof IllegalMoveInfo) {
        }
        else if (!(info instanceof HiveGameState))
            return;
        else {
            surfaceView.setState((HiveGameState)info);
            surfaceView.invalidate();
        }

    }//receiveInfo

    public boolean onTouch(View v, MotionEvent event) {
        double x = event.getX();
        double y = event.getY();
        int divider = 0;
        divider = (int)((y/66));
        yStart =(int) y + 33*divider;
        yStart = yStart/100;

        if((divider%2) == 0) {
            xStart = (int) x;
            xStart = xStart/100;
        } else {
            x = x - 50;
            xStart = (int) x;
            xStart = xStart/100;
        }


        Logger.log("onTouch",xStart + " " + yStart);
        if (moveReady = false) {
            // ignore if not an "up" event
            if (event.getAction() != MotionEvent.ACTION_UP) return true;
            // get the x and y coordinates of the touch-location;
            // convert them to square coordinates (where both
            // values are in the range 0..2)
            xStart = (int) event.getX();
            yStart = (int) event.getY();

            xStart = xStart/100;
            yStart = yStart/100;

            //Point p = surfaceView.mapPixelToSquare(xStart, yStart);

            HiveSelectedPieceAction action = new HiveSelectedPieceAction(this, xStart, yStart);
            game.sendAction(action);
            surfaceView.invalidate();

            /*
            if (p == null) {
                //surfaceView.flash(Color.RED, 50);
            } else {
                HiveSelectedPieceAction action = new HiveSelectedPieceAction(this, xStart, yStart);
                game.sendAction(action);
                surfaceView.invalidate();
            }
             */

        } else {
            xEnd = (int) event.getX();
            yEnd = (int) event.getY();

            xEnd = xEnd/100;
            yEnd = yEnd/100;

            //Point p = surfaceView.mapPixelToSquare(xStart, yStart);

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
        surfaceView = (HiveView) activity.findViewById(R.id.hiveSurfaceView);

        surfaceView.setOnTouchListener(this);

    }//setAsGui


}

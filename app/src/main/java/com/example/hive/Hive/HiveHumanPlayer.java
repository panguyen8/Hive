package com.example.hive.Hive;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.hive.R;
import com.example.hive.game.GameHumanPlayer;
import com.example.hive.game.GameMainActivity;
import com.example.hive.game.infoMessage.GameInfo;
import com.example.hive.game.infoMessage.IllegalMoveInfo;
import com.example.hive.game.utilities.Logger;

public class HiveHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener{

    private final int QUEEN = 1;
    private final int GRASSHOPPER = 2;
    private final int SPIDER = 3;
    private final int BEETLE = 4;
    private final int ANT = 5;

    private int pieceChosen = 0;

    protected boolean moveReady = false;
    protected boolean piecePlacement = false;
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
            case R.id.QueenButton:
                action = new HiveButtonAction(this,QUEEN);
                pieceChosen = 1;
                break;
            case R.id.SpiderButton:
                action = new HiveButtonAction(this, SPIDER);
                pieceChosen = 3;
                break;
            case R.id.GrasshopperButton:
                action = new HiveButtonAction(this, GRASSHOPPER);
                pieceChosen = 2;
                break;
            case R.id.AntButton:
                action = new HiveButtonAction(this, ANT);
                pieceChosen = 5;
                break;
            case R.id.BeetleButton:
                action = new HiveButtonAction(this, BEETLE);
                pieceChosen = 4;
                break;
        }
        moveReady = true;
        piecePlacement = true;
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
        int xCoord;
        int yCoord;
        int divider = 0;
        divider = (int)((y/66));
        yCoord =(int) y + 33*divider;
        yCoord = yStart/100;

        if((divider%2) == 0) {
            xCoord = (int) x;
            xCoord = xStart/100;
        } else {
            x = x - 50;
            xCoord = (int) x;
            xCoord = xStart/100;
        }


        Logger.log("onTouch",xStart + " " + yStart);
        if (moveReady = false) {
            // ignore if not an "up" event
            if (event.getAction() != MotionEvent.ACTION_UP) return true;
            // get the x and y coordinates of the touch-location;
            // convert them to square coordinates (where both
            // values are in the range 0..2)

            xStart = xCoord;
            yStart = yCoord;

            HiveSelectedPieceAction action = new HiveSelectedPieceAction(this, xStart, yStart);
            game.sendAction(action);
            surfaceView.invalidate();

            moveReady = true;
        } else if (piecePlacement == true) {
            xEnd = xCoord;
            yEnd = yCoord;

            HivePlacePieceAction action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.EMPTY);

            if (pieceChosen == 1) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WBEE);
            } else if (pieceChosen == 2) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WGHOPPER);
            } else if (pieceChosen == 3) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WSPIDER);
            } else if (pieceChosen == 4) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WBEETLE);
            } else if (pieceChosen == 5) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WANT);
            }
            game.sendAction(action);
        
        } else {
                xEnd = xCoord;
                yEnd = yCoord;
                //Point p = surfaceView.mapPixelToSquare(xStart, yStart);
                HiveMoveAction action = new HiveMoveAction(this, xStart, yStart, xEnd, yEnd);
                game.sendAction(action);
                surfaceView.invalidate();

                moveReady = false;
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
        ImageButton queenButton = (ImageButton) activity.findViewById(R.id.QueenButton);
        ImageButton beetleButton = (ImageButton) activity.findViewById(R.id.BeetleButton);
        ImageButton spiderButton = (ImageButton) activity.findViewById(R.id.SpiderButton);
        ImageButton antButton = (ImageButton) activity.findViewById(R.id.AntButton);
        ImageButton grasshopperButton = (ImageButton) activity.findViewById(R.id.AntButton);

        queenButton.setOnClickListener(this);
        beetleButton.setOnClickListener(this);
        spiderButton.setOnClickListener(this);
        antButton.setOnClickListener(this);
        grasshopperButton.setOnClickListener(this);

        surfaceView.setOnTouchListener(this);

    }//setAsGui


}

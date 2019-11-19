package com.example.hive.Hive;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.hive.R;
import com.example.hive.game.GameHumanPlayer;
import com.example.hive.game.GameMainActivity;
import com.example.hive.game.infoMessage.GameInfo;
import com.example.hive.game.infoMessage.IllegalMoveInfo;
import com.example.hive.game.utilities.Logger;

import org.w3c.dom.Text;

public class HiveHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener{

    EditText theText;

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

    protected int xCoord;
    protected int yCoord;


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
                action = new HiveButtonAction(this, QUEEN);
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
            case R.id.ClearInfo:
                theText.setText("");
                break;
        }
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
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return true;
        }

        double x = event.getX();
        double y = event.getY();

        int divider = 0;
        divider = (int)(y/66);
        yCoord =(int) y + 33*divider;

        yCoord = yCoord/100;

        if((divider%2) == 0) {
            xCoord = (int) x;
            xCoord = xCoord/100;
        } else {
            x = x - 50;
            xCoord = (int) x;
            xCoord = xCoord/100;
        }

        //Logger.log("onTouch","Coord" + xCoord + " " + yCoord + " " +divider);
        if (piecePlacement) {
            xEnd = xCoord;
            yEnd = yCoord;

            String piece = new String();

            HivePlacePieceAction action;

            if (pieceChosen == 1) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WBEE);
                piece = "Bee";
            } else if (pieceChosen == 2) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WGHOPPER);
                piece = "Grasshopper";
            } else if (pieceChosen == 3) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WSPIDER);
                piece = "Spider";
            } else if (pieceChosen == 4) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WBEETLE);
                piece = "Beetle";
            } else if (pieceChosen == 5) {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.WANT);
                piece = "Ant";
            } else {
                action = new HivePlacePieceAction(this, xEnd, yEnd, HiveGameState.piece.EMPTY);
            }

            game.sendAction(action);

            theText.append(piece + " has been placed at (" +xEnd+ ", " +yEnd+ ")\n");
            piecePlacement = false;
            return true;

        } else if (!moveReady) {

            xStart = xCoord;
            yStart = yCoord;
            Logger.log("onTouch","Start: " + xStart + " " + yStart);

            HiveSelectedPieceAction action = new HiveSelectedPieceAction(this, xStart, yStart);
            game.sendAction(action);
            surfaceView.invalidate();

            moveReady = true;
        } else  if (moveReady) {
                xEnd = xCoord;
                yEnd = yCoord;
                HiveMoveAction action = new HiveMoveAction(this, xStart, yStart, xEnd, yEnd);
                game.sendAction(action);

                Logger.log("onTouch", "End: " + xEnd + " " + yEnd);

                surfaceView.invalidate();

                theText.append("Piece has been moved from (" + xStart + ", " + yStart +") to (" +xEnd+ ", " +yEnd+ ")\n");
                moveReady = false;
        }

        // register that we have handled the event
        return true;

    }

    //textView

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

        theText = (EditText) activity.findViewById(R.id.editText);
        theText.setText("");

        //Initialize the widget reference member variables
        surfaceView = (HiveView) activity.findViewById(R.id.hiveSurfaceView);
        ImageButton queenButton = (ImageButton) activity.findViewById(R.id.QueenButton);
        ImageButton beetleButton = (ImageButton) activity.findViewById(R.id.BeetleButton);
        ImageButton spiderButton = (ImageButton) activity.findViewById(R.id.SpiderButton);
        ImageButton antButton = (ImageButton) activity.findViewById(R.id.AntButton);
        ImageButton grasshopperButton = (ImageButton) activity.findViewById(R.id.AntButton);
        Button clearInfo = (Button) activity.findViewById(R.id.ClearInfo);

        clearInfo.setOnClickListener(this);
        queenButton.setOnClickListener(this);
        beetleButton.setOnClickListener(this);
        spiderButton.setOnClickListener(this);
        antButton.setOnClickListener(this);
        grasshopperButton.setOnClickListener(this);

        surfaceView.setOnTouchListener(this);

    }//setAsGui


}

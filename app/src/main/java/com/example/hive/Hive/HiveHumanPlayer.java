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

public class HiveHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener{

    EditText theText;

    private String pieceText = "";
    private HiveGameState.piece piecePlaced = HiveGameState.piece.EMPTY;

    protected boolean moveReady = false;

    protected boolean piecePlacement = false;

    protected int xStart = 0;
    protected int yStart = 0;
    protected int xEnd = 0;
    protected int yEnd = 0;

    protected int xCoord;
    protected int yCoord;

    HiveGameState hgs;


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
        HiveButtonAction action = new HiveButtonAction(this, piecePlaced);
        switch(v.getId()) {
            case R.id.QueenButton:
                piecePlaced = HiveGameState.piece.WBEE;
                pieceText = "WHITE BEE";
                action = new HiveButtonAction(this, piecePlaced);
                break;
            case R.id.SpiderButton:
                piecePlaced = HiveGameState.piece.WSPIDER;
                pieceText = "WHITE SPIDER";
                action = new HiveButtonAction(this, piecePlaced);
                break;
            case R.id.GrasshopperButton:
                piecePlaced = HiveGameState.piece.WGHOPPER;
                pieceText = "WHITE GRASSHOPPER";
                action = new HiveButtonAction(this, piecePlaced);
                break;
            case R.id.AntButton:
                piecePlaced = HiveGameState.piece.WANT;
                pieceText = "WHITE ANT";
                action = new HiveButtonAction(this, piecePlaced);
                break;
            case R.id.BeetleButton:
                piecePlaced = HiveGameState.piece.WBEETLE;
                pieceText = "WHITE BEETLE";
                action = new HiveButtonAction(this, piecePlaced);
                break;
            case R.id.ClearInfo:
                theText.setText("");
                break;
            case R.id.Reset:
                HiveResetBoardAction action2 = new HiveResetBoardAction(this, false);
                game.sendAction(action2);
                moveReady = false;
                piecePlacement = true;
                break;
        }
        if(piecePlacement){
            piecePlacement = false;
            HiveResetBoardAction action3 = new HiveResetBoardAction(this, true);
            game.sendAction(action3);
            surfaceView.invalidate();
        }
        else{
            piecePlacement = true;
            game.sendAction(action);
            surfaceView.invalidate();
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
        if (surfaceView == null) {
            return;
        }
        if (info instanceof IllegalMoveInfo) {
        }
        else if (!(info instanceof HiveGameState))
            return;
        else {
            surfaceView.setState((HiveGameState)info);
            hgs = new HiveGameState((HiveGameState) info);
            Logger.log("turnCount", Integer.toString(hgs.getTurnCount()));
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

        if (piecePlacement) {
            xEnd = xCoord;
            yEnd = yCoord;

            if(hgs.makePlace(xEnd, yEnd, piecePlaced)) {

                HivePlacePieceAction action = new HivePlacePieceAction(this, xEnd, yEnd, piecePlaced);

                theText.append(pieceText + " has been placed at (" + xEnd + ", " + yEnd + ")\n");
                game.sendAction(action);

                piecePlacement = false;


            } else {
                piecePlacement = true;
            }
            return true;

        } else if (!moveReady) {

            xStart = xCoord;
            yStart = yCoord;
            Logger.log("onTouch","Start: " + xStart + " " + yStart);

            if(hgs.board[xStart][yStart] != HiveGameState.piece.EMPTY || hgs.board[xStart][yStart] != HiveGameState.piece.TARGET) {
                surfaceView.setSelectedCoords(xStart, yStart);
            }

            HiveSelectedPieceAction action = new HiveSelectedPieceAction(this, xStart, yStart);
            game.sendAction(action);
            surfaceView.invalidate();

            moveReady = true;
        } else  if (moveReady) {
                xEnd = xCoord;
                yEnd = yCoord;
                if(xStart == xEnd && yStart == yEnd){
                    Logger.log("onTouch","Deselect: " + xEnd + " " + yEnd);
                }
                else {
                    HiveMoveAction action = new HiveMoveAction(this, xStart, yStart, xEnd, yEnd);
                    game.sendAction(action);

                    Logger.log("onTouch", "End: " + xEnd + " " + yEnd);

                    theText.append("Piece has been moved from (" + xStart + ", " + yStart + ") to (" + xEnd + ", " + yEnd + ")\n");
                }

                surfaceView.deselectPiece();
                surfaceView.invalidate();
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
        ImageButton grasshopperButton = (ImageButton) activity.findViewById(R.id.GrasshopperButton);
        Button clearInfo = (Button) activity.findViewById(R.id.ClearInfo);
        Button quit = (Button) activity.findViewById(R.id.Reset);

        quit.setOnClickListener(this);
        clearInfo.setOnClickListener(this);
        queenButton.setOnClickListener(this);
        beetleButton.setOnClickListener(this);
        spiderButton.setOnClickListener(this);
        antButton.setOnClickListener(this);
        grasshopperButton.setOnClickListener(this);

        surfaceView.setOnTouchListener(this);

    }//setAsGui

}

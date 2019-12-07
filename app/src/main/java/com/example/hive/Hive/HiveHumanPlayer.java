package com.example.hive.Hive;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hive.R;
import com.example.hive.game.GameHumanPlayer;
import com.example.hive.game.GameMainActivity;
import com.example.hive.game.infoMessage.GameInfo;
import com.example.hive.game.infoMessage.IllegalMoveInfo;
import com.example.hive.game.utilities.Logger;

/**
 * Allows player to send actions to local game.
 *
 * @author Phuocan Nguyen
 * @author Marc Hilderbrand
 * @author Samuel Nguyen
 */
public class HiveHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener{

    EditText theText;
    TextView turnText;

    TextView beeCount;
    TextView spiderCount;
    TextView gHopperCount;
    TextView antCount;
    TextView beetleCount;
    TextView bBeeCount;
    TextView bBeetleCount;
    TextView bAntCount;
    TextView bSpiderCount;
    TextView bGHopperCount;

    ImageButton queenButton;
    ImageButton beetleButton;
    ImageButton spiderButton;
    ImageButton antButton;
    ImageButton grasshopperButton;

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

        boolean placeButton = true;

        surfaceView.deselectPiece();
        moveReady = false;

            switch(v.getId()) {
                case R.id.QueenButton:
                   if (hgs.getTurn() == 0) {
                        createPieceText(HiveGameState.piece.WBEE);
                   }
                   else{
                       createPieceText(HiveGameState.piece.BBEE);
                   }
                   action = new HiveButtonAction(this, piecePlaced);
                   break;
                case R.id.SpiderButton:
                    if (hgs.getTurn() == 0) {
                        createPieceText(forcePlaceBeeText(HiveGameState.piece.WSPIDER));
                    }
                    else{
                        createPieceText(forcePlaceBeeText(HiveGameState.piece.BSPIDER));
                    }
                    action = new HiveButtonAction(this, piecePlaced);
                    break;
                case R.id.GrasshopperButton:
                    if (hgs.getTurn() == 0) {
                        createPieceText(forcePlaceBeeText(HiveGameState.piece.WGHOPPER));
                    }
                    else{
                        createPieceText(forcePlaceBeeText(HiveGameState.piece.BGHOPPER));
                    }
                    action = new HiveButtonAction(this, piecePlaced);
                    break;
                case R.id.AntButton:
                    if (hgs.getTurn() == 0)
                    {
                        createPieceText(forcePlaceBeeText(HiveGameState.piece.WANT));
                    }
                    else{
                        createPieceText(forcePlaceBeeText(HiveGameState.piece.BANT));
                    }
                    action = new HiveButtonAction(this, piecePlaced);
                    break;
                case R.id.BeetleButton:
                    if (hgs.getTurn() == 0) {
                        createPieceText(forcePlaceBeeText(HiveGameState.piece.WBEETLE));
                    }
                    else{
                        createPieceText(forcePlaceBeeText(HiveGameState.piece.BBEETLE));
                    }
                    action = new HiveButtonAction(this, piecePlaced);
                    break;
            }
            switch (v.getId()) {
                case R.id.ClearInfo:
                    theText.setText("");
                    placeButton = false;
                    break;
                case R.id.Reset:
                    HiveResetBoardAction action2 = new HiveResetBoardAction(this, false);
                    game.sendAction(action2);

                    //reset grayed out buttons
                    queenButton.setImageAlpha(255);
                    spiderButton.setImageAlpha(255);
                    grasshopperButton.setImageAlpha(255);
                    antButton.setImageAlpha(255);
                    beetleButton.setImageAlpha(255);

                    placeButton = false;
                    moveReady = false;
                    piecePlacement = true;
                    break;
                case R.id.helpButton:
                    theText.append("To win, the opponent's bee must be surrounded.\n" +
                            "To place a piece, tap its button, then tap a highlighted hexagon.\n" +
                            "To move a piece, tap the piece on the board, then tap a highlighted hexagon.\n" +
                            "Remember to place the bee within the first four moves, or it will be placed for you! \n" +
                            "To deselect a piece, just tap it again! Have fun! \n");

                    placeButton = false;
                    break;
            }
            if(piecePlacement){
             piecePlacement = false;
                HiveResetBoardAction action3 = new HiveResetBoardAction(this, true);
                game.sendAction(action3);
             surfaceView.invalidate();
            }
            else {
                if(placeButton) {
                    //reset targets if there are no pieces available to place
                    if (hgs.checkNumPieces(piecePlaced) != 0) {
                    piecePlacement = true;
                        game.sendAction(action);
                        surfaceView.invalidate();
                    }
                }
                else {
                        HiveResetBoardAction action3 = new HiveResetBoardAction(this, true);
                        game.sendAction(action3);
                }
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
        // Error checking
        if (surfaceView == null) {
            return;
        }
        if (info instanceof IllegalMoveInfo) {
        }
        else if (!(info instanceof HiveGameState)) {
            return;
        }
        else {
            //update HiveGameState in the gui and HiveHumanPlayer
            surfaceView.setState((HiveGameState)info);
            hgs = new HiveGameState((HiveGameState) info);

            //Update unplaced piece count
            beeCount.setText("   White Bee: " + hgs.checkNumPieces(HiveGameState.piece.WBEE));
            spiderCount.setText("   White Spider: " + hgs.checkNumPieces(HiveGameState.piece.WSPIDER));
            gHopperCount.setText("   White Grasshopper: " + hgs.checkNumPieces(HiveGameState.piece.WGHOPPER));
            antCount.setText("   White Ant: " + hgs.checkNumPieces(HiveGameState.piece.WANT));
            beetleCount.setText("   White Beetle: " + hgs.checkNumPieces(HiveGameState.piece.WBEETLE));

            bBeeCount.setText("   Black Bee: " + hgs.checkNumPieces(HiveGameState.piece.BBEE));
            bSpiderCount.setText("   Black Spider: " + hgs.checkNumPieces(HiveGameState.piece.BSPIDER));
            bGHopperCount.setText("   Black Grasshopper: " + hgs.checkNumPieces(HiveGameState.piece.BGHOPPER));
            bAntCount.setText("   Black Ant: " + hgs.checkNumPieces(HiveGameState.piece.BANT));
            bBeetleCount.setText("   Black Beetle: " + hgs.checkNumPieces(HiveGameState.piece.BBEETLE));

            if(hgs.getTurnCount() > 7 && hgs.bugList.contains(HiveGameState.piece.WBEE)){
                spiderButton.setImageAlpha(100);
                grasshopperButton.setImageAlpha(100);
                antButton.setImageAlpha(100);
                beetleButton.setImageAlpha(100);
            }
            else{
                spiderButton.setImageAlpha(255);
                grasshopperButton.setImageAlpha(255);
                antButton.setImageAlpha(255);
                beetleButton.setImageAlpha(255);
            }
            if(hgs.checkNumPieces(HiveGameState.piece.WBEE) == 0){
                queenButton.setImageAlpha(100);
            }
            if(hgs.checkNumPieces(HiveGameState.piece.WSPIDER) == 0){
                spiderButton.setImageAlpha(100);
            }
            if(hgs.checkNumPieces(HiveGameState.piece.WGHOPPER) == 0){
                grasshopperButton.setImageAlpha(100);
            }
            if(hgs.checkNumPieces(HiveGameState.piece.WANT) == 0){
                antButton.setImageAlpha(100);
            }
            if(hgs.checkNumPieces(HiveGameState.piece.WBEETLE) == 0){
                beetleButton.setImageAlpha(100);
            }

            Logger.log("turnCount", Integer.toString(hgs.getTurnCount()));
            turnText.setText("Turns: " + hgs.getTurnCount());
            surfaceView.invalidate();
        }

    }//receiveInfo

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return true;
        }
        double x = event.getX();
        double y = event.getY();

        int divider = (int)(y/66);
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

        if(hgs.getTurn() == 0){
            return movementHelper(0);
        }
        else{
            return movementHelper(1);
        }
    }

    public boolean movementHelper(int player) {
        if (piecePlacement) {
            xEnd = xCoord;
            yEnd = yCoord;

            if (hgs.makePlace(xEnd, yEnd, piecePlaced)) {

                HivePlacePieceAction action = new HivePlacePieceAction(this, xEnd, yEnd, piecePlaced);

                theText.append(pieceText + " has been placed at (" + xEnd + ", " + yEnd + ")\n");
                game.sendAction(action);

                piecePlacement = false;

            } else {
                piecePlacement = true;
                theText.append("Player is attempting to place piece illegally!\n");
            }
            return true;

        } else if (!moveReady) {

            xStart = xCoord;
            yStart = yCoord;
            Logger.log("onTouch", "Start: " + xStart + " " + yStart);

            if (player == 0) {
                if (hgs.checkIfWhite(xStart, yStart)) {
                    surfaceView.setSelectedCoords(xStart, yStart);
                    HiveSelectedPieceAction action = new HiveSelectedPieceAction(this, xStart, yStart);
                    game.sendAction(action);
                    moveReady = true;
                    surfaceView.invalidate();
                }
                else{
                    moveReady = false;
                }
            }
            else{
                if(hgs.checkIfBlack(xStart, yStart)) {
                    surfaceView.setSelectedCoords(xStart, yStart);
                    HiveSelectedPieceAction action = new HiveSelectedPieceAction(this, xStart, yStart);
                    game.sendAction(action);
                    moveReady = true;
                    surfaceView.invalidate();
                }
                else{
                    moveReady = false;
                }
            }
        }
        else if (moveReady) {
            xEnd = xCoord;
            yEnd = yCoord;

            //if the same spot is not a target piece, then deselect
            //else move normally
            if (hgs.board[xEnd][yEnd] != HiveGameState.piece.TARGET) {
                Logger.log("onTouch", "Deselect: " + xEnd + " " + yEnd);
                HiveResetBoardAction action = new HiveResetBoardAction(this, true);
                game.sendAction(action);
            }
            else {
                HiveMoveAction action = new HiveMoveAction(this, xStart, yStart, xEnd, yEnd);
                if (hgs.makeMove(action.endRow, action.endCol, hgs.board[action.endRow][action.endCol])
                    && hgs.checkIslands(action.startRow, action.startCol, action.endRow, action.endCol)) {

                    createPieceText(hgs.board[xStart][yStart]);
                    game.sendAction(action);
                    moveReady = true;
                    theText.append(pieceText + " has been moved from (" + xStart + ", " + yStart +
                            ") to (" + xEnd + ", " + yEnd + ")\n");
                }
                else {
                    HiveResetBoardAction action3 = new HiveResetBoardAction(this, true);
                    game.sendAction(action3);
                    moveReady = false;
                    theText.append("Player is attempting to move piece illegally!\n");
                }
                Logger.log("onTouch", "End: " + xEnd + " " + yEnd);
            }

            //deselect piece highlight regardless of whether there was movement or not
            surfaceView.deselectPiece();
            surfaceView.invalidate();
            moveReady = false;
        }
        return true;
    }

    /**
     * turns piece type into string
     * @param pieceType piece to be turned into text
     */
    public void createPieceText(HiveGameState.piece pieceType){
        switch(pieceType) {
            case WBEE:
                piecePlaced = HiveGameState.piece.WBEE;
                pieceText = "WHITE BEE";
                break;
            case BBEE:
                piecePlaced = HiveGameState.piece.BBEE;
                pieceText = "BLACK BEE";
                break;
            case WSPIDER:
                piecePlaced = HiveGameState.piece.WSPIDER;
                pieceText = "WHITE SPIDER";
                break;
            case BSPIDER:
                piecePlaced = HiveGameState.piece.BSPIDER;
                pieceText = "BLACK SPIDER";
                break;
            case WGHOPPER:
                piecePlaced = HiveGameState.piece.WGHOPPER;
                pieceText = "WHITE GRASSHOPPER";
                break;
            case BGHOPPER:
                piecePlaced = HiveGameState.piece.BGHOPPER;
                pieceText = "BLACK GRASSHOPPER";
                break;
            case WANT:
                piecePlaced = HiveGameState.piece.WANT;
                pieceText = "WHITE ANT";
                break;
            case BANT:
                piecePlaced = HiveGameState.piece.BANT;
                pieceText = "BLACK ANT";
                break;
            case WBEETLE:
                piecePlaced = HiveGameState.piece.WBEETLE;
                pieceText = "WHITE BEETLE";
                break;
            case BBEETLE:
                piecePlaced = HiveGameState.piece.BBEETLE;
                pieceText = "BLACK BEETLE";
                break;
        }
    }

    /**
     * sets text to appropriate player's bee if after turn 7
     */
    public HiveGameState.piece forcePlaceBeeText(HiveGameState.piece piece){
        if(hgs.getTurnCount() > 7){
            if (hgs.getTurn() == 0 && hgs.checkNumPieces(HiveGameState.piece.WBEE) > 0) {
                return HiveGameState.piece.WBEE;
            }
            else {
                if (hgs.getTurn() == 1 && hgs.checkNumPieces(HiveGameState.piece.BBEE) > 0){
                    return HiveGameState.piece.BBEE;
                }
            }
        }
        return piece;
    }

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui (GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.hive_layout);

        theText = (EditText) activity.findViewById(R.id.editText);
        theText.setText("");

        turnText = activity.findViewById(R.id.TurnCount);
        beeCount = activity.findViewById(R.id.BeeCount);
        spiderCount = activity.findViewById(R.id.SpiderCount);
        gHopperCount = activity.findViewById(R.id.GHopperCount);
        antCount = activity.findViewById(R.id.AntCount);
        beetleCount = activity.findViewById(R.id.BeetleCount);
        bBeeCount = activity.findViewById(R.id.BlackBeeCount);
        bAntCount = activity.findViewById(R.id.BlackAntCount);
        bBeetleCount = activity.findViewById(R.id.BlackBeetleCount);
        bGHopperCount = activity.findViewById(R.id.BlackGHopperCount);
        bSpiderCount = activity.findViewById(R.id.BlackSpiderCount);

        //Initialize the widget reference member variables
        surfaceView = (HiveView) activity.findViewById(R.id.hiveSurfaceView);
        queenButton = (ImageButton) activity.findViewById(R.id.QueenButton);
        beetleButton = (ImageButton) activity.findViewById(R.id.BeetleButton);
        spiderButton = (ImageButton) activity.findViewById(R.id.SpiderButton);
        antButton = (ImageButton) activity.findViewById(R.id.AntButton);
        grasshopperButton = (ImageButton) activity.findViewById(R.id.GrasshopperButton);
        Button clearInfo = (Button) activity.findViewById(R.id.ClearInfo);
        Button quit = (Button) activity.findViewById(R.id.Reset);
        Button help = (Button) activity.findViewById(R.id.helpButton);

        theText.append("Welcome to Hive! \n" +
                "To win, the opponent's bee must be surrounded.\n" +
                "To place a piece, tap its button, then tap a highlighted hexagon.\n" +
                "To move a piece, tap the piece on the board, then tap a highlighted hexagon.\n" +
                "Remember to place the bee within the first four moves, or it will be placed for you! \n" +
                "To deselect a piece, just tap it again! Have fun! \n");

        quit.setOnClickListener(this);
        clearInfo.setOnClickListener(this);
        help.setOnClickListener(this);
        queenButton.setOnClickListener(this);
        beetleButton.setOnClickListener(this);
        spiderButton.setOnClickListener(this);
        antButton.setOnClickListener(this);
        grasshopperButton.setOnClickListener(this);

        surfaceView.setOnTouchListener(this);

    }//setAsGui

}

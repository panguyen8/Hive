/**
 *@author Marc Hilderbrand
 *@author Erik Liu
 *@author Phuocan Nguyen
 *@author Samuel Nguyen
 *@author Stephen Nguyen
 *
 *@version 10/22/19
 */

package com.example.hive.Hive;

//Comment by Stephen
//I assume this is used in network play, delete if it's unnecessary
import android.net.wifi.p2p.WifiP2pGroup;

import com.example.hive.game.infoMessage.GameState;

import java.util.ArrayList;

public class HiveGameState extends GameState {
    private final int BLACK_TURN = 0;
    private final int WHITE_TURN = 1;

    //the declaration of the board
    public piece[][] board = new piece[20][20];

    //int variable to tell whose turn it is
    //If 1, white moves, if 0, black moves
    //Removed static for now, not sure if it's needed
    //Let me (Stephen) know if it is
    private int turn = 1;  // Edit by Samuel Nguyen

    //Represents how many total pieces each player has
    private int player0Pieces;
    private int player1Pieces;

    public ArrayList<piece> bugList = new ArrayList<>();
    enum piece {
        BBEE, BSPIDER, BANT, BGHOPPER, BBEETLE, WBEE, WSPIDER, WANT, WGHOPPER, WBEETLE, EMPTY;
    }

    //Basic constructor
    public HiveGameState() {

        //1 BBEE, 2 BSPIDERS, 3 BANT, 3 BGHOPPER, 2,BBEETLE
        bugList.add(piece.BBEE);
        bugList.add(piece.BSPIDER);
        bugList.add(piece.BSPIDER);
        bugList.add(piece.BANT);
        bugList.add(piece.BANT);
        bugList.add(piece.BANT);
        bugList.add(piece.BGHOPPER);
        bugList.add(piece.BGHOPPER);
        bugList.add(piece.BGHOPPER);
        bugList.add(piece.BBEETLE);
        bugList.add(piece.BBEETLE);

        //1 BBEE, 2 WSPIDERS, 3 WANT, 3 WGHOPPER, 2,WBEETLE
        bugList.add(piece.WBEE);
        bugList.add(piece.WSPIDER);
        bugList.add(piece.WSPIDER);
        bugList.add(piece.WANT);
        bugList.add(piece.WANT);
        bugList.add(piece.WANT);
        bugList.add(piece.WGHOPPER);
        bugList.add(piece.WGHOPPER);
        bugList.add(piece.WGHOPPER);
        bugList.add(piece.WBEETLE);
        bugList.add(piece.WBEETLE);

        this.turn = WHITE_TURN; // White goes first?
        this.player0Pieces = 11;
        this.player1Pieces = 11;

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                board[i][j] = HiveGameState.piece.WBEE;
            }
        }
    }

    //Copy constructor (Stephen)
    // Some edits by Samuel Nguyen
    public HiveGameState(HiveGameState hgs) {
        this.turn = hgs.turn;

        //Copies each board index/cell
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                this.board[i][j] = hgs.board[i][j];
            }
        }
        this.bugList = hgs.bugList;
    }

    /**
     * Gets the id of whoever's move it is
     * @return id of player to move
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Sets the player to move
     * @param id: id of player that is being set to move
     */
    public void setTurn(int id) {
        this.turn = id;
    }

    public piece[][] getBoard() {
        return this.board;
    }

    public ArrayList<piece> getBugList() { return bugList;}

    public HiveGameState.piece getPiece(int x, int y) {
        return board[x][y];
    }

    public void setPiece(int x, int y, HiveGameState.piece piece) {
        board[x][y] = piece;
    }
}

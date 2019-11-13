package com.example.hive.Hive;

import com.example.hive.game.infoMessage.GameState;

import java.util.ArrayList;

public class HiveState extends GameState {
    private static final long serialVersionUID = 7552321013488624386L;

    private final int BLACK_TURN = 0;
    private final int WHITE_TURN = 1;


    //the declaration of the board
    private HiveState.piece[][] board = new HiveState.piece[20][20];
    private int turn = 1;

    //Represents how many total pieces each player has
    private int player0Pieces;
    private int player1Pieces;

    public ArrayList<HiveGameState.piece> bugList = new ArrayList<>();
    enum piece {
        BBEE, BSPIDER, BANT, BGHOPPER, BBEETLE, WBEE, WSPIDER, WANT, WGHOPPER, WBEETLE;
    }

    public HiveState() {

        //1 BBEE, 2 BSPIDERS, 3 BANT, 3 BGHOPPER, 2,BBEETLE
        bugList.add(HiveGameState.piece.BBEE);
        bugList.add(HiveGameState.piece.BSPIDER);
        bugList.add(HiveGameState.piece.BSPIDER);
        bugList.add(HiveGameState.piece.BANT);
        bugList.add(HiveGameState.piece.BANT);
        bugList.add(HiveGameState.piece.BANT);
        bugList.add(HiveGameState.piece.BGHOPPER);
        bugList.add(HiveGameState.piece.BGHOPPER);
        bugList.add(HiveGameState.piece.BGHOPPER);
        bugList.add(HiveGameState.piece.BBEETLE);
        bugList.add(HiveGameState.piece.BBEETLE);

        //1 BBEE, 2 WSPIDERS, 3 WANT, 3 WGHOPPER, 2,WBEETLE
        bugList.add(HiveGameState.piece.WBEE);
        bugList.add(HiveGameState.piece.WSPIDER);
        bugList.add(HiveGameState.piece.WSPIDER);
        bugList.add(HiveGameState.piece.WANT);
        bugList.add(HiveGameState.piece.WANT);
        bugList.add(HiveGameState.piece.WANT);
        bugList.add(HiveGameState.piece.WGHOPPER);
        bugList.add(HiveGameState.piece.WGHOPPER);
        bugList.add(HiveGameState.piece.WGHOPPER);
        bugList.add(HiveGameState.piece.WBEETLE);
        bugList.add(HiveGameState.piece.WBEETLE);

        this.turn = WHITE_TURN; // White goes first?
        this.player0Pieces = 11;
        this.player1Pieces = 11;
    }

    // Some edits by Samuel Nguyen
    public HiveState(HiveState hgs) {
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

    public HiveState.piece getPiece(int x, int y) {
        return this.board[x][y];
    }

    public void setPiece(int x, int y, HiveState.piece piece) {
        this.board[x][y] = piece;
    }

    public int getPlayerId() {
        return turn;
    }
}

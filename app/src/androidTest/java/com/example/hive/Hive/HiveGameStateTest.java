package com.example.hive.Hive;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HiveGameStateTest {

    // Done by Samuel Nguyen
    @Test
    public void getTurn() {
        HiveGameState hgs = new HiveGameState();
        int testNum = hgs.getTurn();
        assertEquals(1, testNum);
    }

    // Done by Samuel Nguyen
    @Test
    public void setTurn() {
        HiveGameState hgs = new HiveGameState();
        hgs.setTurn(1);
        int testTurn = 1;
        assertEquals(1, testTurn);
    }

    //Marc Hilderbrand
    @Test
    public void canPlace() {
        HiveGameState hgs = new HiveGameState();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                hgs.board[i][j] = HiveGameState.piece.BANT;
            }
        }
        hgs.board[1][1] = HiveGameState.piece.WANT;
        boolean place = hgs.canPlace(1,1);
        assertEquals(true, place);
    }

    //Phuocan Nguyen
    @Test
    public void checkSurround() {
        HiveGameState hgs = new HiveGameState();

        hgs.board[5][5] = HiveGameState.piece.WBEE;

        hgs.makeTarget(5,5);

        //row+1 col
        assertNotEquals(HiveGameState.piece.EMPTY, hgs.board[6][5]);
        //row+1 col+1
        assertNotEquals(HiveGameState.piece.EMPTY, hgs.board[6][6]);
        //row col+1
        assertNotEquals(HiveGameState.piece.EMPTY, hgs.board[5][6]);
        //row col-1
        assertNotEquals(HiveGameState.piece.EMPTY, hgs.board[5][4]);
        //row-1 col
        assertNotEquals(HiveGameState.piece.EMPTY, hgs.board[4][5]);
        //row+1 col-1
        assertNotEquals(HiveGameState.piece.EMPTY, hgs.board[6][4]);
    }

    //Phuocan Nguyen
    @Test
    public void makeTarget() {
        HiveGameState hgs = new HiveGameState();

        hgs.board[5][5] = HiveGameState.piece.WBEE;

        hgs.makeTarget(5,5);

        //row+1 col
        assertEquals(HiveGameState.piece.TARGET, hgs.board[6][5]);
        //row+1 col+1
        assertEquals(HiveGameState.piece.TARGET, hgs.board[6][6]);
        //row col+1
        assertEquals(HiveGameState.piece.TARGET, hgs.board[5][6]);
        //row col-1
        assertEquals(HiveGameState.piece.TARGET, hgs.board[5][4]);
        //row-1 col
        assertEquals(HiveGameState.piece.TARGET, hgs.board[4][5]);
        //row+1 col-1
        assertEquals(HiveGameState.piece.TARGET, hgs.board[6][4]);
    }

    //Stephen Nguyen
    @Test
    public void resetTarget() {
        HiveGameState hgs = new HiveGameState();

        HiveGameState.piece[][] board = hgs.getBoard();
        board[0][0] = HiveGameState.piece.WBEE;

        HiveGameState.piece[][] testBoard = hgs.getBoard();
        for (int i = 0; i < testBoard.length; i++)
        {
            for (int j = 0; j < testBoard[i].length; j++)
            {
                testBoard[i][j] = HiveGameState.piece.EMPTY;
            }
        }
        hgs.resetTarget();
        assertArrayEquals(board, testBoard);
    }

    //Marc Hilderbrand
    @Test
    public void getBoard() {
        HiveGameState hgs = new HiveGameState();
        assertEquals(hgs.board, hgs.getBoard());
    }

    //Marc Hilderbrand
    @Test
    public void getBugList() {
        HiveGameState hgs = new HiveGameState();
        assertEquals(hgs.bugList, hgs.getBugList());
    }

    //Stephen Nguyen
    @Test
    public void getPiece() {
        HiveGameState hgs = new HiveGameState();
        HiveGameState.piece[][] board = hgs.getBoard();
        board[0][0] = HiveGameState.piece.WBEE;
        hgs.getPiece(0, 0);
        assertEquals(HiveGameState.piece.WBEE, board[0][0]);
    }

    @Test
    public void setPiece() {
    }
}
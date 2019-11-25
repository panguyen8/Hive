package com.example.hive.Hive;

import org.junit.Test;

import static org.junit.Assert.*;

public class HiveGameStateTest {

    // Done by Samuel Nguyen
    @Test
    public void getTurn() {
        HiveGameState hgs = new HiveGameState();

    }

    // Done by Samuel Nguyen
    @Test
    public void setTurn() {
        HiveGameState hgs = new HiveGameState();
        hgs.setTurn(1);
        int testTurn = 1;
        assertEquals(1, testTurn);
    }

    @Test
    public void canPlace() {
    }

    @Test
    public void checkSurround() {
    }

    @Test
    public void makeTarget() {
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

    @Test
    public void getBoard() {
    }

    @Test
    public void getBugList() {
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
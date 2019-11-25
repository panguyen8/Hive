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

    @Test
    public void resetTarget() {
    }

    @Test
    public void getBoard() {
    }

    @Test
    public void getBugList() {
    }

    @Test
    public void getPiece() {
    }

    @Test
    public void setPiece() {
    }
}
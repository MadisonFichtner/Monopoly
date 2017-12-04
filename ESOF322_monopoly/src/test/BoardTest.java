package test;

import org.junit.Test;

import src.Board;
import src.Card;
import src.Deed;
import src.Player;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

public class BoardTest {
    @Test
    public void beginTurn() throws Exception {
        Deed[] deeds = new Deed[40];
        Player[] players = new Player[4];
        players[0] = new Player("test", "player", 0);
        Board testBoard = new Board(players, deeds, new ArrayList<Card>(), new ArrayList<Card>());

        testBoard.beginTurn(players[0]);

        assertTrue(testBoard.getCurrent().equals(players[0]));
    }

    @Test
    public void gameOver() throws Exception {
        Deed[] deeds = new Deed[40];                    //Creates empty board, doesn't need to be a real board for this test
        Player[] players = new Player[4];                //Creates array of 4 players
        players[0] = new Player("Madison", "Fichtner", 0);
        players[1] = new Player("Cody", "Stoner", 1);
        players[2] = new Player("Trent", "Baker", 2);
        players[3] = new Player("Logan", "Bonney", 3);
        players[0].setMoney(players[0].getMoney() + 500);                        //Guarantees that we expect players[0] to win with 500 more dollars than anyone else (net worth of 2000 since everyone starts with 1500)

        Player expectedWinner = players[0];                //Sets expected winner to players[0]
        Board board = new Board(players, deeds, new ArrayList<Card>(), new ArrayList<Card>());        //Creates new board given players, and deeds
        Player winner = board.gameOver();                //Sets actual winner to whoever is the result of Board.java's game_over() function

        assertTrue(winner.equals(expectedWinner));
    }


}

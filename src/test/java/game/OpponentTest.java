/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ian
 */
public class OpponentTest {
    
    private static Field[][] boardPositions;
    private static Opponent opponent;
    private static Board board;
    private static ArrayList<Figure> whitePieces;
    private static ArrayList<Figure> blackPieces;
    
    public OpponentTest() {
        try {
            setUpClass();
        } catch(Exception e) {
            
        }
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
        //create empty boardPositions
        boardPositions = new Field[8][8];
        opponent = new Opponent(boardPositions);
        board = new Board(false);
        whitePieces = new ArrayList<Figure>();
        blackPieces = new ArrayList<Figure>();
        opponent.setPlayerPieces(whitePieces);
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        boolean black = true;
        for(int xCoord = 0; xCoord < 8; xCoord++) {
            for(int yCoord = 0; yCoord < 8; yCoord++) {
                boardPositions[xCoord][yCoord] = new Field(board, black, xCoord, yCoord);
                black = !black;
            }
        }
    }
    

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
        //empty boardPositions
        for(int xCoord = 0; xCoord < 8; xCoord++) {
            for(int yCoord = 0; yCoord < 8; yCoord++) {
                boardPositions[xCoord][yCoord].removeFigure();
            }
        }
        //empty pieces lists
        whitePieces.clear();
        blackPieces.clear();
    }

    /// \ref T1_1 \ref T1_2 \ref T1_3
    @org.junit.jupiter.api.Test
    public void testTakeTurn() {
        try {
            setUp();
        
            //opponent will move one piece on its turn
            //with only one piece, it moves that piece
            Figure queen = new Queen (5, 0, true, boardPositions[5][0]);
            boardPositions[5][0].setFigure(queen);
            blackPieces.add(queen);
            Field queenStart = queen.getField();
            opponent.takeTurn(blackPieces, whitePieces);
            Field queenEnd = queen.getField();
            assertTrue(queenStart != queenEnd);
            
            //with more than that, it moves exactly one piece
            opponent.move(queen, 5, 0);
            Figure rook = new Rook(0, 0, true, boardPositions[0][0]);
            boardPositions[0][0].setFigure(rook);
            blackPieces.add(rook);
            queenStart = queen.getField();
            Field rookStart = rook.getField();
            
            opponent.takeTurn(blackPieces, whitePieces);
            queenEnd = queen.getField();
            Field rookEnd = rook.getField();
            assertTrue(queenStart != queenEnd || rookStart != rookEnd);
        
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
    
    /// \ref T1_3
    public void testMove() {
        try {
            setUp();
        
            Figure piece = new Queen(0, 4, true, boardPositions[0][4]);
            boardPositions[0][4].setFigure(piece);
            int startY = piece.getField().getYCord();
            //before move tests
            assertTrue(boardPositions[0][4].getFigure() != null);
            assertTrue(boardPositions[0][5].getFigure() == null);
            opponent.move(piece, 0, 5);
            int endY = piece.getField().getYCord();
            //after move tests
            assertTrue(startY != endY);
            assertTrue(boardPositions[0][4].getFigure() == null);
            assertTrue(boardPositions[0][5].getFigure() != null);
        
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
    
    /// \ref T1_1 \ref T1_2
    public void testChoosePieceToMove() {
        try {
            setUp();
        
            Figure king = new King(0, 3, true, boardPositions[0][3]);
            boardPositions[0][3].setFigure(king);
            Figure pawn = new Pawn(3, 5, true, boardPositions[3][5]);
            boardPositions[3][5].setFigure(pawn);
            blackPieces.add(pawn);
            blackPieces.add(king);
            
            Figure queen = new Queen(0, 6, false, boardPositions[0][6]);
            boardPositions[0][6].setFigure(queen);
            whitePieces.add(queen);
            
            //opponent will get itself out of check
            //king moves out of danger from queen
            assertTrue(opponent.isInCheck(boardPositions));
            opponent.determineMoveablePieces(blackPieces);
            opponent.choosePieceToMove();
            assertFalse(opponent.isInCheck(boardPositions));
            
            //no longer in check, opponent will take player piece if possible
            //bishop takes queen
            opponent.move(king, 1, 2);
            assertTrue(boardPositions[0][6].getFigure() == queen);
            Figure bishop = new Bishop(1, 5, true, boardPositions[1][5]);
            boardPositions[1][5].setFigure(bishop);
            blackPieces.add(bishop);
            opponent.determineMoveablePieces(blackPieces);
            opponent.choosePieceToMove();
            assertTrue(boardPositions[0][6].getFigure() == bishop);
            
            //opponent will attempt to move pieces that are in danger if it cannot take a piece
            //pawn moves out of danger from knight
            Figure knight = new Knight(2, 7, false, boardPositions[2][7]);
            boardPositions[2][7].setFigure(knight);
            whitePieces.add(knight);
            opponent.determineMoveablePieces(blackPieces);
            opponent.choosePieceToMove();
            assertTrue(boardPositions[1][5].getFigure() == null);
            
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
    
    /// \ref T1_1
    public void testCanMove() {
        try {
            setUp();
        
            //queen on empty board should be able to move
            Figure queen = new Queen(1, 1, false, boardPositions[1][1]);
            boardPositions[1][1].setFigure(queen);
            assertTrue(opponent.canMove(queen));
            
            //bishop in corner blocked by queen should not be able to move
            Figure bishop = new Bishop(0, 0, false, boardPositions[0][0]);
            boardPositions[0][0].setFigure(bishop);
            assertFalse(opponent.canMove(bishop));
            
            //once queen moves out of the way, bishop can move
            opponent.move(queen, 1, 2);
            assertTrue(opponent.canMove(bishop));
            
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
    
    /// \ref T1_1 \ref T1_2
    public void testIsInCheck() {
        try {
            setUp();
        
            //king on empty board is not in check
            Figure king = new King(2, 2, true, boardPositions[2][2]);
            boardPositions[2][2].setFigure(king);
            assertFalse(opponent.isInCheck(boardPositions));
            
            //queen in front of king puts it in check
            Figure queen = new Queen(2, 4, false, boardPositions[2][4]);
            boardPositions[2][4].setFigure(queen);
            whitePieces.add(queen);
            assertTrue(opponent.isInCheck(boardPositions));
            
            //king moves to the side, is no longer in check
            opponent.move(king, 3, 2);
            assertFalse(opponent.isInCheck(boardPositions));
            
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
    
    /// \ref T1_1 \ref T1_2
    public void testEscapeCheck() {
        try {
            setUp();
        
            //king in check by queen
            Figure king = new King(2, 2, true, boardPositions[2][2]);
            boardPositions[2][2].setFigure(king);
            Figure queen = new Queen(2, 4, false, boardPositions[2][4]);
            boardPositions[2][4].setFigure(queen);
            blackPieces.add(king);
            whitePieces.add(queen);
            opponent.determineMoveablePieces(blackPieces);
            int startX = king.getField().getXCord();
            int startY = king.getField().getYCord();
            assertTrue(opponent.isInCheck(boardPositions));
            
            opponent.escapeCheck();
            
            int endX = king.getField().getXCord();
            int endY = king.getField().getYCord();
            //king's position changes after escapeCheck, and king is no longer in check
            assertTrue(endX != startX || endY != startY);
            assertFalse(opponent.isInCheck(boardPositions));
            
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
    
    /// \ref T1_1
    public void testCanTakePlayerPiece() {
        try {
            setUp();
        
            //queen on empty board can't take a piece
            Figure queen = new Queen(2, 2, true, boardPositions[2][2]);
            boardPositions[2][2].setFigure(queen);
            assertFalse(opponent.canTakePlayerPiece(queen));
            
            //queen can take pawn in front of it
            Figure pawn = new Pawn(2, 5, false, boardPositions[2][5]);
            boardPositions[2][5].setFigure(pawn);
            
            //queen can take pawn
            assertTrue(opponent.canTakePlayerPiece(queen));
            //after taking pawn, queen's new position is pawn's original position
            assertTrue(boardPositions[2][5].getFigure() == queen);
            assertTrue(queen.getField().getXCord() == 2);
            assertTrue(queen.getField().getYCord() == 5);
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
    
    /// \ref T1_1
    public void testCanEscapeFromBeingTaken() {
        try {
            setUp();
        
            
            Figure bishop = new Bishop(0, 0, true, boardPositions[0][0]);
            boardPositions[0][0].setFigure(bishop);
            Figure pawn = new Pawn(1, 1, true, boardPositions[1][1]);
            boardPositions[1][1].setFigure(pawn);
            blackPieces.add(bishop);
            blackPieces.add(pawn);
            
            //nothing threatening bishop, should return false
            assertFalse(opponent.canEscapeFromBeingTaken(bishop));
            
            //bishop can't move to escape danger
            Figure rook = new Rook(0, 1, false, boardPositions[0][1]);
            boardPositions[0][1].setFigure(rook);
            whitePieces.add(rook);
            assertFalse(opponent.canEscapeFromBeingTaken(bishop));
            
            //bishop can move, should return true and move bishop
            opponent.move(pawn, 1, 2);
            assertTrue(opponent.canEscapeFromBeingTaken(bishop));
            assertTrue(boardPositions[0][0].getFigure() == null);
            
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
    
    ///\ref T1_1
    public void testRandomPiece() {
        try {
            setUp();
        
            Figure rook = new Rook(0, 1, true, boardPositions[0][1]);
            boardPositions[0][1].setFigure(rook);
            Figure pawn = new Pawn(2, 2, true, boardPositions[2][2]);
            boardPositions[2][2].setFigure(pawn);
            blackPieces.add(rook);
            blackPieces.add(pawn);
            opponent.determineMoveablePieces(blackPieces);
            Field rookStart = rook.getField();
            Field pawnStart = pawn.getField();
            
            opponent.randomPiece();
            
            Field rookEnd = rook.getField();
            Field pawnEnd = pawn.getField();
            //exactly one of the pieces is moved
            assertTrue((rookStart != rookEnd && pawnStart == pawnEnd) || (pawnStart != pawnEnd && rookStart == rookEnd));
            
            tearDown();
        } catch(Exception e) {
            fail(e);
        }
    }
}

package game;

import java.util.ArrayList;

/// \imp \ref T1_1 \ref T1_2 \ref T1_3
/// Class that allows the game to be played single player.
/// Holds the methods for the computer choosing a piece to move and moving that piece.
/// These functions allow us to simulate a computer player so that the game can
/// be played without a second person.
public class Opponent {
    /// \ref T1_1 \ref T1_2
    ///The computer player must have a list of which pieces it can move so that it can
    ///choose a piece to move. This cuts down on time checking possible moves on pieces that 
    ///cannot move at all.
    private ArrayList<Figure> moveablePieces = new ArrayList();
    /// \ref T1_1 \ref T1_2
    ///The computer player must know where the player's pieces are in order to choose a proper move
    private ArrayList<Figure> playerPieces; 
    /// \ref T1_1 \ref T1_2
    ///The computer player needs to know the current board state in order to choose the 
    ///correct move to make.
    private Field[][] board;
    
    public Opponent(Field[][] arrayBoard) {
        this.board = arrayBoard;
    }
    
    /// \imp \ref T1_1 \ref T1_2 \ref T1_3
    ///The computer player must be able to choose a piece to move, choose where to move it,
    ///and then move that piece
    public void takeTurn(ArrayList<Figure> computerPieces, ArrayList<Figure> playerPieces) {
        this.playerPieces = playerPieces;
        determineMoveablePieces(computerPieces);
        choosePieceToMove();
    }
    
    /// \imp \ref T1_3
    /// The computer player must be able to move their pieces to a new position
    public void move(Figure piece, int newXCoord, int newYCoord) {
        Field oldField = piece.getField();
        System.out.println("Computer moving piece at " + oldField.getXCord() + ", " + oldField.getYCord());
        System.out.println("Moving to " + newXCoord + ", " + newYCoord);
        Field newField = board[newXCoord][newYCoord];
        if(newField.getFigure() != null) {
            playerPieces.remove(newField.getFigure());
        }
        oldField.getFigure().removeTexture();
        newField.setFigure(piece);
        oldField.removeFigure();
    }
    
    ///\imp \ref T1_1 \ref T1_2
    ///Determine which piece should be moved by the computer.
    ///The computer player will use the following rules to determine which piece to move next:
    ///1. It will try to get out of check, if necessary
    ///2. It will try to take an opponent's piece, if possible.
    ///3. It will try to protect it's own pieces, if possible.
    ///4. It will move randomly if it cannot do any of the above.
    public void choosePieceToMove() {
        
        if(isInCheck(board)) {
            escapeCheck();
            return;
        }
        
        for(Figure piece : moveablePieces) {
            if(canTakePlayerPiece(piece)) {
                return;
            }
        }
        
        for(Figure piece : moveablePieces) {
            if(canEscapeFromBeingTaken(piece)) {
                return;
            }
        }
        
        randomPiece();
        return;
    }
    
    ///\ref T1_1 The computer player must know all pieces that are able to be
    ///moved in order to correctly choose which piece it will move
    public void determineMoveablePieces(ArrayList<Figure> pieces) {
        moveablePieces.clear();
        for(Figure piece : pieces) {
            if(canMove(piece)) {
                moveablePieces.add(piece);
            }
        }
    }
    
    ///\ref T1_1 The computer player has to be able to determine if the piece is
    ///able to move before it can decide which piece to move
    public boolean canMove(Figure piece) {
        for (int xCoord = 0; xCoord < board.length; xCoord++) {
            for(int yCoord = 0; yCoord < board[xCoord].length; yCoord++) {
                Field localField = board[xCoord][yCoord];
                if(piece.isMovePossible(localField))
                    return true;
            }
        }
        return false;
    }
    
    ///\ref T1_1 \ref T1_2 
    ///In order to make the correct move, the computer player
    ///must be able to determine whether or not it is currently in check
    public boolean isInCheck(Field[][] board) {
        for(Figure piece : playerPieces) {
            for (int xCoord = 0; xCoord < board.length; xCoord++) {
                for(int yCoord = 0; yCoord < board[xCoord].length; yCoord++) {
                    Field localField = board[xCoord][yCoord];
                    if(piece.isMovePossible(localField) && localField.getFigure() instanceof King) {
                        System.out.println("Computer In check!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    ///\ref T1_1 \ref T1_2 
    ///In order to choose the correct piece and position to a move,
    ///the computer player must first attempt to escape from being in check
    public void escapeCheck() {
        for(Figure piece : moveablePieces) {
            for (int xCoord = 0; xCoord < board.length; xCoord++) {
                for(int yCoord = 0; yCoord < board[xCoord].length; yCoord++) {
                    Field localField = board[xCoord][yCoord];
                    if(piece.isMovePossible(localField)) {
                        Figure temp = localField.getFigure();
                        Field oldField = piece.getField();
                        if(temp != null) {
                            playerPieces.remove(temp);
                        }
                        oldField.getFigure().removeTexture();
                        localField.setFigure(piece);
                        oldField.removeFigure();
                        if(isInCheck(board)) {
                            if(temp == null) {
                                localField.getFigure().removeTexture();
                                oldField.setFigure(piece);
                                localField.removeFigure();
                            }
                            else {
                                localField.setFigure(temp);
                                oldField.setFigure(piece);
                                playerPieces.add(temp);
                            }
                        }
                        else {
                            return;
                        }
                        
                    }
                }
            }   
        }
    }
    
    ///\ref T1_1 
    ///The computer player has to determine if a piece is able to take one of the  
    ///player's pieces in order to choose which piece to move
    public boolean canTakePlayerPiece(Figure piece) {
        for (int xCoord = 0; xCoord < board.length; xCoord++) {
            for(int yCoord = 0; yCoord < board[xCoord].length; yCoord++) {
                Field localField = board[xCoord][yCoord];
                if(piece.isMovePossible(localField) && localField.getFigure() != null){
                    move(piece, xCoord, yCoord);
                    return true;
                }
            }
        }            
        return false;
    }
    
    ///\ref T1_1 The computer player has to determine if a piece is able to escape 
    ///being taken by the player in order to choose which piece to move
    public boolean canEscapeFromBeingTaken(Figure piece) {
        for(Figure playerPiece : playerPieces) {
            if(playerPiece.isMovePossible(piece.getField())) {
                for (int xCoord = 0; xCoord < board.length; xCoord++) {
                    for(int yCoord = 0; yCoord < board[xCoord].length; yCoord++) {
                        Field field = board[xCoord][yCoord];
                        if(piece.isMovePossible(field)) {
                            move(piece, xCoord, yCoord);
                            return true;
                        }
                    }    
                }
            }
        }
        return false;
    }
    
    ///\ref T1_1 
    ///If none of the other conditions are met, the computer player still has to 
    ///choose a piece, so it will choose a random piece to move
    public void randomPiece() {
        Figure piece = moveablePieces.get((int)(Math.random() * moveablePieces.size()));
        for (int xCoord = 0; xCoord < board.length; xCoord++) {
            for(int yCoord = 0; yCoord < board[xCoord].length; yCoord++) {
                Field field = board[xCoord][yCoord];
                if(piece.isMovePossible(field)) {
                    move(piece, xCoord, yCoord);
                    return;
                }
            }
        }    
    }
    public void setPlayerPieces(ArrayList<Figure> playerPieces) {
        this.playerPieces = playerPieces;
    }
}

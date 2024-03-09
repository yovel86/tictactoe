package machine_coding.tictactoe.models;

import machine_coding.tictactoe.exceptions.BotCountExceededException;
import machine_coding.tictactoe.exceptions.InvalidGameStateException;
import machine_coding.tictactoe.strategies.check_for_win.OrderOneWinningStrategy;
import machine_coding.tictactoe.strategies.check_for_win.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private Board board;
    private List<Player> players;
    private GameStatus gameStatus;
    private int currentPlayerIdx;
    private List<Move> moves;
    private WinningStrategy winningStrategy;

    private Game(GameBuilder builder) {
        this.board = builder.board;
        this.players = builder.players;
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.currentPlayerIdx = 0;
        this.moves = new ArrayList<>();
        this.winningStrategy = builder.winningStrategy;
        for(Player player: players) {
            if(player instanceof HumanPlayer) {
                HumanPlayer humanPlayer = (HumanPlayer) player;
                humanPlayer.setUndoCount(builder.undoLimitPerPlayer);
            }
        }
    }

    public void printBoard() {
        this.board.printBoard();
    }

    public void makeMove() {
        Player currentPlayer = players.get(currentPlayerIdx);
        RowColumn move = currentPlayer.makeMove(board);
        while(!board.checkIfCellIsUnoccupied(move.getRow(), move.getCol())) {
            if(currentPlayer instanceof HumanPlayer) {
                System.out.println("Please make a move on different cell\n");
            }
            move = currentPlayer.makeMove(board);
        }
        board.captureMoveInBoard(move.getRow(), move.getCol(), currentPlayer);
        Cell currentCell = board.getCell(move.getRow(), move.getCol());
        Move currentMove = new Move(currentPlayer, currentCell);
        moves.add(currentMove);
        if(winningStrategy.checkForWin(board, currentCell)) {
            gameStatus = GameStatus.ENDED;
            return;
        } else if(checkForDraw()) {
            gameStatus = GameStatus.DRAWN;
            return;
        }
        currentPlayerIdx += 1;
        if(currentPlayerIdx == players.size()) currentPlayerIdx = 0;
    }

    public boolean checkForDraw() {
        int n = board.getBoardSize();
        int totalMoves = n * n;
        return moves.size() == totalMoves;
    }

    public void undo() {
        int prevPlayerIdx = currentPlayerIdx - 1;
        if(prevPlayerIdx < 0) prevPlayerIdx = players.size() - 1;
        Player prevPlayer = players.get(prevPlayerIdx);
        if(prevPlayer instanceof HumanPlayer) {
            HumanPlayer humanPlayer = (HumanPlayer) prevPlayer;
            if(humanPlayer.getUndoCount() > 0) {
                Scanner input = new Scanner(System.in);
                System.out.println("Do you want to UNDO? (Y/N) ");
                char response = input.next().charAt(0);
                if(response == 'Y' || response == 'y') {
                    // Get the last move & Remove it from Moves array
                    Move lastMove = moves.remove(moves.size() - 1);
                    // Get the cell
                    Cell cell = lastMove.getCell();
                    // Remove the player from cell
                    cell.removePlayer();
                    // Give Move access to previous player
                    currentPlayerIdx = prevPlayerIdx;
                    // Handle the Undo move in WinningStrategy
                    winningStrategy.handleUndo(lastMove);
                    System.out.println("Successfully UNDO done for " + prevPlayer.getName() + "\n");
                    humanPlayer.setUndoCount(humanPlayer.getUndoCount() - 1);
                }
            } else {
                System.out.println("No more pending UNDO's...Moving to next player");
            }
        }
    }

    public void replay() {
        if(gameStatus.equals(GameStatus.IN_PROGRESS)) {
            throw new InvalidGameStateException("Game is still in progress...");
        } else {
            board.resetBoard();
            int moveCount = 1;
            for(Move move: moves) {
                Cell cell = move.getCell();
                Player player = move.getPlayer();
                board.captureMoveInBoard(cell.getRow(), cell.getCol(), player);
                System.out.println("Move " + moveCount + ", " + player.getName() + "'s turn");
                moveCount++;
                board.printBoard();
            }
        }
    }

    public static class GameBuilder {

        private Board board;
        private List<Player> players;
        private WinningStrategy winningStrategy;
        private int undoLimitPerPlayer;

       public GameBuilder setPlayers(List<Player> players) {
           this.players = players;
           int numOfPlayers = players.size();
           this.board = new Board(numOfPlayers + 1); // IF 2 players play, then we need 3 * 3 size of board
           this.winningStrategy = new OrderOneWinningStrategy(board.getBoardSize());
           return this;
       }

       public GameBuilder setUndoLimitPerPlayer(int undoCount) {
           this.undoLimitPerPlayer = undoCount;
           return this;
       }

       public Game build() throws BotCountExceededException {
           int botCount = 0;
           for(Player player: this.players) {
               if(player instanceof BotPlayer) botCount++;
               if(botCount > 1) throw new BotCountExceededException("Found more than 1 bots, Only 1 bot is allowed");
           }
           return new Game(this);
       }

    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getCurrentPlayerIdx() {
        return currentPlayerIdx;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public static GameBuilder getBuilder() {
        return new GameBuilder();
    }

}

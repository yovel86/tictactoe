package machine_coding.tictactoe.controllers;

import machine_coding.tictactoe.exceptions.BotCountExceededException;
import machine_coding.tictactoe.models.Game;
import machine_coding.tictactoe.models.Player;
import machine_coding.tictactoe.models.GameStatus;

import java.util.List;

public class GameController {

    public Game createGame(List<Player> players, int undoCount) throws BotCountExceededException {
        return Game.getBuilder().setPlayers(players).setUndoLimitPerPlayer(undoCount).build();
    }

    public GameStatus getGameStatus(Game game) {
        return game.getGameStatus();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public Player getCurrentPlayer(Game game) {
        return game.getCurrentPlayer();
    }

    public void undo(Game game) { game.undo(); }

    public void replayGame(Game game) { game.replay(); }

}

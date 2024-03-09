package machine_coding.tictactoe.strategies.bot_playing;

import machine_coding.tictactoe.models.Board;
import machine_coding.tictactoe.models.RowColumn;

public interface BotPlayingStrategy {

    public RowColumn makeMove(Board board);

}

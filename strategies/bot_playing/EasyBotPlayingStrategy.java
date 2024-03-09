package machine_coding.tictactoe.strategies.bot_playing;

import machine_coding.tictactoe.exceptions.InvalidGameStateException;
import machine_coding.tictactoe.models.Board;
import machine_coding.tictactoe.models.Cell;
import machine_coding.tictactoe.models.CellStatus;
import machine_coding.tictactoe.models.RowColumn;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {

    @Override
    public RowColumn makeMove(Board board) {
        for(List<Cell> row: board.getCells()) {
            for(Cell cell: row) {
                if(cell.getCellStatus().equals(CellStatus.UNOCCUPIED)) {
                    return new RowColumn(cell.getRow(), cell.getCol());
                }
            }
        }
        throw new InvalidGameStateException("No place for bot to make a move");
    }

}

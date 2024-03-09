package machine_coding.tictactoe.strategies.check_for_win;

import machine_coding.tictactoe.models.Board;
import machine_coding.tictactoe.models.Cell;
import machine_coding.tictactoe.models.Move;
import machine_coding.tictactoe.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class OrderOneWinningStrategy implements WinningStrategy {

    private int n;
    private List<HashMap<Character, Integer>> rowsMap;
    private List<HashMap<Character, Integer>> colsMap;
    private HashMap<Character, Integer> diagonalMap;
    private HashMap<Character, Integer> antiDiagonalMap;

    public OrderOneWinningStrategy(int boardSize) {
        this.n = boardSize;
        rowsMap = new ArrayList<>();
        colsMap = new ArrayList<>();
        diagonalMap = new HashMap<>();
        antiDiagonalMap = new HashMap<>();
        for(int i = 0; i < boardSize; i++) {
            rowsMap.add(new HashMap<>());
            colsMap.add(new HashMap<>());
        }
    }

    @Override
    public boolean checkForWin(Board board, Cell currentCell) {
        int row = currentCell.getRow();
        int col = currentCell.getCol();
        char symbol = currentCell.getPlayer().getSymbol().getSymbol();
        // Update Row HashMap
        HashMap<Character, Integer> rowMap = rowsMap.get(row);
        rowMap.put(symbol, rowMap.getOrDefault(symbol, 0) + 1);
        // Update Column HashMap
        HashMap<Character, Integer> colMap = colsMap.get(col);
        colMap.put(symbol, colMap.getOrDefault(symbol, 0) + 1);
        // Update Diagonal HashMap
        if(checkIfCellIsInDiagonal(row, col)) {
            diagonalMap.put(symbol, diagonalMap.getOrDefault(symbol, 0) + 1);
        }
        // Update Anti-Diagonal HashMap
        if(checkIfCellIsInAntiDiagonal(row, col, board.getBoardSize())) {
            antiDiagonalMap.put(symbol, antiDiagonalMap.getOrDefault(symbol, 0) + 1);
        }
        // Check If Player has WON
        if(rowsMap.get(row).get(symbol) == board.getBoardSize()) return true;
        if(colsMap.get(col).get(symbol) == board.getBoardSize()) return true;
        if(checkIfCellIsInDiagonal(row, col) && diagonalMap.get(symbol) == board.getBoardSize()) return true;
        if(checkIfCellIsInAntiDiagonal(row, col, board.getBoardSize()) && antiDiagonalMap.get(symbol) == board.getBoardSize()) return true;
        return false;
    }

    @Override
    public void handleUndo(Move move) {
        Cell cell = move.getCell();
        int row = cell.getRow();
        int col = cell.getCol();
        Player player = move.getPlayer();
        char symbol = player.getSymbol().getSymbol();
        HashMap<Character, Integer> rowMap = rowsMap.get(row);
        rowMap.put(symbol, rowMap.get(symbol) - 1);
        HashMap<Character, Integer> colMap = colsMap.get(col);
        colMap.put(symbol, colMap.get(symbol) - 1);
        if(checkIfCellIsInDiagonal(row, col)) {
            diagonalMap.put(symbol, diagonalMap.get(symbol) - 1);
        }
        if(checkIfCellIsInAntiDiagonal(row, col, n)) {
            antiDiagonalMap.put(symbol, antiDiagonalMap.get(symbol) - 1);
        }
    }

    private boolean checkIfCellIsInDiagonal(int row, int col) {
        return row == col;
    }

    private boolean checkIfCellIsInAntiDiagonal(int row, int col, int boardSize) {
        return (row + col) == (boardSize - 1);
    }

}

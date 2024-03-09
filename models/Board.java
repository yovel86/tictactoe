package machine_coding.tictactoe.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<List<Cell>> cells;

    public Board(int boardSize) {
        this.cells = new ArrayList<>();
        for(int i = 0; i < boardSize; i++) {
            List<Cell> row = new ArrayList<>();
            for(int j = 0; j < boardSize; j++) {
                row.add(new Cell(i, j, CellStatus.UNOCCUPIED));
            }
            this.cells.add(row);
        }
    }

    public void printBoard() {
        int n = cells.size();
        for(int i = 0; i < n; i++) {
            List<Cell> row = cells.get(i);
            for(int j = 0; j < n; j++) {
                Cell cell = row.get(j);
                cell.printCell();
            }
            System.out.println();
        }
        System.out.println();
    }

    public void resetBoard() {
        for(List<Cell> row: cells) {
            for(Cell cell: row) {
                cell.removePlayer();
            }
        }
    }

    public boolean checkIfCellIsUnoccupied(int row, int col) {
        Cell cell = this.cells.get(row).get(col);
        return cell.isUnoccupied();
    }

    public void captureMoveInBoard(int row, int col, Player player) {
        Cell cell = cells.get(row).get(col);
        cell.setPlayer(player);
    }

    public Cell getCell(int row, int col) {
        return cells.get(row).get(col);
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public int getBoardSize() {
        return this.cells.size();
    }

}

package machine_coding.tictactoe.models;

public class Cell {

    private int row;
    private int col;
    private CellStatus cellStatus;
    private Player player;

    public Cell(int row, int col, CellStatus cellStatus) {
        this.row = row;
        this.col = col;
        this.cellStatus = cellStatus;
    }

    public boolean isUnoccupied() {
        return player == null && cellStatus.equals(CellStatus.UNOCCUPIED);
    }

    public void printCell() {
        if(this.getCellStatus().equals(CellStatus.UNOCCUPIED)) {
            System.out.print(" _ ");
        } else {
            System.out.print(" " + this.player.getSymbol().getSymbol() + " ");
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) {
        this.player = player;
        this.cellStatus = CellStatus.OCCUPIED;
    }

    public void removePlayer() {
        this.player = null;
        this.cellStatus = CellStatus.UNOCCUPIED;
    }

}

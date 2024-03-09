package machine_coding.tictactoe.models;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private int undoCount;

    public HumanPlayer(String name, Symbol symbol) {
        super(name, symbol);
    }

    @Override
    public RowColumn makeMove(Board board) {
        Scanner input = new Scanner(System.in);
        System.out.println(this.getName() + "'s turn: (enter row & column number) ");
        int row = input.nextInt();
        int col = input.nextInt();
        while(row >= board.getBoardSize() || col >= board.getBoardSize()) {
            System.out.println("Please enter Row & Column number within the size of Board");
            row = input.nextInt();
            col = input.nextInt();
        }
        return new RowColumn(row, col);
    }

    public int getUndoCount() {
        return undoCount;
    }

    public void setUndoCount(int undoCount) {
        this.undoCount = undoCount;
    }

}

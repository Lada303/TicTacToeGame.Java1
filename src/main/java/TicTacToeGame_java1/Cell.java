package TicTacToeGame_java1;

public class Cell {

    private final int columnNumber;
    private final int rowNumber;
    private Dots dot;

    protected Cell(int x, int y) {
        columnNumber = x;
        rowNumber = y;
    }

    protected int getColumnNumber() {
        return columnNumber;
    }

    protected int getRowNumber() {
        return rowNumber;
    }

    protected void setDot(Dots dot) {
        this.dot = dot;
    }

    protected Dots getDot() {
        return dot;
    }

    protected boolean isEmptyCell() {
        return dot == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cell that = (Cell) obj;
        if (this.columnNumber != that.columnNumber) return false;
        if (this.rowNumber != that.rowNumber) return false;
        return this.dot == that.dot;

    }
}

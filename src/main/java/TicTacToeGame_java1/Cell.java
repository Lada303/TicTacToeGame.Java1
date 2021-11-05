package TicTacToeGame_java1;

public class Cell {
    protected final int columnNumber;
    protected final int rowNumber;
    private Dots dot;

    protected Cell (int x, int y) {columnNumber=x; rowNumber=y; }
    protected Cell (int x, int y, Dots dot) {columnNumber=x; rowNumber=y; this.dot=dot;}
    protected Cell (Cell that) {
        this.columnNumber=that.columnNumber;
        this.rowNumber=that.rowNumber;
        this.dot=that.dot;
    }

    protected void setDot(Dots dot) {
        this.dot=dot;
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

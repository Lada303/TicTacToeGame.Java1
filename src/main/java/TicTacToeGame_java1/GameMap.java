package TicTacToeGame_java1;

public class GameMap {

    private final Cell[][] map;

    protected GameMap(int x, int y) {
        map = new Cell[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                map[i][j] = new Cell(j, i);
            }
        }
    }

    protected boolean isCellValid(int x, int y) {
        return (0 <= x && x < map[0].length) || (0 <= y && y < map.length);
    }

    protected int getCountRow() {
        return map.length;
    }

    protected int getCountColumn() {
        return map[1].length;
    }

    protected Cell getCell(int x, int y) {
        return map[y][x];
    }

    protected Cell getCell(Cell cell) {
        return map[cell.rowNumber][cell.columnNumber];
    }

    protected Cell[] getRow(Cell cell) {
        Cell[] row = new Cell[map[0].length];
        System.arraycopy(map[cell.rowNumber], 0, row, 0, row.length);
        return row;
    }

    protected Cell[] getColumn(Cell cell) {
        Cell[] column = new Cell[map.length];
        for (int i = 0; i < column.length; i++) {
            column[i] = map[i][cell.columnNumber];
        }
        return column;
    }

    protected Cell[] getD1(Cell cell) {
        int b = cell.rowNumber - cell.columnNumber;
        Cell[] d1 = new Cell[map.length];
        for (int i = (b >= 0 ? b : 0); i < d1.length - (b >= 0 ? 0 : -b); i++) {
            d1[i] = map[i][i - b];
        }
        return d1;
    }

    protected Cell[] getD2(Cell cell) {
        int b = cell.rowNumber + cell.columnNumber;
        Cell[] d2 = new Cell[map.length];
        for (int i = (b < map.length ? 0 : b - (map.length - 1));
             i < d2.length - (b < map.length ? (map.length - 1) - b : 0); i++) {
            d2[i] = (map[i][b - i]);
        }
        return d2;
    }

    protected boolean isD1(Cell cell, int lengthD) {
        return (cell.rowNumber - (map.length - lengthD) <= cell.columnNumber
                && cell.columnNumber <= map[0].length - lengthD + cell.rowNumber);
    }

    protected boolean isD2(Cell cell, int lengthD) {
        return (lengthD - 1 - cell.rowNumber <= cell.columnNumber
                && cell.columnNumber <= map[0].length - 1 - cell.rowNumber + (map.length - lengthD));
    }

    protected void printMap() {
        System.out.print("  | ");
        for (int i = 1; i <= map[1].length; i++) {
            System.out.print(i + " | ");
        }
        System.out.println();
        printHorizontalLine();
        for (int i = 0; i < map.length; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < map[1].length; j++) {
                System.out.print((map[i][j].getDot() == null ? " " : map[i][j].getDot()) + " | ");
            }
            System.out.println();
            printHorizontalLine();
        }
    }

    private void printHorizontalLine() {
        for (int i = 0; i <= map[1].length; i++) {
            System.out.print("----");
        }
        System.out.println();
    }

}

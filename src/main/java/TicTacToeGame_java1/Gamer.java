package TicTacToeGame_java1;

public abstract class Gamer {
    private final int id;
    private final String name;
    private final Dots dots;
    private Cell cell;
    private int score;

    protected Gamer(int id, String name, Dots dots) {
        this.id = id;
        this.name = name;
        this.dots = dots;
        this.score = 0;
    }

    protected int getId() {
        return id;
    }

    protected String getName() {
        return name;
    }

    protected Dots getDots() {
        return dots;
    }

    protected Cell getCell() {
        return cell;
    }

    protected void setCell(Cell cell) {
        this.cell = cell;
    }

    protected int getScore() {
        return score;
    }

    protected void incrementScore() {
        this.score++;
    }

    protected abstract boolean doStep(Competition competition);
}
package TicTacToeGame_java1;

public abstract class Gamer {
    private final String name;
    private final Dots dots;
    private Cell cell;
    private int score;

    protected Gamer(String name, Dots dots) {
        this.name = name;
        this.dots = dots;
        this.score = 0;
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

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    protected abstract boolean doStep(Competition competition);
}
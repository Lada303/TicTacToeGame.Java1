package TicTacToeGame_java1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CompetitionJudge {

    private final Gamer gamer1;
    private final Gamer gamer2;
    private int drawScore;
    private int whoseMove;
    private int countStep;
    private GameMap map;
    private int dots_to_win;

    protected CompetitionJudge(Competition competition) {
        this.gamer1 = competition.getGamer1();
        this.gamer2 = competition.getGamer2();
        this.drawScore = 0;
    }

    protected int getWhoseMove() {
        return whoseMove;
    }

    public void resetWhoseMove() {
        this.whoseMove = 1;
    }

    protected void changeWhoseMove() {
        this.whoseMove = whoseMove * (-1);
    }

    protected int getCountStep() {
        return countStep;
    }

    protected void incrementCountStep() {
        this.countStep++;
    }

    protected void resetCountStep() {
        this.countStep = 0;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void setDots_to_win(int dots_to_win) {
        this.dots_to_win = dots_to_win;
    }

    protected void printScore() {
        System.out.printf("Счет:\t\t%s\t\t%s\t\tНичья\n", gamer1.getName(), gamer2.getName());
        System.out.printf("\t\t\t%d\t\t\t%d\t\t\t%d\n", gamer1.getScore(), gamer2.getScore(), drawScore);
        System.out.println();
    }

    protected void printScoreToFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("Score.txt", true))) {
            if (gamer1.getScore() + gamer2.getScore() + drawScore == 1) {
                out.write("\n");
                out.write(String.format("Счет:\t\t%s\t\t%s\t\tНичья\n", gamer1.getName(), gamer2.getName()));
            }
            out.write(String.format("\t\t\t%d\t\t\t%d\t\t\t%d\n", gamer1.getScore(), gamer2.getScore(), drawScore));
        } catch (IOException e) {
            System.out.println("Exp: " + e.getMessage());
        }
    }

    protected boolean isWin(Cell lastCell) {
        if (countStep >= dots_to_win * 2 - 1) {
            if (lastCell.getDot() == Dots.X && checkWin(lastCell)) {
                System.out.println("Победил игрок " + gamer1.getName() + "\n");
                gamer1.incrementScore();
                return true;
            }
            if (lastCell.getDot() == Dots.O && checkWin(lastCell)) {
                System.out.println("Победил игрок " + gamer2.getName() + "\n");
                gamer2.incrementScore();
                return true;
            }
        }
        return false;
    }

    private boolean checkWin(Cell lastCell) {
        if (countNonInterruptDotsToWin(lastCell.getDot(), map.getRow(lastCell)) == dots_to_win) {
            return true;
        }
        if (countNonInterruptDotsToWin(lastCell.getDot(), map.getColumn(lastCell)) == dots_to_win) {
            return true;
        }
        if (map.isD1(lastCell, dots_to_win) &&
                countNonInterruptDotsToWin(lastCell.getDot(), map.getD1(lastCell)) == dots_to_win) {
            return true;
        }
        return (map.isD2(lastCell, dots_to_win) &&
                countNonInterruptDotsToWin(lastCell.getDot(), map.getD2(lastCell)) == dots_to_win);
    }

    // неприрывающаяся последовательность симоволов - для определения победителя
    private int countNonInterruptDotsToWin(Dots dot, Cell[] arrCells) {
        int counter = 0;
        for (Cell cell : arrCells) {
            counter = (cell != null && cell.getDot() == dot ? counter + 1 : 0);
            if (counter == dots_to_win) return counter;
        }
        return counter;
    }

    protected boolean isDraw() {
        if (countStep >= map.getCountColumn() * map.getCountRow()) {
            System.out.println("Ничья\n");
            drawScore++;
            return true;
        }
        return false;
    }
}

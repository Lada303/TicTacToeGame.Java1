package TicTacToeGame.gameApp;

/*
"Судья"
- следит за игрой (кто ходит, какой сечас шаг и т.п.)
- определяет результаты игры (кто выграл, ничья)
- считает очки по игроку1, игроку2 и ничьи
- печатает очки в консоль и в фаил
- записывает процесс игры в фаил
*/

import TicTacToeGame.gameApp.models.gamemap.Cell;
import TicTacToeGame.gameApp.models.gamemap.Dots;
import TicTacToeGame.gameApp.models.gamemap.GameMap;
import TicTacToeGame.gameApp.models.gamers.Gamer;
import TicTacToeGame.gameApp.writersfile.JacksonWriter;
import TicTacToeGame.gameApp.writersfile.WriteGameToFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompetitionJudge {

    private final Gamer gamer1;
    private final Gamer gamer2;
    private int drawScore;
    private int whoseMove;
    private int countStep;
    private int countFiles;
    private GameMap map;
    private int dots_to_win;
    private final List<String> listStep;
    private final WriteGameToFile writer;

    public CompetitionJudge(Competition competition) {
        this.gamer1 = competition.getGamer1();
        this.gamer2 = competition.getGamer2();
        this.drawScore = 0;
        this.listStep = new ArrayList<>();
        // тут можно поменять Writer записи файла с содержанием игры
        this.writer = new JacksonWriter(gamer1, gamer2, listStep);
        this.countFiles = 0;
        lookCounterFile();
    }

    public int getWhoseMove() {
        return whoseMove;
    }

    public void resetWhoseMove() {
        this.whoseMove = 1;
    }

    public void changeWhoseMove() {
        this.whoseMove = whoseMove * (-1);
    }

    public int getCountStep() {
        return countStep;
    }

    public void incrementCountStep() {
        this.countStep++;
    }

    public void resetCountStep() {
        this.countStep = 0;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void setDots_to_win(int dots_to_win) {
        this.dots_to_win = dots_to_win;
    }

    public void clearListStep() {
        listStep.clear();
    }

    public void addToListStep(Cell lastCell) {
        listStep.add((lastCell.getColumnNumber() + 1) + " " + (lastCell.getRowNumber() + 1));
    }

    public void printScore() {
        System.out.printf("Счет:\t\t%s\t\t%s\t\tНичья\n", gamer1.getName(), gamer2.getName());
        System.out.printf("\t\t\t%d\t\t\t%d\t\t\t%d\n", gamer1.getScore(), gamer2.getScore(), drawScore);
        System.out.println();
    }

    public void printScoreToFile() {
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

    public boolean isWin(Cell lastCell) {
        if (countStep >= dots_to_win * 2 - 1) {
            if (lastCell.getDot() == Dots.X && checkWin(lastCell)) {
                System.out.println("Победил игрок " + gamer1.getName() + "\n");
                gamer1.incrementScore();
                writer.writeGameToFile(countFiles, map.getSize(), 1);
                countFiles++;
                return true;
            }
            if (lastCell.getDot() == Dots.O && checkWin(lastCell)) {
                System.out.println("Победил игрок " + gamer2.getName() + "\n");
                gamer2.incrementScore();
                writer.writeGameToFile(countFiles, map.getSize(), 2);
                countFiles++;
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

    public boolean isDraw() {
        if (countStep >= map.getCountColumn() * map.getCountRow()) {
            System.out.println("Ничья\n");
            drawScore++;
            writer.writeGameToFile(countFiles, map.getSize(), 0);
            countFiles++;
            return true;
        }
        return false;
    }

    private void lookCounterFile() {
        File file;
        String end = writer.getClass().getName().contains("son") ? ".json" : ".xml";
        do {
            countFiles++;
            String name = gamer1.getName()+"Vs" + gamer2.getName() + "_" + countFiles + end;
            file = new File(name);
         } while (file.exists());
     }
}

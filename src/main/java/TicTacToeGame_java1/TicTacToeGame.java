package TicTacToeGame_java1;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeGame {

    private static int dotsToWin = 3;
    private static int mode;
    private static int whoseMove;
    private static GameMap map;
    private static int countStep;
    private static Cell gamer1Cell;
    private static Cell gamer2Cell;
    private static int gamer1Score = 0;
    private static int gamer2Score = 0;
    private static int drawScore = 0;
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rand = new Random();

    public static void main(String[] args) {
        helloInstruction();
        mode = selectMode();
        while (true) {
            selectMap();
            dotsToWin = selectDotsToWin();
            playRound();
            printScore();
            printScoreToFile();
            System.out.println("Продолжить игру? Y/N >>>");
            if (!sc.next().equalsIgnoreCase("y")) {
                System.out.println("Пока..");
                break;
            }
        }
        sc.close();
    }

    private static void playRound() {
        countStep = 0;
        map.printMap();
        while (true) {
            whoseMove = 1;
            humanTurn();
            if (countStep == -1) break;
            countStep++;
            map.printMap();
            if (isWin(gamer1Cell)) break;
            if (isDraw()) break;

            whoseMove = 2;
            if (mode == 1) {
                aiTurn();
            } else {
                humanTurn();
            }
            countStep++;
            map.printMap();
            if (isWin(gamer2Cell)) break;
            if (isDraw()) break;
        }
    }

    private static void helloInstruction() {
        System.out.println("Поиграем в крестики-нолики!");
        System.out.println("Вы вводите координаты в формате X Y через Пробел или Enter.");
        System.out.println("Если хотите прекратить игру - введите q вместо координат.\n");
    }

    private static int selectMode() {
        int mode = 0;
        try {
            System.out.println("Выбеерете режим игры. ");
            System.out.println("Введите 0, если хотите играть с другим игроком.");
            System.out.println("Введите 1, если хотите играть с ИИ >>>.");
            mode = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Вы ввели неправильный режим. Включен режим по умолчанию - 0!");
        }
        System.out.println("Установлен режим игры с " + (mode == 1 ? "ИИ!" : "другим игроком!"));
        System.out.println();
        return mode;
    }

    private static void selectMap() {
        int sizeX, sizeY;
        try {
            do {
                System.out.println("Введите размер поля X  Y через пробел или enter. ");
                System.out.println("X =  от 3 до 8, а Y = от 3 до 5 (все включительно). ");
                System.out.println("При этом X должен быть >= Y >>> ");
                sizeX = sc.nextInt();
                sizeY = sc.nextInt();
            } while (isSizeNotValid(sizeX, 3, 8) || isSizeNotValid(sizeY, 3, 5) || sizeX < sizeY);
        } catch (InputMismatchException e) {
            System.out.println("Карта по умолчанию - 3*3");
            sizeX = sizeY = 3;
            sc.nextLine();
        }
        map = new GameMap(sizeX, sizeY);
    }

    private static int selectDotsToWin() {
        int dots;
        if (map.getCountRow() <= 4) {
            dots = map.getCountRow();
            System.out.println("Количество фишек для победы = " + dots + "\n");
        } else {
            try {
                do {
                    System.out.println("Введите количество фишек, необходимых для победы 4 или 5");
                    dots = sc.nextInt();
                } while (isSizeNotValid(dots, 4, 5));
            } catch (InputMismatchException e) {
                dots = map.getCountRow();
                System.out.println("Количество фишек для победы = " + dots + "\n");
                sc.nextLine();
            }
        }
        return dots;
    }

    private static boolean isSizeNotValid(int size, int min, int max) {
        if (size >= min && size <= max) {
            return false;
        }
        System.out.println("Неверное значение");
        return true;
    }

    private static void printScore() {
        System.out.println(mode == 0 ? "Счет:   Игрок1  Игрок2  Ничья" : "Счет:   Вы      ИИ      Ничья");
        System.out.printf("         %d       %d       %d\n", gamer1Score, gamer2Score, drawScore);
        System.out.println();
    }

    private static void printScoreToFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("Score.txt", true))) {
            if (gamer1Score + gamer2Score + drawScore == 1) {
                out.write("\n");
                out.write(mode == 0 ? "Счет:   Игрок1  Игрок2  Ничья\n" : "Счет:   Вы      ИИ      Ничья\n");
            }
            out.write(String.format("         %d       %d       %d\n", gamer1Score, gamer2Score, drawScore));
        } catch (IOException e) {
            System.out.println("Exp: " + e.getMessage());
        }
    }

    private static void humanTurn() {
        int x, y;
        try {
            do {
                System.out.println("Введите координаты в формате X Y >>>");
                x = sc.nextInt() - 1;
                y = sc.nextInt() - 1;
            } while (!isValidEmpty(x, y));
        } catch (InputMismatchException e) {
            System.out.println("Вы прервали раунд!");
            countStep = -1;
            sc.nextLine();
            return;
        }
        if (mode == 0 && whoseMove == 2) {
            map.getCell(x, y).setDot(Dots.O);
            gamer2Cell = map.getCell(x, y);
        } else {
            map.getCell(x, y).setDot(Dots.X);
            gamer1Cell = map.getCell(x, y);
        }
    }

    private static boolean isValidEmpty(int x, int y) {
        if (!map.isCellValid(x, y)) {
            System.out.println("Неверные координаты");
            return false;
        }
        if (map.getCell(x, y).isEmptyCell()) return true;
        System.out.println("Ячейка занята");
        return false;
    }

    private static boolean isWin(Cell lastCell) {
        if (countStep >= dotsToWin * 2 - 1) {
            if (lastCell.getDot() == Dots.X && checkWin(lastCell)) {
                System.out.println("победил первый игрок!\n");
                gamer1Score++;
                return true;
            }
            if (lastCell.getDot() == Dots.O && checkWin(lastCell)) {
                System.out.println("Победил второй игрок" + (mode == 1 ? " - ИИ!\n" : "!\n"));
                gamer2Score++;
                return true;
            }
        }
        return false;
    }

    private static boolean checkWin(Cell lastCell) {
        if (countNonInterruptDotsToWin(lastCell.getDot(), map.getRow(lastCell)) == dotsToWin) {
            return true;
        }
        if (countNonInterruptDotsToWin(lastCell.getDot(), map.getColumn(lastCell)) == dotsToWin) {
            return true;
        }
        if (map.isD1(lastCell, dotsToWin) &&
                countNonInterruptDotsToWin(lastCell.getDot(), map.getD1(lastCell)) == dotsToWin) {
            return true;
        }
        return (map.isD2(lastCell, dotsToWin) &&
                countNonInterruptDotsToWin(lastCell.getDot(), map.getD2(lastCell)) == dotsToWin);
    }

    // неприрывающаяся последовательность симоволов - для определения победителя
    private static int countNonInterruptDotsToWin(Dots dot, Cell[] arrCells) {
        int counter = 0;
        for (Cell cell : arrCells) {
            counter = (cell != null && cell.getDot() == dot ? counter + 1 : 0);
            if (counter == dotsToWin) return counter;
        }
        return counter;
    }

    private static boolean isDraw() {
        if (countStep >= map.getCountColumn() * map.getCountRow()) {
            System.out.println("Ничья\n");
            drawScore++;
            return true;
        }
        return false;
    }

    private static void aiTurn() {
        for (int decrement = 1; decrement < dotsToWin; decrement++) {
            //шаг для победы
            if (decrement == 1 && isAIStep(gamer2Cell, decrement)) {
                break;
            }
            //шаг для помехи
            if (isAIStep(gamer1Cell, decrement)) {
                break;
            }
        }
        if (gamer2Cell == null || !map.getCell(gamer2Cell).isEmptyCell()) {
            int x, y;
            do {
                x = rand.nextInt(map.getCountColumn());
                y = rand.nextInt(map.getCountRow());
            } while (map.getCell(x, y).getDot() != null);
            gamer2Cell = map.getCell(x, y);
        }
        map.getCell(gamer2Cell).setDot(Dots.O);
        System.out.println("Компьютер походил " + (gamer2Cell.columnNumber + 1) + " " + (gamer2Cell.rowNumber + 1));
    }

    //выбирает шаг
    private static boolean isAIStep(Cell lastCell, int decrement) {
        if (lastCell == null || countStep < dotsToWin * 2 - 1 && lastCell.getDot() == Dots.O) {
            return false;
        }
        if (map.isD1(lastCell, dotsToWin)
                && countDotsInLine(lastCell.getDot(), map.getD1(lastCell)) == dotsToWin - decrement
                && isEmptyValidCellInLine(map.getD1(lastCell), lastCell)) {
            return true;
        }
        if (map.isD2(lastCell, dotsToWin)
                && countDotsInLine(lastCell.getDot(), map.getD2(lastCell)) == dotsToWin - decrement
                && isEmptyValidCellInLine(map.getD2(lastCell), lastCell)) {
            return true;
        }
        if (countDotsInLine(lastCell.getDot(), map.getRow(lastCell)) == dotsToWin - decrement
                && isEmptyValidCellInLine(map.getRow(lastCell), lastCell)) {
            return true;
        }
        return countDotsInLine(lastCell.getDot(), map.getColumn(lastCell)) == dotsToWin - decrement
                && isEmptyValidCellInLine(map.getColumn(lastCell), lastCell);
    }

    //Всего элементов  - для определения пути противодействия игроку
    private static int countDotsInLine(Dots dot, Cell[] arrCells) {
        int counter = 0;
        for (Cell cell : arrCells) {
            if (cell != null && cell.getDot() == dot) counter++;
        }
        return counter;
    }

    // находит пустую ячиеку и устанавливает ее координаты
    private static boolean isEmptyValidCellInLine(Cell[] arrCells, Cell lastCell) {
        int position = 0;
        for (int i = 0; i < arrCells.length; i++) {
            if (arrCells[i] != null && arrCells[i] == lastCell) {
                position = i;
                break;
            }
        }
        if (position != 0 && arrCells[position - 1] != null && arrCells[position - 1].isEmptyCell()) {
            gamer2Cell = arrCells[position - 1];
            return true;
        }
        if (position != arrCells.length - 1 && arrCells[position + 1] != null && arrCells[position + 1].isEmptyCell()) {
            gamer2Cell = arrCells[position + 1];
            return true;
        }
        for (Cell cell : arrCells) {
            if (cell != null && cell.isEmptyCell()) {
                gamer2Cell = cell;
                return true;
            }
        }
        return false;
    }

}

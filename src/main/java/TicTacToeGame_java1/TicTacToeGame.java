package TicTacToeGame_java1;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeGame {

    public static int dotsToWin=3;
    public static GameMap map;
    public static int countStep;
    public static Cell humanCell;
    public static Cell aiCell;
    public static int humanScore=0;
    public static int aiScore=0;
    public static int nScore=0;

    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();


    public static void main(String[] args) {
        helloInstruction();
        while(true) {
            selectMap();
            dotsToWin = selectDotsToWin();
            playRound();
            printScore();
            System.out.println("Продолжить игру? Y/N >>>");
            if (!sc.next().toLowerCase(Locale.ROOT).equals("y")) {
                System.out.println("Пока..");
                break;
            }
        }
        sc.close();

    }
    public static void playRound() {
        countStep = 0;
        map.printMap();
        while (true) {
            humanTurn();
            if (countStep==-1) break;
            countStep++;
            map.printMap();
            if (isWin(humanCell)) break;
            if (isDraw()) break;

            if ( countStep < dotsToWin*2-1 || !lookingWinStep()) {
                lookingStopStep();
            }
            aiTurn();
            countStep++;
            map.printMap();
            if (isWin(aiCell)) break;
            if (isDraw()) break;
        }
    }

    public static void helloInstruction() {
        System.out.println("Поиграем в крестики-нолики!");
        System.out.println("Вы вводите координаты в формате X Y через Пробел или Enter.");
        System.out.println("Если хотите прекратить игру - введите q вместо координат.\n");
    }

    public static void selectMap() {
        int sizeX, sizeY;
        try {
            do {
                System.out.println("Введите размер поля X  Y через пробел или enter. ");
                System.out.println("X =  от 3 до 8, а Y = от 3 до 5 (все включительно). ");
                System.out.println("При этом X должен быть >= Y >>> ");
                sizeX = sc.nextInt();
                sizeY = sc.nextInt();
            } while (isSizeNotValid(sizeX,3,8) || isSizeNotValid(sizeY, 3,5) || sizeX<sizeY);
        }
        catch (InputMismatchException e) {
            System.out.println("Карта по умолчанию - 3*3");
            sizeX=sizeY=3;
            sc.nextLine();
        }
        map = new GameMap(sizeX,sizeY);
    }

    public static int selectDotsToWin() {
        int dots;
        if (map.getCountRow()<=4) {
            dots=map.getCountRow();
            System.out.println("Количество фишек для победы = " +dots+"\n");
        }
        else {
            try {
                do {
                    System.out.println("Введите количество фишек, необходимых для победы 4 или 5");
                    dots = sc.nextInt();
                } while (isSizeNotValid(dots, 4, 5)) ;
            } catch (InputMismatchException e) {
                dots=map.getCountRow();
                System.out.println("Количество фишек для победы = " +dots+"\n");
                sc.nextLine();
            }
        }
        return dots;
    }

    public static boolean isSizeNotValid(int size, int min, int max) {
        if (size >= min && size <= max) {
            return false;
        }
        System.out.println("Неверное значение");
        return true;
    }

    public static void printScore() {
        System.out.println("Счет:   Ваш   ИИ   Ничья");
        System.out.printf("        %d     %d    %d\n", humanScore, aiScore, nScore);
        System.out.println();
    }

    public static void humanTurn() {
       int x,y;
        try {
            do {
                System.out.println("Введите координаты в формате X Y >>>");
                x = sc.nextInt() - 1;
                y = sc.nextInt() - 1;
            } while (!isValidEmpty(x,y));
        }
        catch (InputMismatchException e) {
            System.out.println("Вы прервали раунд!");
            countStep = -1;
            sc.nextLine();
            return;
        }
        map.getCell(x,y).setDot(Dots.X);
        humanCell= map.getCell(x, y);
    }

    public static boolean isValidEmpty(int x, int y) {
        if (!map.isCellValid(x,y)) {
            System.out.println("Неверные координаты");
            return false;
        }
        if (map.getCell(x,y).isEmptyCell()) return true;
        System.out.println("Ячейка занята");
        return false;
    }

    public static boolean isWin(Cell lastCell) {
        if (countStep >= dotsToWin*2-1) {
            if (lastCell.getDot() == Dots.X && checkWin(lastCell)){
                System.out.println("Вы победили!\n");
                humanScore++;
                return true;
            }
            if (lastCell.getDot() == Dots.O && checkWin(lastCell)) {
                System.out.println("Победил компьютер...\n");
                aiScore++;
                return true;
            }
        }
        return false;
    }

    public static boolean checkWin(Cell lastCell) {
        // count row
        if (countNonInterruptDotsToWin(lastCell.getDot(), map.getRow(lastCell))==dotsToWin) return true;
        // count column
        if (countNonInterruptDotsToWin(lastCell.getDot(), map.getColumn(lastCell))==dotsToWin) return true;
        // count D1 and D2
        if (map.isD1(lastCell,dotsToWin) &&
            countNonInterruptDotsToWin(lastCell.getDot(), map.getD1(lastCell))==dotsToWin) return true;
        return (map.isD2(lastCell,dotsToWin) &&
                countNonInterruptDotsToWin(lastCell.getDot(), map.getD2(lastCell))==dotsToWin);
    }

    // неприрывающаяся последовательность симоволов - для определения победителя
    public static int countNonInterruptDotsToWin(Dots dot, Cell[] arrCells) {
        int counter=0;
        for (Cell cell : arrCells) {
            counter = (cell != null && cell.getDot() == dot ? counter + 1 : 0);
            if (counter == dotsToWin) return counter;
        }
        return counter;
    }

    public static boolean isDraw() {
        if (countStep >= map.getCountColumn() * map.getCountRow()) {
            System.out.println("Ничья\n");
            nScore++;
            return true;
        }
        return false;
    }

    //выбирает шаг, для победы
    public static boolean lookingWinStep() {
        if (countDotsInLine(Dots.O, map.getRow(aiCell))==dotsToWin-1
            && isEmptyValidCellInLine(map.getRow(aiCell),aiCell)) return true;
        if (countDotsInLine(Dots.O, map.getColumn(aiCell))==dotsToWin-1
            && isEmptyValidCellInLine(map.getColumn(aiCell),aiCell)) return true;
        if (map.isD1(aiCell, dotsToWin) && countDotsInLine(Dots.O, map.getD1(aiCell))==dotsToWin-1
            && isEmptyValidCellInLine(map.getD1(aiCell),aiCell)) return true;
        return map.isD2(aiCell,dotsToWin) && countDotsInLine(Dots.O, map.getD2(aiCell)) == dotsToWin - 1
                && isEmptyValidCellInLine(map.getD2(aiCell),aiCell);
    }

    //Всего элементов  - для определения пути противодействия игроку
    public static int countDotsInLine(Dots dot, Cell[] arrCells) {
        int counter=0;
        for (Cell cell : arrCells) {
            if (cell != null && cell.getDot() == dot) counter++;
        }
        return counter;
    }
    // находит пустую ячиеку и устанавливает ее координаты
    public static boolean isEmptyValidCellInLine(Cell[] arrCells, Cell lastCell) {
        int position=0;
        for (int i = 0; i < arrCells.length; i++) {
            if (arrCells[i] != null && arrCells[i]==lastCell) {
                position=i;
                break;
            }
        }
        if (position!=0 && arrCells[position-1] != null && arrCells[position-1].isEmptyCell()) {
            aiCell = arrCells[position-1];
            return true;
        }
        if (position!=arrCells.length-1 && arrCells[position+1] != null && arrCells[position+1].isEmptyCell()) {
            aiCell = arrCells[position+1];
            return true;
        }

        for (Cell cell : arrCells) {
            if (cell != null && cell.isEmptyCell()){
                aiCell = cell;
                return true;
            }
        }
        return false;
    }

    //выбирает шаг, чтобы помешать игроку
    public static boolean lookingStopStep() {
        int count_row =  countDotsInLine(Dots.X, map.getRow(humanCell));
        int count_column = countDotsInLine(Dots.X, map.getColumn(humanCell));
        int count_d1 = map.isD1(humanCell,dotsToWin) ? countDotsInLine(Dots.X, map.getD1(humanCell)) : 0;
        int count_d2 = map.isD2(humanCell,dotsToWin) ? countDotsInLine(Dots.X, map.getD2(humanCell)) : 0;

        int max = Math.max(Math.max(count_column,count_row),Math.max(count_d1,count_d2));
        while (max > 0) {
            if (map.getCountRow()!=map.getCountColumn() && count_row == max)
                if(isEmptyValidCellInLine(map.getRow(humanCell),humanCell)) return true;
                else count_row=0;
            if (count_d1 ==max)
                if(isEmptyValidCellInLine(map.getD1(humanCell),humanCell)) return true;
                else count_d1=0;
            if (count_d2 == max)
                if(isEmptyValidCellInLine(map.getD2(humanCell),humanCell)) return true;
                else count_d2=0;
            if (count_column == max)
                if(isEmptyValidCellInLine(map.getColumn(humanCell),humanCell)) return true;
                else count_column=0;
            if (count_row == max)
                if(isEmptyValidCellInLine(map.getRow(humanCell),humanCell)) return true;
                else count_row=0;
            max = Math.max(Math.max(count_column,count_row),Math.max(count_d1,count_d2));
        }
        return false;
    }

    public static void aiTurn() {
        if (aiCell == null || !map.getCell(aiCell).isEmptyCell()) {
            int x, y;
            do {
                x = rand.nextInt(map.getCountColumn());
                y = rand.nextInt(map.getCountRow());
            } while (map.getCell(x,y).getDot() != null);
            aiCell=map.getCell(x,y);
        }
        map.getCell(aiCell).setDot(Dots.O);
        System.out.println("Компьютер походил " + (aiCell.columnNumber + 1) + " " + (aiCell.rowNumber + 1));
    }
}

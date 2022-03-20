package TicTacToeGame.gameApp;

/*
"Соревнование" - игру идут подряд, пока пользователь не прервет
При создании "соревнования" определеям вид: с ИИ или другим игроком
(инициализируются игроки и судья)
В начале каждой игре:
- выбираем размер поля
- выбираем количество фишек для выгрыша
*/

import TicTacToeGame.gameApp.models.gamemap.Cell;
import TicTacToeGame.gameApp.models.gamemap.Dots;
import TicTacToeGame.gameApp.models.gamemap.GameMap;
import TicTacToeGame.gameApp.models.gamers.AIGamer;
import TicTacToeGame.gameApp.models.gamers.Gamer;
import TicTacToeGame.gameApp.models.gamers.HumanGamer;

import java.util.*;

public class Competition {

    private final Scanner sc;
    private final Gamer gamer1;
    private final Gamer gamer2;
    private final CompetitionJudge judge;
    private GameMap map;
    private int dots_to_win;

    public Competition() {
        sc = new Scanner(System.in);
        int mode = selectMode();
        this.gamer1 = new HumanGamer(1, introduceGamer(1), Dots.X);
        this.gamer2 = mode == 0 ? new HumanGamer(2, introduceGamer(2), Dots.O) :
                new AIGamer(2,"AI", Dots.O);
        this.judge = new CompetitionJudge(this);
        startNewCompetition();
    }

    public Scanner getSc() {
        return sc;
    }

    public Gamer getGamer1() {
        return gamer1;
    }

    public Gamer getGamer2() {
        return gamer2;
    }

    public CompetitionJudge getJudge() {
        return judge;
    }

    public GameMap getMap() {
        return map;
    }

    public int getDots_to_win() {
        return dots_to_win;
    }

    private void startNewCompetition() {
        while (true) {
            map = selectMap();
            dots_to_win = selectDotsToWin();
            judge.setMap(map);
            judge.setDots_to_win(dots_to_win);
            playNewRound();
            judge.printScore();
            judge.printScoreToFile();
            System.out.println("Продолжить игру? Y/N >>>");
            if (!sc.next().equalsIgnoreCase("y")) {
                System.out.println("Пока..");
                break;
            }
        }
        sc.close();
    }

    private void playNewRound() {
        judge.resetCountStep();
        judge.clearListStep();
        judge.resetWhoseMove();
        map.printMap();
        while (true) {
            if (judge.getWhoseMove() == 1) {
                if (!gamer1.doStep(this)) {
                    gamer2.incrementScore();
                    break;
                }
            } else {
                if (!gamer2.doStep(this)) {
                    gamer1.incrementScore();
                    break;
                }
            }
            judge.incrementCountStep();
            map.printMap();
            Cell lastCell = judge.getWhoseMove() == 1 ? gamer1.getCell() : gamer2.getCell();
            judge.addToListStep(lastCell);
            if (judge.isWin(lastCell)) {
                 break;
            }
            if (judge.isDraw()) {
                 break;
            }
            judge.changeWhoseMove();
        }
    }

    private int selectMode() {
        int mode = 0;
        try {
            System.out.println("Выбеерете режим игры. ");
            System.out.println("Введите 0, если хотите играть с другим игроком.");
            System.out.println("Введите 1, если хотите играть с ИИ >>>.");
            mode = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Вы ввели неправильный режим. Включен режим по умолчанию - 0!");
        }
        System.out.println("Установлен режим игры с " + (mode != 0 ? "ИИ!" : "другим игроком!"));
        System.out.println();
        sc.nextLine();
        return mode;
    }

    private String introduceGamer(int n) {
        System.out.println("Введите имя игрока " + n + " >>>");
        return sc.nextLine();
    }

    private GameMap selectMap() {
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
        return new GameMap(sizeX, sizeY);
    }

    private int selectDotsToWin() {
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

    private boolean isSizeNotValid(int size, int min, int max) {
        if (size >= min && size <= max) {
            return false;
        }
        System.out.println("Неверное значение");
        return true;
    }
}

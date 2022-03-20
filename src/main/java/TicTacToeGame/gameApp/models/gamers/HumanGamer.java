package TicTacToeGame.gameApp.models.gamers;

import TicTacToeGame.gameApp.Competition;
import TicTacToeGame.gameApp.models.gamemap.Dots;
import TicTacToeGame.gameApp.models.gamemap.GameMap;
import java.util.InputMismatchException;

public class HumanGamer extends Gamer {

    public HumanGamer(int id, String name, Dots dots) {
        super(id, name, dots);
    }

    @Override
    public boolean doStep(Competition competition) {
        int x, y;
        try {
            do {
                System.out.println("Введите координаты в формате X Y >>>");
                x = competition.getSc().nextInt() - 1;
                y = competition.getSc().nextInt() - 1;
            } while (!isValidEmpty(x, y, competition.getMap()));
        } catch (InputMismatchException e) {
            System.out.println("Вы прервали раунд!");
            System.out.println("Вам зачтено поражение!");
            competition.getSc().nextLine();
            return false;
        }
        competition.getMap().getCell(x, y).setDot(this.getDots());
        this.setCell(competition.getMap().getCell(x, y));
        return true;
    }

    private boolean isValidEmpty(int x, int y, GameMap map) {
        if (!map.isCellValid(x, y)) {
            System.out.println("Неверные координаты");
            return false;
        }
        if (map.getCell(x, y).isEmptyCell()) return true;
        System.out.println("Ячейка занята");
        return false;
    }
}

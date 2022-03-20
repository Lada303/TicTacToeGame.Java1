package TicTacToeGame.reconstructionApp;
/*
Производит непосредственно отрисовку процесса игры
В этом классе можно заменить на нужный конвектор координат
 */
import TicTacToeGame.reconstructionApp.convectors.CoordinateConvector;
import TicTacToeGame.reconstructionApp.convectors.MyCoordinateConvector;
import TicTacToeGame.reconstructionApp.models.Player;
import TicTacToeGame.reconstructionApp.models.Step;

import java.util.List;

public class ReconstructionGame {

    private String[][] map;
    private CoordinateConvector coordinateConvector;
    private Player player1;
    private Player player2;

    public void reconstruction(List<Object> listReadFile) {
        // Введите нужный конвектор коорлинат
        coordinateConvector = new MyCoordinateConvector();
        player1 = (Player) (listReadFile.remove(0));
        player2 = (Player) (listReadFile.remove(0));
        if (listReadFile.get(0) instanceof String) {
            String str = (String)(listReadFile.remove(0));
            map = new String[Integer.parseInt(str.substring(2))][Integer.parseInt(str.substring(0, 1))];
        } else {
            map = new String[3][3];
        }

        for (Object item : listReadFile) {
            if (item instanceof Step) { // print GameMap with step
                int[] xy = coordinateConvector.mapCoordinateConvector(((Step) item).getCellValue());
                map[xy[1]][xy[0]] = ((Step) item).getPlayerId().equals("1") ? player1.getSymbol() : player2.getSymbol();
                printMap();
                System.out.println();
            } else { // print GameResult
                if (item instanceof String) {
                    System.out.println(item);
                } else {
                    System.out.println("Player " + ((Player)item).getId() + " -> " + ((Player)item).getName() +
                            " is winner as '" + ((Player)item).getSymbol() + "'");
                }
                break;
            }
        }
    }

    private void printMap() {
        System.out.print("  | ");
        for (int i = 1; i <= map[1].length; i++) {
            System.out.print(i + " | ");
        }
        System.out.println();
        printHorizontalLine();
        for (int i = 0; i < map.length; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < map[1].length; j++) {
                System.out.print((map[i][j] == null  ? " " : map[i][j]) + " | ");
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

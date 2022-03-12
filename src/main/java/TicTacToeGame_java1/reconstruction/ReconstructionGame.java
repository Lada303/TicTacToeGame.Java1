package TicTacToeGame_java1.reconstruction;

import java.util.List;

public class ReconstructionGame {

    private String[][] map;
    private Player player1;
    private Player player2;
    private int x;
    private int y;

    protected void reconstruction(List<Object> listReadFile) {
        player1 = (Player) (listReadFile.remove(0));
        player2 = (Player) (listReadFile.remove(0));
        if (listReadFile.get(0) instanceof String) {
            String str = (String)(listReadFile.remove(0));
            map = new String[Integer.parseInt(str.substring(2))][Integer.parseInt(str.substring(0, 1))];
        } else {
            map = new String[3][3];
        }
        x = 0;
        y = 0;

        for (Object item : listReadFile) {
            if (item instanceof Step) {
                reconstructionStep(((Step) item));
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

    private void reconstructionStep(Step step) {
        String strMapCoordinate = step.getCellValue();
        if (strMapCoordinate.length() == 1) {
            mapCoordinateConvector(strMapCoordinate);
        } else {
            strMapCoordinate = strMapCoordinate.replaceAll("[^0-9]+", "");
            x = Integer.parseInt(strMapCoordinate.substring(0, 1)) - 1;
            y = Integer.parseInt(strMapCoordinate.substring(1)) - 1;
        }
        map[y][x] = step.getPlayerId().equals("1") ? player1.getSymbol() : player2.getSymbol();
        printMap();
        System.out.println();
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

    private void mapCoordinateConvector(String strMapCoordinate) {
        switch (strMapCoordinate) {
            case "1" -> {
                x = 0;
                y = 0;
            }
            case "2" -> {
                x = 1;
                y = 0;
            }
            case "3" -> {
                x = 2;
                y = 0;
            }
            case "4" -> {
                x = 0;
                y = 1;
            }
            case "5" -> {
                x = 1;
                y = 1;
            }
            case "6" -> {
                x = 2;
                y = 1;
            }
            case "7" -> {
                x = 0;
                y = 2;
            }
            case "8" -> {
                x = 1;
                y = 2;
            }
            case "9" -> {
                x = 2;
                y = 2;
            }
        }
    }
}

package TicTacToeGame_java1.reconstruction;

import java.util.List;

public class ReconstructionGame {

    private String[][] map;
    private Adapter adapterCoordinate;
    private Player player1;
    private Player player2;

    protected void reconstruction(List<Object> listReadFile) {
        adapterCoordinate = new MyAdapterCoordinate();
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
                String coordinate = adapterCoordinate.mapCoordinateConvector(((Step) item).getCellValue());
                int x = Integer.parseInt(coordinate.substring(0, 1)) - 1;
                int y = Integer.parseInt(coordinate.substring(1)) - 1;
                map[y][x] = ((Step) item).getPlayerId().equals("1") ? player1.getSymbol() : player2.getSymbol();
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

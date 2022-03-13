package TicTacToeGame_java1.reconstruction;

public class MyAdapterCoordinate implements Adapter {

    public int[] mapCoordinateConvector(String strMapCoordinate) {
        String str;
        if (strMapCoordinate.length() == 1) {
            str = convector1(strMapCoordinate);
        } else {
            str = strMapCoordinate.replaceAll("[^0-9]+", "");
        }
        int[] xy = new int[2];
        xy[0] = Integer.parseInt(str.substring(0, 1)) - 1;
        xy[1] = Integer.parseInt(str.substring(1)) - 1;
        return xy;
    }

    private String convector1 (String strMapCoordinate) {
        switch (strMapCoordinate) {
            case "1" -> {return "11";}
            case "2" -> {return "21";}
            case "3" -> {return "31";}
            case "4" -> {return "12";}
            case "5" -> {return "22";}
            case "6" -> {return "32";}
            case "7" -> {return "13";}
            case "8" -> {return "23";}
            case "9" -> {return "33";}
        }
        return "";
    }
}

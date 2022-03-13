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
            case "1" -> {return "00";}
            case "2" -> {return "10";}
            case "3" -> {return "20";}
            case "4" -> {return "01";}
            case "5" -> {return "11";}
            case "6" -> {return "21";}
            case "7" -> {return "02";}
            case "8" -> {return "12";}
            case "9" -> {return "22";}
        }
        return "";
    }
}

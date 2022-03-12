package TicTacToeGame_java1.reconstruction;

import java.io.File;
import java.util.Scanner;

public class startParser {

    public static void main(String[] args) {
        System.out.println("Введите имя xml-файла, например gameplay_1.xml >>> ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        File file = new File(fileName);
        while (!file.exists()) {
            System.out.println("Такого файла - " + fileName + " - не существует!");
            System.out.println("Введите имя xml-файла еще раз, например gameplay_1.xml >>> ");
            fileName = sc.nextLine();
            file = new File(fileName);
        }
        sc.close();

        new ReconstructionGame().reconstruction(new StaXParser().readConfig(fileName));
    }
}

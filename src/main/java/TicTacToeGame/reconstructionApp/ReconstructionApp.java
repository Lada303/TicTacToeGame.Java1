package TicTacToeGame.reconstructionApp;

/*
Точка входа.
!!! В этом классе происходит выбор класса для парсинга.
*/

import TicTacToeGame.reconstructionApp.parsers.JacksonParser;
import TicTacToeGame.reconstructionApp.parsers.StaXParser;
import java.io.File;
import java.util.Scanner;

public class ReconstructionApp {

    public static void main(String[] args) {
        System.out.println("Введите имя файла, например gameplay_1.xml >>> ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        File file = new File(fileName);
        while (!file.exists()) {
            System.out.println("Такого файла - " + fileName + " - не существует!");
            System.out.println("Введите имя файла еще раз, например gameplay_1.xml >>> ");
            fileName = sc.nextLine();
            file = new File(fileName);
        }
        sc.close();

        // Введите нужный класс для парсиннга файла
        new ReconstructionGame().reconstruction(new JacksonParser().readConfig(fileName));
    }
}

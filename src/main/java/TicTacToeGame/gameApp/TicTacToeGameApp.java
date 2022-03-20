package TicTacToeGame.gameApp;

public class TicTacToeGameApp {
    public static void main(String[] args) {
        helloInstruction();
        new Competition();
    }

    private static void helloInstruction() {
        System.out.println("Поиграем в крестики-нолики!");
        System.out.println("Вы вводите координаты в формате X Y через Пробел или Enter.");
        System.out.println("Если хотите прекратить игру - введите q вместо координат.\n");
    }
}

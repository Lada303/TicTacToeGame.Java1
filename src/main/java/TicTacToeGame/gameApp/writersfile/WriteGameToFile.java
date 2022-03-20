package TicTacToeGame.gameApp.writersfile;

/*
Общий интерфейс для записи файлов содержащих процесс игры
 */

@FunctionalInterface
public interface WriteGameToFile {
    void writeGameToFile(int countFiles, String mapSize, int winner);
}

package TicTacToeGame.gameApp.models.gamers;

/*
интерфейс, описывающий что может делать игрок
 */

import TicTacToeGame.gameApp.Competition;

public interface gamerDoing {
    boolean doStep(Competition competition);
}

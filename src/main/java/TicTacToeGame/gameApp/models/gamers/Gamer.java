package TicTacToeGame.gameApp.models.gamers;

/*
Абстрактный класс, описывающий общие свойства игроков
 */

import TicTacToeGame.gameApp.models.gamemap.Cell;
import TicTacToeGame.gameApp.Competition;
import TicTacToeGame.gameApp.models.gamemap.Dots;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

//@JsonRootName(value = "Player")
public abstract class Gamer implements gamerDoing{

    private final int id;
    private final String name;
    private final Dots dots;
    @JsonIgnore
    private Cell cell;
    @JsonIgnore
    private int score;

    public Gamer(int id, String name, Dots dots) {
        this.id = id;
        this.name = name;
        this.dots = dots;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonGetter("symbol")
    public Dots getDots() {
        return dots;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public abstract boolean doStep(Competition competition);

}
package TicTacToeGame.gameApp.writersfile;
/*
Записывает объект в json-фаил
!!! Структура записи не полностью соотвествует установленной
!!! Для автоматического чтения надо доработать парсер или сделать другой
*/
import TicTacToeGame.gameApp.models.gamers.Gamer;
import TicTacToeGame.writeparsegametag.GameTag;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.List;

@JsonRootName(value = "Gameplay")
public class JacksonWriter2 implements WriteGameToFile {

    private final Gamer gamer1;
    private final Gamer gamer2;
    private String mapSize;
    private final List<String> listStep;
    private int winner;
    @JsonIgnore
    private final ObjectMapper mapper;

    public JacksonWriter2(Gamer gamer1, Gamer gamer2, List<String> listStep) {
        this.gamer1 = gamer1;
        this.gamer2 = gamer2;
        this.listStep = listStep;
        this.mapper = new ObjectMapper();
    }

    @JsonGetter("Player1")
    public Gamer getGamer1() {
        return gamer1;
    }

    @JsonGetter("Player2")
    public Gamer getGamer2() {
        return gamer2;
    }

    @JsonGetter(GameTag.MAP)
    public String getMapSize() {
        return mapSize;
    }

    @JsonGetter(GameTag.STEP)
    public List<String> getListStep() {
        return listStep;
    }

    @JsonGetter(GameTag.RESULT)
    public int getWinner() {
        return winner;
    }

    @Override
    public void writeGameToFile(int countFiles, String mapSize, int winner) {
        this.mapSize = mapSize;
        this.winner = winner;
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(gamer1.getName()+"Vs" + gamer2.getName() + "_" + countFiles + ".json");
        try {
            mapper.writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


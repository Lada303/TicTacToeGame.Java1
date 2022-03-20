package TicTacToeGame.gameApp.writersfile;

/*
Записывает json-фаил используя Jackson, потоковую форму записи.
Записывает ход игры согласно установленной структуре
*/

import TicTacToeGame.gameApp.models.gamers.Gamer;
import TicTacToeGame.writeparsegametag.GameTag;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import java.io.*;
import java.util.List;

public class JacksonWriter implements WriteGameToFile{

    private final Gamer gamer1;
    private final Gamer gamer2;
    private final List<String> listStep;
    private JsonGenerator jsonGenerator;

    public JacksonWriter(Gamer gamer1, Gamer gamer2, List<String> listStep) {
        this.gamer1 = gamer1;
        this.gamer2 = gamer2;
        this.listStep = listStep;
    }

    @Override
    public void writeGameToFile(int countFiles, String mapSize, int winner) {
        try {
            writeJacksonDocument(gamer1.getName()+"Vs" + gamer2.getName() + "_" + countFiles + ".json", mapSize, winner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeJacksonDocument(String configFile, String mapSize,  int winner)
            throws Exception {
        jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream(configFile));
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

        jsonGenerator.writeStartObject();
        writePlayer(gamer1);
        writePlayer(gamer2);
        jsonGenerator.writeObjectField(GameTag.MAP, mapSize);
        writeSteps();
        jsonGenerator.writeObjectFieldStart(GameTag.RESULT);
        switch (winner){
            case 1 -> writePlayer(gamer1);
            case 2 -> writePlayer(gamer2);
            default -> jsonGenerator.writeStringField("result",GameTag.DRAW);
        }
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
        jsonGenerator.flush();
        jsonGenerator.close();
    }

    private void writePlayer(Gamer gamer) throws IOException {
        jsonGenerator.writeObjectFieldStart(GameTag.PLAYER);
        jsonGenerator.writeNumberField(GameTag.PLAYER_ID, gamer.getId());
        jsonGenerator.writeStringField(GameTag.PLAYER_NAME, gamer.getName());
        jsonGenerator.writeStringField(GameTag.PLAYER_SYMBOL, gamer.getDots().name());
        jsonGenerator.writeEndObject();
    }

    private void writeSteps() throws IOException {
        jsonGenerator.writeArrayFieldStart(GameTag.STEP);
        for (int i = 0; i < listStep.size(); i++) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField(GameTag.STEP_NUM,i + 1);
            jsonGenerator.writeStringField(GameTag.STEP_PLAYER_ID, i % 2 == 0 ? "1" : "2");
            jsonGenerator.writeStringField(GameTag.STEP_VALUE, listStep.get(i));
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}

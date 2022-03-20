package TicTacToeGame.gameApp.writersfile;

/*
Записывает xml-фаил используя StAx, потоковую форму записи XMLOutputFactory.
Записывает ход игры согласно установленной структуре
*/

import TicTacToeGame.gameApp.models.gamers.Gamer;
import TicTacToeGame.writeparsegametag.GameTag;

import javax.xml.stream.*;
import java.io.FileOutputStream;
import java.util.List;

public class StaxWriter2 implements WriteGameToFile{

    private final Gamer gamer1;
    private final Gamer gamer2;
    private final List<String> listStep;
    private XMLStreamWriter writer;

    public StaxWriter2(Gamer gamer1, Gamer gamer2, List<String> listStep) {
        this.gamer1 = gamer1;
        this.gamer2 = gamer2;
        this.listStep = listStep;
    }

    @Override
    public void writeGameToFile(int countFiles, String mapSize, int winner) {
        try {
            writeXMLDocument(gamer1.getName()+"Vs" + gamer2.getName() + "_" + countFiles + ".xml", mapSize, winner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeXMLDocument(String configFile, String mapSize,  int winner)
            throws Exception {
        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        writer = outputFactory.createXMLStreamWriter(new FileOutputStream(configFile));
        writer.writeStartDocument("1.0");
        writer.writeDTD("\n");
        writer.writeStartElement(GameTag.GAME_PLAY);
        writer.writeDTD("\n");

        gamerWriter(gamer1);
        gamerWriter(gamer2);
        gameMapWriter(mapSize);
        gameStepsWriter();
        resultWriter(winner);

        writer.writeEndDocument();
        writer.close();
    }

    private void gamerWriter(Gamer gamer) throws XMLStreamException {
        writer.writeDTD("\t");
        writer.writeEmptyElement(GameTag.PLAYER);
        writer.writeAttribute(GameTag.PLAYER_ID,  String.valueOf(gamer.getId()));
        writer.writeAttribute(GameTag.PLAYER_NAME,  gamer.getName());
        writer.writeAttribute(GameTag.PLAYER_SYMBOL,  gamer.getDots().toString());
        writer.writeDTD("\n");
    }

    private void gameMapWriter(String mapSize) throws XMLStreamException {
        writer.writeDTD("\t");
        writer.writeStartElement(GameTag.MAP);
        writer.writeCharacters(mapSize);
        writer.writeEndElement();
        writer.writeDTD("\n");
    }

    private void gameStepsWriter() throws XMLStreamException {
        writer.writeDTD("\t");
        writer.writeStartElement(GameTag.GAME);
        writer.writeDTD("\n");
        for (int i = 0; i < listStep.size(); i++) {
            stepWriter(String.valueOf(i + 1), i % 2 == 0 ? "1" : "2", listStep.get(i));
        }
        writer.writeDTD("\t");
        writer.writeEndElement();
        writer.writeDTD("\n");
    }

    private void stepWriter(String stepCounter, String numGamer, String cellStep)
            throws XMLStreamException {
        writer.writeDTD("\t\t");
        writer.writeStartElement(GameTag.STEP);
        writer.writeAttribute(GameTag.STEP_NUM, stepCounter);
        writer.writeAttribute(GameTag.STEP_PLAYER_ID, numGamer);
        writer.writeCharacters(cellStep);
        writer.writeEndElement();
        writer.writeDTD("\n");
    }

    private void resultWriter(int winner) throws XMLStreamException {
        writer.writeDTD("\t");
        writer.writeStartElement(GameTag.RESULT);
        if (winner == 0) {
            writer.writeCharacters(GameTag.DRAW);
        } else {
            writer.writeDTD("\n");
            writer.writeDTD("\t");
            gamerWriter(winner == 1 ? gamer1 : gamer2);
            writer.writeDTD("\t");
        }
        writer.writeEndElement();
        writer.writeDTD("\n");
    }
}

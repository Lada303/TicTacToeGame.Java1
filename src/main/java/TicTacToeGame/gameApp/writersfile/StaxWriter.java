package TicTacToeGame.gameApp.writersfile;

/*
Записывает xml-фаил используя StAx, потоковую форму записи eventFactory.
Записывает ход игры согласно установленной структуре
*/

import TicTacToeGame.gameApp.models.gamers.Gamer;
import TicTacToeGame.writeparsegametag.GameTag;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileOutputStream;
import java.util.List;

public class StaxWriter implements WriteGameToFile {

    private final Gamer gamer1;
    private final Gamer gamer2;
    private final List<String> listStep;

    private final XMLEventFactory eventFactory;
    private XMLEventWriter eventWriter;
    private final XMLEvent end;
    private final XMLEvent tab1;
    private final XMLEvent tab2;

    public StaxWriter(Gamer gamer1, Gamer gamer2, List<String> listStep) {
        this.gamer1 = gamer1;
        this.gamer2 = gamer2;
        this.listStep = listStep;

        this.eventFactory = XMLEventFactory.newFactory();
        this.end = eventFactory.createDTD("\n");
        this.tab1 = eventFactory.createDTD("\t");
        this.tab2 = eventFactory.createDTD("\t\t");
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
        eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(configFile));

        eventWriter.add(eventFactory.createStartDocument());
        eventWriter.add(end);
        eventWriter.add(eventFactory.createStartElement("","", GameTag.GAME_PLAY));
        eventWriter.add(end);

        gamerWriter(gamer1);
        gamerWriter(gamer2);
        gameMapWriter(mapSize);
        gameStepsWriter();
        resultWriter(winner);

        eventWriter.add(eventFactory.createEndElement("", "", GameTag.GAME_PLAY));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }

    private void gamerWriter(Gamer gamer) throws XMLStreamException {
        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createStartElement("", "", GameTag.PLAYER));
        eventWriter.add(eventFactory.createAttribute("id", String.valueOf(gamer.getId())));
        eventWriter.add(eventFactory.createAttribute("name", gamer.getName()));
        eventWriter.add(eventFactory.createAttribute("symbol", gamer.getDots().toString()));
        eventWriter.add(eventFactory.createEndElement("", "", GameTag.PLAYER));
        eventWriter.add(end);
    }

    private void gameMapWriter(String mapSize) throws XMLStreamException {
        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createStartElement("", "", GameTag.MAP));
        eventWriter.add(eventFactory.createCharacters(mapSize));
        eventWriter.add(eventFactory.createEndElement("", "", GameTag.MAP));
        eventWriter.add(end);
    }

    private void gameStepsWriter() throws XMLStreamException {
        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createStartElement("","", GameTag.GAME));
        eventWriter.add(end);
        for (int i = 0; i < listStep.size(); i++) {
            stepWriter(String.valueOf(i + 1), i % 2 == 0 ? "1" : "2", listStep.get(i));
        }
        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createEndElement("", "", GameTag.GAME));
        eventWriter.add(end);
    }

    private void stepWriter(String stepCounter, String numGamer, String cellStep)
            throws XMLStreamException {
        eventWriter.add(tab2);
        eventWriter.add(eventFactory.createStartElement("", "", GameTag.STEP));
        eventWriter.add(eventFactory.createAttribute(GameTag.STEP_NUM, stepCounter));
        eventWriter.add(eventFactory.createAttribute(GameTag.STEP_PLAYER_ID, numGamer));
        eventWriter.add(eventFactory.createCharacters(cellStep));
        eventWriter.add(eventFactory.createEndElement("", "", GameTag.STEP));
        eventWriter.add(end);
    }

    private void resultWriter(int winner) throws XMLStreamException {
        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createStartElement("","", GameTag.RESULT));
        if (winner == 0) {
            eventWriter.add(eventFactory.createCharacters(GameTag.DRAW));
        } else {
            eventWriter.add(end);
            eventWriter.add(tab1);
            gamerWriter(winner == 1 ? gamer1 : gamer2);
            eventWriter.add(tab1);
        }
        eventWriter.add(eventFactory.createEndElement("", "", GameTag.RESULT));
        eventWriter.add(end);
    }
}

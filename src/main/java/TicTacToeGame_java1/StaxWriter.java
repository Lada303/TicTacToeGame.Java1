package TicTacToeGame_java1;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileOutputStream;
import java.util.List;

public class StaxWriter {

    private final String configFile;
    private final XMLEventFactory eventFactory;
    private XMLEventWriter eventWriter;
    private final XMLEvent end;
    private final XMLEvent tab1;
    private final XMLEvent tab2;

    protected StaxWriter(String configFile) {
        this.configFile = configFile;
        eventFactory = XMLEventFactory.newInstance();
        end = eventFactory.createDTD("\n");
        tab1 = eventFactory.createDTD("\t");
        tab2 = eventFactory.createDTD("\t\t");
    }

    protected void writeXMLDocument(Gamer gamer1, Gamer gamer2, String mapSize, List<String> listStep, int winner)
            throws Exception {
        // create an XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // create XMLEventWriter
        eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(configFile));
        // create and write Start Tag
        eventWriter.add(eventFactory.createStartDocument());
        eventWriter.add(end);
        // create config open tag
        eventWriter.add(eventFactory.createStartElement("","", "Gameplay"));
        eventWriter.add(end);

        gamerWriter(gamer1);
        gamerWriter(gamer2);
        gameMapWriter(mapSize);

        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createStartElement("","", "Game"));
        eventWriter.add(end);
        for (int i = 0; i < listStep.size(); i++) {
            stepWriter(String.valueOf(i + 1), i % 2 == 0 ? "1" : "2", listStep.get(i));
        }
        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createEndElement("", "", "Game"));
        eventWriter.add(end);

        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createStartElement("","", "GameResult"));
        if (winner == 0) {
            eventWriter.add(eventFactory.createCharacters("Draw!"));
        } else {
            eventWriter.add(end);
            eventWriter.add(tab1);
            gamerWriter(winner == 1 ? gamer1 : gamer2);
            eventWriter.add(tab1);
        }
        eventWriter.add(eventFactory.createEndElement("", "", "GameResult"));
        eventWriter.add(end);

        eventWriter.add(eventFactory.createEndElement("", "", "Gameplay"));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }

    private void gamerWriter(Gamer gamer) throws XMLStreamException {
        // create Start node
        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createStartElement("", "", "Player"));
        // create Attribute
        eventWriter.add(eventFactory.createAttribute("id", String.valueOf(gamer.getId())));
        eventWriter.add(eventFactory.createAttribute("name", gamer.getName()));
        eventWriter.add(eventFactory.createAttribute("symbol", gamer.getDots().toString()));
        // create End node
        eventWriter.add(eventFactory.createEndElement("", "", "Player"));
        eventWriter.add(end);
    }

    private void gameMapWriter(String mapSize) throws XMLStreamException {
        // create Start node
        eventWriter.add(tab1);
        eventWriter.add(eventFactory.createStartElement("", "", "GameMap"));
        // create Content
        eventWriter.add(eventFactory.createCharacters(mapSize));
        // create End node
        eventWriter.add(eventFactory.createEndElement("", "", "Player"));
        eventWriter.add(end);
    }

    private void stepWriter(String stepCounter, String numGamer, String cellStep)
            throws XMLStreamException {
        // create Start node
        eventWriter.add(tab2);
        eventWriter.add(eventFactory.createStartElement("", "", "Step"));
        // create Attribute
        eventWriter.add(eventFactory.createAttribute("num", stepCounter));
        eventWriter.add(eventFactory.createAttribute("playerId", numGamer));
        // create Content
        eventWriter.add(eventFactory.createCharacters(cellStep));
        // create End node
        eventWriter.add(eventFactory.createEndElement("", "", "Step"));
        eventWriter.add(end);
    }

}

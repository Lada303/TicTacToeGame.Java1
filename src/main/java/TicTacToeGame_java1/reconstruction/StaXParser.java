package TicTacToeGame_java1.reconstruction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaXParser {
    private static final String PLAYER = "Player";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SYMBOL = "symbol";
    private static final String MAP = "GameMap";
    private static final String STEP = "Step";
    private static final String NUM = "num";
    private static final String PLAYER_ID = "playerId";
    private static final String RESULT = "GameResult";

    private final List<Object> list;
    private Object element;
    private XMLEventReader eventReader;

    {
        list = new ArrayList<>();
        element = null;
    }

    protected List<Object> readConfig(String configFile) {
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    readPlayer(startElement);
                    readMap(startElement);
                    readStep(startElement);
                    readResult(startElement);
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (!endElement.getName().getLocalPart().equals("Gameplay") &&
                            !endElement.getName().getLocalPart().equals("Game")) {
                        list.add(element);
                        element = null;
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void readPlayer(StartElement startElement) {
        if (startElement.getName().getLocalPart().equals(PLAYER)) {
            element = new Player();
            Iterator<Attribute> attributes = startElement.getAttributes();
            while (attributes.hasNext()) {
                Attribute attribute = attributes.next();
                if (attribute.getName().toString().equals(ID)) {
                    ((Player) element).setId(attribute.getValue());
                }
                if (attribute.getName().toString().equals(NAME)) {
                    ((Player) element).setName(attribute.getValue());
                }
                if (attribute.getName().toString().equals(SYMBOL)) {
                    ((Player) element).setSymbol(attribute.getValue());
                }
            }
        }
    }

    private void readMap(StartElement startElement) throws XMLStreamException {
        if (startElement.getName().getLocalPart().equals(MAP)) {
            XMLEvent event = eventReader.nextEvent();
            element = event.asCharacters().getData();
        }
    }

    private void readStep(StartElement startElement) throws XMLStreamException {
        if (startElement.getName().getLocalPart().equals(STEP)) {
            element = new Step();
            Iterator<Attribute> attributes = startElement.getAttributes();
            while (attributes.hasNext()) {
                Attribute attribute = attributes.next();
                if (attribute.getName().toString().equals(NUM)) {
                    ((Step) element).setNum(attribute.getValue());
                }
                if (attribute.getName().toString().equals(PLAYER_ID)) {
                    ((Step) element).setPlayerId(attribute.getValue());
                }
            }
            XMLEvent event = eventReader.nextEvent();
            ((Step) element).setCellValue(event.asCharacters().getData());
        }
    }

    private void readResult(StartElement startElement) throws XMLStreamException {
        if (startElement.getName().getLocalPart().equals(RESULT)) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isCharacters()) {
                element = event.asCharacters().getData();
            }
        }
    }
}

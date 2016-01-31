package pl.chelm.pwsz.harsh_crystal;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Class, that handles parsing of configuration files. Is not finished and can
 * not handle ill-formatted configuration file, neither it is well-optimized.
 * 
 * Is to be improved.
 * 
 * @author Vladyslav Bondarenko
 *
 */
class ConfigurationFileContentHandler implements ContentHandler
{
    enum ExpectedElements
    {
        board, builderClass, EMPTY, harshCrystal, height, name, period, populationRate, properties, property, quantityOfActorTypes, simulation, timer, type, value, width;
    }

    transient private Map<String, Object> boardProperties = null;
    transient private ExpectedElements currentElement = ExpectedElements.EMPTY;
    transient private String currentName;

    transient private Class<?> currentType;
    transient private ExpectedElements previousElement = ExpectedElements.EMPTY;

    transient private Map<String, Object> simulationProperties = null;
    transient private Map<String, Object> timerProperties = null;

    public ConfigurationFileContentHandler()
    {
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        /*
         * TODO Split parsing of board and simulation elements 
         * into different classes.
         */
        switch (currentElement)
        {
        case builderClass:
            try
            {
                this.simulationProperties.put(currentElement.name(),
                        Class.forName(new String(ch, start, length)));
            } catch (ClassNotFoundException e1)
            {
                throw new SAXException(e1);
            }
            break;

        case type:
            try
            {
                currentType = Class.forName(new String(ch, start, length));
            } catch (ClassNotFoundException e)
            {
                throw new SAXException(e);
            }
            break;

        case name:
            currentName = new String(ch, start, length);
            break;

        case value:
            try
            {
                this.simulationProperties.put(currentName,
                        currentType.getDeclaredMethod("valueOf", String.class)
                                .invoke(null, new String(ch, start, length)));
            } catch (Exception e)
            {
                throw new SAXException(e);
            }
            break;

        case width:
            this.boardProperties.put(currentElement.name(),
                    Integer.valueOf(new String(ch, start, length)));
            break;

        case height:
            this.boardProperties.put(currentElement.name(),
                    Integer.valueOf(new String(ch, start, length)));
            break;

        case populationRate:
            this.boardProperties.put(currentElement.name(),
                    Float.valueOf(new String(ch, start, length)));
            break;

        case quantityOfActorTypes:
            this.boardProperties.put(currentElement.name(),
                    Integer.valueOf(new String(ch, start, length)));
            break;

        case period:
            this.timerProperties.put(currentElement.name(),
                    Integer.valueOf(new String(ch, start, length)));
            break;

        default:
            break;
        }
    }

    @Override
    public void endDocument() throws SAXException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        System.out.println(String.format("<= %s", ExpectedElements.valueOf(localName)));
        this.previousElement = this.currentElement;
        this.currentElement = ExpectedElements.EMPTY;
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void processingInstruction(String target, String data)
            throws SAXException
    {
        // TODO Auto-generated method stub

    }

    public void setBoardProperties(Map<String, Object> boardProperties)
    {
        this.boardProperties = boardProperties;
    }

    @Override
    public void setDocumentLocator(Locator locator)
    {
        // TODO Auto-generated method stub

    }

    public void setSimulationProperties(
            Map<String, Object> simulationProperties)
    {
        this.simulationProperties = simulationProperties;
    }

    public void setTimerProperties(Map<String, Object> timerProperties)
    {
        this.timerProperties = timerProperties;
    }

    @Override
    public void skippedEntity(String name) throws SAXException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void startDocument() throws SAXException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException
    {
        try
        {
            this.currentElement = ExpectedElements.valueOf(localName);
        } catch (IllegalArgumentException e)
        {
            this.currentElement = ExpectedElements.EMPTY;
            throw new SAXException(String.format(
                    "Unknown element \"%s\" encountered.", currentElement));
        }
        System.out.println(String.format("=> %s", this.currentElement));
    }

    @Override
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException
    {
        // TODO Auto-generated method stub

    }

}

package pl.chelm.pwsz.harsh_crystal;

import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ConfigurationFileContentHandler implements ContentHandler {
	enum ExpectedElements {
		harshCrystal, 
		board, width, height, populationRate, quantityOfActorTypes,
		simulation, builderClass,
		properties, property, type, name, value, 
		timer, period,
		EMPTY;
	}
	
	transient private Map<String, Object> boardProperties      = null;
	transient private Map<String, Object> simulationProperties = null;
	transient private Map<String, Object> timerProperties      = null;
	
	transient private ExpectedElements    previousElement      = ExpectedElements.EMPTY;
	transient private ExpectedElements    currentElement       = ExpectedElements.EMPTY;
	
	transient private Class<?>            currentType;
	transient private String              currentName;
	
	public ConfigurationFileContentHandler() {}

	public void setBoardProperties(Map<String, Object> boardProperties) {
		this.boardProperties = boardProperties;
	}

	public void setSimulationProperties(Map<String, Object> simulationProperties) {
		this.simulationProperties = simulationProperties;
	}

	public void setTimerProperties(Map<String, Object> timerProperties) {
		this.timerProperties = timerProperties;
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		try {
			this.currentElement = ExpectedElements.valueOf(localName);	
		} catch (IllegalArgumentException e) {
			this.currentElement = ExpectedElements.EMPTY;
			throw new SAXException(String.format("Unknown element \"%s\" encountered.", currentElement));
		}
		System.out.println(String.format("=> %s", this.currentElement));
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println(String.format("<= %s", ExpectedElements.valueOf(localName)));
		this.previousElement = this.currentElement;
		this.currentElement = ExpectedElements.EMPTY;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//switch (previousElement) {
			//case simulation:
				switch (currentElement) {
					case builderClass:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						try {
							this.simulationProperties.put(currentElement.name(), Class.forName(new String(ch, start, length)));
						} catch (ClassNotFoundException e1) {
							throw new SAXException(e1);
						}
						break;
					
					//default:
						//break;
				//}
			//case property:
				//switch (currentElement) {
					case type:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						try {
							currentType = Class.forName(new String(ch, start, length));
						} catch (ClassNotFoundException e) {
							throw new SAXException(e);
						}
						break;
					
					case name:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						currentName = new String(ch, start, length);
						break;
						
					case value:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						try {
							this.simulationProperties.put(currentName, currentType.getDeclaredMethod("valueOf", String.class).invoke(null, new String(ch, start, length)));
						} catch (Exception e) {
							throw new SAXException(e);
						}
						break;
					
					//default:
						//break;
				//}
				//break;
				
			//case board:
				//switch (currentElement) {
					case width:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						this.boardProperties.put(currentElement.name(), Integer.valueOf(new String(ch, start, length)));
						break;
						
					case height:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						this.boardProperties.put(currentElement.name(), Integer.valueOf(new String(ch, start, length)));
						break;
						
					case populationRate:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						this.boardProperties.put(currentElement.name(), Float.valueOf(new String(ch, start, length)));
						break;
						
					case quantityOfActorTypes:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						this.boardProperties.put(currentElement.name(), Integer.valueOf(new String(ch, start, length)));
						break;
						
					//default:
						//break;
				//}
				//break;
				
			//case timer:
				//switch (currentElement) {
					case period:
						System.out.println(String.format("\"%s\"", new String(ch, start, length)));
						this.timerProperties.put(currentElement.name(), Integer.valueOf(new String(ch, start, length)));
						break;
						
					//default:
						//break;
				//}

			default:
				break;
		}
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

}

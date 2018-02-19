/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 *
 * @author toshiba
 */
public class XMLCreator {
    
    public static void writeXmlChat(List<Message> messages,String user_owner,String path){
        try {
            // create a XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

            // create XMLEventWriter
            XMLEventWriter eventWriter=null;
            try {
                eventWriter = outputFactory
                        .createXMLEventWriter(new FileOutputStream(path)); // "output.xml"
            } catch (FileNotFoundException ex) {
                Logger.getLogger(XMLCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

            // create a EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            XMLEvent tab = eventFactory.createDTD("\t");

            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();

            eventWriter.add(startDocument);
            // create open tag
            eventWriter.add(end);
            StartElement rssStart = eventFactory.createStartElement("", "", "myMessages");
            eventWriter.add(rssStart);
            eventWriter.add(end);
            Color c;
            for (Message msg : messages) {
                eventWriter.add(tab);
                eventWriter.add(eventFactory.createStartElement("", "", "message"));
                eventWriter.add(end);
                
                createNode(eventWriter, "user", user_owner);
                createNode(eventWriter, "sender", msg.getSender().getUsername());
                int index=0;
                if(msg.getReceiver().getUsers().get(index).getUsername().equals(msg.getSender().getUsername())){
                    index = 1;
                }
                createNode(eventWriter, "receiver", msg.getReceiver().getUsers().get(index).getUsername());
                createNode(eventWriter, "content", msg.getMessageContent());
                //System.out.println(msg.getColor().toString());
                //System.out.println(msg.getColor());
                
                createNode(eventWriter, "color", "#FF7F50");
                createNode(eventWriter, "fontSize", Integer.toString(msg.getFontSize()));
                createNode(eventWriter, "fontWeight", msg.getFontWeight().name());
                createNode(eventWriter, "fontStyle", msg.getFontStyle().name());
                eventWriter.add(tab);
                eventWriter.add(eventFactory.createEndElement("", "", "message"));
                eventWriter.add(end);

            }

            eventWriter.add(eventFactory.createEndElement("", "", "myMessages"));

            eventWriter.add(end);

            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
            
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream("output.xml"));

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("schema.xsd"));

            Validator validator = schema.newValidator();
            validator.validate(new StAXSource(reader));

            //no exception thrown, so valid
            System.out.println("Document is valid");
            
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XMLCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t\t");
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

}

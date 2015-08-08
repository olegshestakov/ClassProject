package org.sourceit.parsers.xml.sax;

import com.sun.org.apache.xerces.internal.impl.PropertyManager;
import com.sun.xml.internal.stream.writers.XMLStreamWriterImpl;
import org.sourceit.entities.Person;
import org.sourceit.parse.Parseable;
import org.sourceit.utils.EntityUtil;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// used only for Person.class
public class SaxParser<T extends Person> implements Parseable<T> {

    private File file;
    private T tempEntity;
    private List<T> entityCache = new ArrayList<>(10);

    public SaxParser(File file, T entity) {
        this.file = file;
        this.tempEntity = entity;
        this.handler = new EntityHandler();
        entityCache.addAll(parseEntity());
    }

    private EntityHandler handler;
    private String properties;

    private T createEntity(String entityParams) {

        String[] params = entityParams.split(",");

        if (params.length >= 3) {
            this.tempEntity = EntityUtil.createEntity(params, tempEntity);
        } else {
            System.err.println("Wrong number of arguments");
            throw new IllegalArgumentException("Wrong number of arguments. File -- " + this.file);
        }

        return tempEntity;
    }


    @Override
    public List<T> parseEntity() {

        T localEntity;
        List<T> localPersons = new ArrayList<>(10);

        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(this.handler);
            reader.parse(this.file.toString());

            String[] personParams = properties.split("\n");

            for (String param : personParams) {
                if (param.length() != 0) {
                    localEntity = createEntity(param);
                    localPersons.add(localEntity);
                }
            }

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        return localPersons;
    }

    //TODO HW: XML
    //TODO Use XMLStreamWriter
    @Override
    public void writeEntity(T entity) {
//        entityCache.add(entity);
//        try {
//            XMLStreamWriter writer = new XMLStreamWriterImpl(new FileWriter("temp.xml"), new PropertyManager(2));
//            writer.writeStartDocument("utf-8", "1.0");
//            writer.writeStartElement("my", "persons", "http://www.example.com/Persons");
//
//            for (T tempEntity : entityCache) {
//                String[] params = tempEntity.toString().split(",");
//
//                writer.writeStartElement("person");
//
//                writer.writeStartElement("name");
//                writer.writeCharacters(params[0]);
//                writer.writeEndElement();
//
//                writer.writeStartElement("age");
//                writer.writeCharacters(params[1]);
//                writer.writeEndElement();
//
//                writer.writeStartElement("gender");
//                writer.writeCharacters(params[2]);
//                writer.writeEndElement();
//
//                writer.writeEndElement();
//
//            }
//
//            writer.writeEndElement();
//            writer.writeEndDocument();
//
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XMLStreamException e) {
//            e.printStackTrace();
//        }
    }

    private class EntityHandler implements ContentHandler {

        private List<String> properties = new ArrayList<>();

        public EntityHandler() {
        }

        @Override
        public void setDocumentLocator(Locator locator) {
        }

        @Override
        public void startDocument() throws SAXException {
        }

        @Override
        public void endDocument() throws SAXException {
            StringBuffer buffer = new StringBuffer("");

            for (String property : properties) {
                buffer.append(property);
            }

            SaxParser.this.properties = buffer.toString();
        }

        @Override
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
        }

        @Override
        public void endPrefixMapping(String prefix) throws SAXException {
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (localName.equals("person") ||
                    localName.equals("teacher") ||
                    localName.equals("student") ||
                    localName.equals("college_student")
                    ) {
                properties.add("\n");
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String temp = new String(ch, start, length).trim();
            if ((!temp.equals("\n") ||
                    !temp.equals("\r") ||
                    !temp.equals("\r\n") ||
                    !temp.equals("\n\r")) && (temp.length() > 0)
                    ) {
                properties.add(temp);
                properties.add(",");
            }
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

        }

        @Override
        public void processingInstruction(String target, String data) throws SAXException {

        }

        @Override
        public void skippedEntity(String name) throws SAXException {

        }
    }
}

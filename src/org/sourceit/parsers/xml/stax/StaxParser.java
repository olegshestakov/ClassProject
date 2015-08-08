package org.sourceit.parsers.xml.stax;

import org.sourceit.entities.Person;
import org.sourceit.parse.Parseable;
import org.sourceit.utils.EntityUtil;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaxParser<T extends Person> implements Parseable<T> {


    private File file;
    private T tempEntity;
    private List<T> entityCache = new ArrayList<>(10);

    public StaxParser(File file, T entity) {
        this.file = file;
        this.tempEntity = entity;
        entityCache.addAll(parseEntity());
    }

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

        try (FileReader fileReader = new FileReader(this.file)) {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder(1000);
            while (reader.hasNext()) {

                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    String property = reader.getLocalName();
                    switch (property) {
                        case "name":
                        case "age":
                        case "gender":
                            stringBuilder.append(reader.getElementText()).append(",");
                            if (property.equals("gender")) {
                                stringBuilder.append("\n");
                            }
                            break;
                    }
                }
            }

            String[] params = stringBuilder.toString().split("\n");

            for (int i = 0; i < params.length; i++) {
                localPersons.add(createEntity(params[i]));
            }

        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }

        return localPersons;
    }

    // TODO: HW
    // TODO: Use XMLStreamWriter
    @Override
    public void writeEntity(T entity) {

    }
}

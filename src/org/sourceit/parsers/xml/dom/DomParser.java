package org.sourceit.parsers.xml.dom;

import org.sourceit.entities.Person;
import org.sourceit.parse.Parseable;
import org.sourceit.utils.EntityUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// used only for Person.class
public class DomParser<T extends Person> implements Parseable<T> {

    private File file;
    private T tempEntity;
    private List<T> entityCache = new ArrayList<>(10);

    public DomParser(File file, T entity) {
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

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(this.file);

            Element root = document.getDocumentElement();
            root.normalize();

            NodeList personList = root.getElementsByTagName("person");

            StringBuilder stringBuilder = new StringBuilder(1000);
            for (int i = 0; i < personList.getLength(); i++) {
                NodeList children = personList.item(i).getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        String properties = children.item(j).getNodeName();
                        switch (properties) {
                            case "name":
                            case "age":
                            case "gender":
                                stringBuilder.append(children.item(j).getTextContent()).append(",");
                                break;
                        }
                    }
                }
                stringBuilder.append("\n");
            }

            String[] params = stringBuilder.toString().split("\n");

            for (int i = 0; i < params.length; i++) {
                tempEntity = createEntity(params[i]);
                localPersons.add(tempEntity);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return localPersons;
    }

    // TODO: HW
    // TODO: Use DocumentBuilder
    @Override
    public void writeEntity(T entity) {

    }

}

package org.sourceit.parsers.serialize;

import org.sourceit.entities.Person;
import org.sourceit.parse.Parseable;
import org.sourceit.utils.EntityUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeParser<T extends Person> implements Parseable<T> {

    private File file;
    private T tempEntity;
    private List<T> entityCache = new ArrayList<>(10);

    public SerializeParser(File file, T entity) {
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

        List<T> localPersons = new ArrayList<>(10);

        T localEntity;

        try(ObjectInputStream readStream = new ObjectInputStream(new FileInputStream(this.file))) {

            int res;

            String resultString = "";

            while ((res = readStream.read()) != -1) {
                resultString = resultString + (char) res;
            }

            String[] personParams = resultString.split("\n");

            for (String param : personParams) {
                if (param.length() != 0) {
                    localEntity = createEntity(param);
                    localPersons.add(localEntity);
                }
            }
        } catch (IOException e) {

        }

        return localPersons;
    }

    @Override
    public void writeEntity(T entity) {

        try(ObjectOutputStream writeStream = new ObjectOutputStream(new FileOutputStream(this.file))) {
            for (T iterEntity : entityCache) {
                writeStream.write(iterEntity.toString().getBytes());
                writeStream.write("\n".getBytes());
            }
            writeStream.flush();
        } catch (IOException e) {

        }


    }
}

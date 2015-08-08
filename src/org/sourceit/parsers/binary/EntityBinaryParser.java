package org.sourceit.parsers.binary;


import org.sourceit.entities.Person;
import org.sourceit.entities.Teacher;
import org.sourceit.parse.Parseable;
import org.sourceit.utils.EntityUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EntityBinaryParser<T extends Person> implements Parseable<T> {

    private List<T> entityCache = new ArrayList<>(10);
    private File file;
    private T tempEntity;

    public EntityBinaryParser(File file, T entity) {
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

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(this.file))) {

            int res;

            String resultString = "";

            while ((res = stream.read()) != -1) {
                resultString = resultString + (char) res;
            }

            String[] personParams = resultString.split("\n");

            for (String param : personParams) {
                localEntity = createEntity(param);
                localPersons.add(localEntity);
            }


        } catch (IOException e) {
            System.err.println("Unexpected error -> " + e.getMessage());
        }

        return localPersons;
    }

    @Override
    public void writeEntity(T entity) {
        entityCache.add(entity);

        try (BufferedOutputStream writeStream = new BufferedOutputStream(new FileOutputStream(this.file))) {

            for (T iterEntity : entityCache) {
                writeStream.write(iterEntity.toString().getBytes());
                writeStream.write("\n".getBytes());
            }
            writeStream.flush();
        } catch (IOException e) {
            System.err.println("Unexpected error -> " + e.getMessage());
        }
    }
}

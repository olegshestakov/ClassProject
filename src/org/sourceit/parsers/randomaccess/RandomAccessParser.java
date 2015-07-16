package org.sourceit.parsers.randomaccess;

import org.sourceit.entities.Person;
import org.sourceit.parse.Parseable;
import org.sourceit.utils.EntityUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RandomAccessParser<T extends Person> implements Parseable<T> {

    private File file;
    private T tempEntity;
    private List<T> entityCache = new ArrayList<>(10);

    public RandomAccessParser(File file, T entity) {
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

        try (RandomAccessFile randomAccess = new RandomAccessFile(this.file, "rw")) {

            int res;

            String resultString = "";

            while ((res = randomAccess.read()) != -1) {
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
            System.err.println("Unexpected error -> " + e.getMessage());
        }

        return localPersons;
    }

    @Override
    public void writeEntity(T entity) {
        try (RandomAccessFile randomAccess = new RandomAccessFile(this.file, "rw")) {

            int byteLength = 0;

            for (T iterEntity : entityCache) {
                byteLength += iterEntity.toString().getBytes().length + "\n".getBytes().length;
            }

            randomAccess.skipBytes(byteLength);
            randomAccess.writeBytes(entity.toString());
            randomAccess.writeBytes("\n");
        } catch (IOException e) {
            System.err.println("Unexpected error -> " + e.getMessage());
        }
        entityCache.add(entity);
    }
}

package org.sourceit.parsers.sign;

import org.sourceit.entities.Person;
import org.sourceit.entities.Student;
import org.sourceit.parse.Parseable;
import org.sourceit.properties.Gender;
import org.sourceit.utils.EntityUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class EntitySignParser<T extends Person> implements Parseable<T> {

    private File file;
    private T tempEntity;
    private List<T> entityCache = new ArrayList<>(100);

    /**
     * Constructor, sets file and initialize cache
     *
     * @param file -- source of persons.
     */
    public EntitySignParser(File file, T entity) {
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

        try (FileReader fileReader = new FileReader(this.file)) {
            if (fileReader.ready()) {
                int res;

                String resultString = "";

                while ((res = fileReader.read()) != -1) {
                    resultString = resultString + (char) res;
                }

                String[] personParams = resultString.split("\n");

                for (String param : personParams) {
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
        entityCache.add(entity);

        try (FileWriter fileWriter = new FileWriter(this.file)) {

            for (T iterEntity : entityCache) {
                fileWriter.write(iterEntity.toString());
                fileWriter.write("\n");
            }
            fileWriter.flush();
        } catch (IOException e) {
            System.err.println("Unexpected error -> " + e.getMessage());
        }
    }
}

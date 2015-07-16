package org.sourceit.parsers.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sourceit.entities.Person;
import org.sourceit.parse.Parseable;
import org.sourceit.utils.EntityUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonParser<T extends Person> implements Parseable<T> {

    private File file;
    private T tempEntity;
    private List<T> entityCache = new ArrayList<>(100);

    public JsonParser(File file, T entity) {
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

        try (FileReader reader = new FileReader(this.file)) {

            JSONParser jsonParser = new JSONParser();

            JSONObject object = (JSONObject) jsonParser.parse(reader);

            JSONArray jsonArray = (JSONArray) object.get("entities");

            for (Object params : jsonArray) {
                JSONObject temp = (JSONObject) params;
                List<String> stringBuffer = new ArrayList<>();
                for (Object s : temp.keySet()) {
                    stringBuffer.add((String) temp.get(s));
                }

                Collections.reverse(stringBuffer);

                StringBuffer buffer = new StringBuffer();

                int length = 0;
                for (String s : stringBuffer) {
                    buffer.append(s);
                    if (length != stringBuffer.size() - 1) {
                        buffer.append(",");
                    }
                    length++;
                }

                localEntity = createEntity(buffer.toString());
                localPersons.add(localEntity);
            }

        } catch (IOException | ParseException e) {

        }

        return localPersons;
    }

    @Override
    public void writeEntity(T entity) {

        entityCache.add(entity);

        try (FileWriter writer = new FileWriter(this.file)) {

            JSONObject jsonObject = new JSONObject();

            JSONArray jsonArray = new JSONArray();

            for (T temp : entityCache) {
                JSONObject tempJson = new JSONObject();
                String[] params = entity.toString().split(",");
                int i = 0;
                for (String property : entity.getProperties().split(",")) {
                    tempJson.put(property, params[i]);
                    i++;
                }
                jsonArray.add(tempJson);
            }

            jsonObject.put("entities", jsonArray);

            jsonObject.writeJSONString(writer);
        } catch (IOException e) {

        }
    }
}

package org.sourceit.parse;

import org.sourceit.entities.Person;

import java.util.List;

public interface Parseable<T> {

    List<T> parseEntity();

    void writeEntity(T entity);

}

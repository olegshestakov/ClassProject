package org.sourceit;

import org.sourceit.entities.Person;
import org.sourceit.parse.Parseable;
import org.sourceit.parsers.xml.sax.SaxParser;

import java.io.File;

public class Main {


    public static void main(String[] args) {

        Parseable<Person> parseable =
                new SaxParser<Person>(new File("person.xml"), new Person());

        System.out.println(parseable.parseEntity());
    }

}
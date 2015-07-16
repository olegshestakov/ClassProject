package org.sourceit.properties;

public enum Subject {

    CS("Computer science", "CS"), CH("Chemistry", "CH"), EN("English", "EN"),
    EE("Electrical Engineering", "EE"), CM("Communications", "CM");

    private String subject;
    private String abbr;

    Subject(String subject, String abbreviation) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return name();
    }
}

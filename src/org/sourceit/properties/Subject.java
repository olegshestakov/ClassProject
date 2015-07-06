package org.sourceit.properties;

public enum Subject {

    CS("Computer science"), CH("Chemistry"), EN("English"),
    EE("Electrical Engineering"), CM("Communications");

    private String subject;

    Subject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}

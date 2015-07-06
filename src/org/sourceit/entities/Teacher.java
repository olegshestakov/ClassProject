package org.sourceit.entities;

import org.sourceit.properties.Gender;
import org.sourceit.properties.Subject;

public class Teacher extends Person {

    protected double salary;
    protected Subject subject;

    public Teacher() {
        this.salary = 1000.00;
        this.subject = Subject.CS;
    }

    public Teacher(double salary, Subject subject) {
        this.salary = salary;
        this.subject = subject;
    }

    public Teacher(String name, int age, Gender gender, Subject subject, double salary) {
        super(name, age, gender);
        this.salary = salary;
        this.subject = subject;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    @Override
    public Gender getGender() {
        return super.getGender();
    }

    @Override
    public void setGender(Gender gender) {
        super.setGender(gender);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{{" + super.toString() + "}" +
                "salary=" + salary +
                ", subject=" + subject +
                '}';
    }
}

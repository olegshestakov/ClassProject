package org.sourceit.entities;

import org.sourceit.properties.Gender;

public class Student extends Person {

    protected String idNumber;
    protected double gpa;

    public Student() {
        this.idNumber = "000001";
        this.gpa = 3.0;
    }

    public Student(String idNumber, double gpa) {
        this.idNumber = idNumber;
        this.gpa = gpa;
    }

    public Student(String name, int age, Gender gender, String idNumber, double gpa) {
        super(name, age, gender);
        this.idNumber = idNumber;
        this.gpa = gpa;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{{" + super.toString() + "}" +
                "idNumber='" + idNumber + '\'' +
                ", gpa=" + gpa +
                '}';
    }
}

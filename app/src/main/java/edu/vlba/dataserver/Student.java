package edu.vlba.dataserver;

public class Student {
    private String Id;
    private String name;
    private String phone;

    public Student(String id, String name, String phone) {
        Id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}

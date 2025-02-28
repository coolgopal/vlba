package edu.vlba.dataserver;

public class Student {
    private String Id;
    private String name;
    private String phone;
    private String studentclass;

    public Student(String id, String name, String phone, String studentclass) {
        Id = id;
        this.name = name;
        this.phone = phone;
        this.studentclass = studentclass;
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
    public String getStudentClass() { return studentclass; }
}

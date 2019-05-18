package com.ydhnwb.arrangeit.models;

public class StudentModel {
    private String nim;
    private String email;
    private String name;
    private String name_idiomatic;
    private String key;
    private int semester;

    public StudentModel() { }

    public StudentModel(String nim, String email, String name, String name_idiomatic, String key, int semester) {
        this.nim = nim;
        this.email = email;
        this.name = name;
        this.name_idiomatic = name_idiomatic;
        this.key = key;
        this.semester = semester;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_idiomatic() {
        return name_idiomatic;
    }

    public void setName_idiomatic(String name_idiomatic) {
        this.name_idiomatic = name_idiomatic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}

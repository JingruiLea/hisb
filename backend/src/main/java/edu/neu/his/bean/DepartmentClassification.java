package edu.neu.his.bean;


public class DepartmentClassification {
    private int id;
    private String name;

    public DepartmentClassification(int id,String name) {
        this.setId(id);
        this.setName(name);
    }

    public DepartmentClassification(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

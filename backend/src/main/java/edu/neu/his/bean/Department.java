package edu.neu.his.bean;

public class Department {
    private int id;
    private String pinyin;
    private String name;
    private String type;
    private int classification_id;
    private String classification_name;

    public Department(int id,String pinyin,String name,String type,int classification_id) {
        this.id = id;
        this.pinyin = pinyin;
        this.name = name;
        this.type = type;
        this.classification_id = classification_id;
    }

    public Department(int id,String pinyin,String name,String type,String classification_name,int classification_id) {
        this.id = id;
        this.pinyin = pinyin;
        this.name = name;
        this.type = type;
        this.classification_name = classification_name;
        this.classification_id = classification_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClassification_id(int classification_id) {this.classification_id = classification_id;}

    public int getClassification_id() {return classification_id;}

    public String getClassification_name() {
        return classification_name;
    }

    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }
}

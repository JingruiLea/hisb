
package edu.neu.his.bean;

public class NonDrugChargeItem {
    private String id;
    private String pinyin;
    private String format;
    private String name;
    private float fee;
    private int expense_classification_id;
    private int department_id;

    private String expense_classification_name;
    private String department_name;

    public NonDrugChargeItem(String id,String pinyin,String format,String name,float fee,int expense_classification_id,int department_id) {
        this.id = id;
        this.pinyin = pinyin;
        this.format = format;
        this.name = name;
        this.fee = fee;
        this.expense_classification_id = expense_classification_id;
        this.department_id = department_id;
    }

    public NonDrugChargeItem() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getExpense_classification_id() {
        return expense_classification_id;
    }

    public void setExpense_classification_id(int expense_classification_id) {
        this.expense_classification_id = expense_classification_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getExpense_classification_name() {
        return expense_classification_name;
    }

    public void setExpense_classification_name(String expense_classification_name) {
        this.expense_classification_name = expense_classification_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
}
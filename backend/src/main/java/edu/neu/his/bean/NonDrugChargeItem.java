
package edu.neu.his.bean;

public class NonDrugChargeItem {
    private int id;
    private String code;
    private String format;
    private String pinyin;
    private String name;
    private float fee;
    private int expense_classification_id;
    private int department_id;
    private String fee_name;
    private String department;

    public NonDrugChargeItem(String code, String format, String pinyin, String name, float fee, int expense_classification_id, int department_id){
        this.setCode(code);
        this.setDepartment_id(department_id);
        this.setExpense_classification_id(expense_classification_id);
        this.setFee(fee);
        this.setFormat(format);
        this.setName(name);
        this.setPinyin(pinyin);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getExpense_classification_id() {
        return expense_classification_id;
    }

    public void setExpense_classification_id(int expense_classification_id) {
        this.expense_classification_id = expense_classification_id;
    }

    public String getFee_name() {
        return fee_name;
    }

    public void setFee_name(String fee_name) {
        this.fee_name = fee_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
package edu.neu.his.bean;

public class BillRecord {
    private int id;
    private int medical_record_id;
    private int expense_classification_id;
    private String type;//缴费退费
    private int print_status;
    private float cost;
    private float should_pay;
    private float truely_pay;
    private float retail_fee;
    private int user_id;
    private String creat_time;
    private String expense_classification;

    public BillRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedical_record_id() {
        return medical_record_id;
    }

    public void setMedical_record_id(int medical_record_id) {
        this.medical_record_id = medical_record_id;
    }

    public int getExpense_classification_id() {
        return expense_classification_id;
    }

    public void setExpense_classification_id(int expense_classification_id) {
        this.expense_classification_id = expense_classification_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrint_status() {
        return print_status;
    }

    public void setPrint_status(int print_status) {
        this.print_status = print_status;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getShould_pay() {
        return should_pay;
    }

    public void setShould_pay(float should_pay) {
        this.should_pay = should_pay;
    }

    public float getTruely_pay() {
        return truely_pay;
    }

    public void setTruely_pay(float truely_pay) {
        this.truely_pay = truely_pay;
    }

    public float getRetail_fee() {
        return retail_fee;
    }

    public void setRetail_fee(float retail_fee) {
        this.retail_fee = retail_fee;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public String getExpense_classification() {
        return expense_classification;
    }

    public void setExpense_classification(String expense_classification) {
        this.expense_classification = expense_classification;
    }
}

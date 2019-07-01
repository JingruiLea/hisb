package edu.neu.his.bean.dailyCheck;

/**
 * 该类代表各科室/医生票据总额
 */
public class ObjectSum {
    private String name;
    private String fee_name;
    private Float sum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFee_name() {
        return fee_name;
    }

    public void setFee_name(String fee_name) {
        this.fee_name = fee_name;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }
}

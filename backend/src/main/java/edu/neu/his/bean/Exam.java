package edu.neu.his.bean;

import java.io.Serializable;

public class Exam implements Serializable {
    private Integer id;

    private Integer medicalRecordId;

    private Integer nonDrugItemId;

    private Integer type;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(Integer medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public Integer getNonDrugItemId() {
        return nonDrugItemId;
    }

    public void setNonDrugItemId(Integer nonDrugItemId) {
        this.nonDrugItemId = nonDrugItemId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", medicalRecordId=").append(medicalRecordId);
        sb.append(", nonDrugItemId=").append(nonDrugItemId);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
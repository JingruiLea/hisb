package edu.neu.his.bean;

import java.io.Serializable;

public class OutpatientChargesRecord implements Serializable {
    private Integer id;

    private Integer medicalRecordId;

    private Integer itemId;

    private String type;

    private String status;

    private Integer quantity;

    private Float cost;

    private Integer excuteDepartmentId;

    private Integer tollCollectorId;

    private String creatTime;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getExcuteDepartmentId() {
        return excuteDepartmentId;
    }

    public void setExcuteDepartmentId(Integer excuteDepartmentId) {
        this.excuteDepartmentId = excuteDepartmentId;
    }

    public Integer getTollCollectorId() {
        return tollCollectorId;
    }

    public void setTollCollectorId(Integer tollCollectorId) {
        this.tollCollectorId = tollCollectorId;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime == null ? null : creatTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", medicalRecordId=").append(medicalRecordId);
        sb.append(", itemId=").append(itemId);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", quantity=").append(quantity);
        sb.append(", cost=").append(cost);
        sb.append(", excuteDepartmentId=").append(excuteDepartmentId);
        sb.append(", tollCollectorId=").append(tollCollectorId);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
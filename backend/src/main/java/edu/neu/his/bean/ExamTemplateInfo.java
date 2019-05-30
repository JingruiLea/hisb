package edu.neu.his.bean;

import java.io.Serializable;

public class ExamTemplateInfo implements Serializable {
    private Integer id;

    private Integer examTemplateId;

    private Integer nonDrugItemId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamTemplateId() {
        return examTemplateId;
    }

    public void setExamTemplateId(Integer examTemplateId) {
        this.examTemplateId = examTemplateId;
    }

    public Integer getNonDrugItemId() {
        return nonDrugItemId;
    }

    public void setNonDrugItemId(Integer nonDrugItemId) {
        this.nonDrugItemId = nonDrugItemId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", examTemplateId=").append(examTemplateId);
        sb.append(", nonDrugItemId=").append(nonDrugItemId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
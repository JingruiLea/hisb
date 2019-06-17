package edu.neu.his.bean.prescription;

import java.io.Serializable;

public class PrescriptionTemplateItem implements Serializable {
    private Integer id;

    private Integer prescription_template_id;

    private Integer drug_id;

    private Integer amount;

    private String note;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrescription_template_id() {
        return prescription_template_id;
    }

    public void setPrescription_template_id(Integer prescription_template_id) {
        this.prescription_template_id = prescription_template_id;
    }

    public Integer getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(Integer drug_id) {
        this.drug_id = drug_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", prescription_template_id=").append(prescription_template_id);
        sb.append(", drug_id=").append(drug_id);
        sb.append(", amount=").append(amount);
        sb.append(", note=").append(note);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
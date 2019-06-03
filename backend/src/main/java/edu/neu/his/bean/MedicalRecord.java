package edu.neu.his.bean;

import java.io.Serializable;

public class MedicalRecord implements Serializable {
    private Integer id;

    private String createTime;

    private String status;

    private String chiefComplaint;

    private String currentMedicalHistory;

    private String currentTreatmentSituation;

    private String pastHistory;

    private String allergyHistory;

    private String physicalExamination;

    private String westernInitialDiagnosis;

    private String chineseInitialDiagnosis;

    private String endDiagnosis;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint == null ? null : chiefComplaint.trim();
    }

    public String getCurrentMedicalHistory() {
        return currentMedicalHistory;
    }

    public void setCurrentMedicalHistory(String currentMedicalHistory) {
        this.currentMedicalHistory = currentMedicalHistory == null ? null : currentMedicalHistory.trim();
    }

    public String getCurrentTreatmentSituation() {
        return currentTreatmentSituation;
    }

    public void setCurrentTreatmentSituation(String currentTreatmentSituation) {
        this.currentTreatmentSituation = currentTreatmentSituation == null ? null : currentTreatmentSituation.trim();
    }

    public String getPastHistory() {
        return pastHistory;
    }

    public void setPastHistory(String pastHistory) {
        this.pastHistory = pastHistory == null ? null : pastHistory.trim();
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory == null ? null : allergyHistory.trim();
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination == null ? null : physicalExamination.trim();
    }

    public String getWesternInitialDiagnosis() {
        return westernInitialDiagnosis;
    }

    public void setWesternInitialDiagnosis(String westernInitialDiagnosis) {
        this.westernInitialDiagnosis = westernInitialDiagnosis == null ? null : westernInitialDiagnosis.trim();
    }

    public String getChineseInitialDiagnosis() {
        return chineseInitialDiagnosis;
    }

    public void setChineseInitialDiagnosis(String chineseInitialDiagnosis) {
        this.chineseInitialDiagnosis = chineseInitialDiagnosis == null ? null : chineseInitialDiagnosis.trim();
    }

    public String getEndDiagnosis() {
        return endDiagnosis;
    }

    public void setEndDiagnosis(String endDiagnosis) {
        this.endDiagnosis = endDiagnosis == null ? null : endDiagnosis.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", chiefComplaint=").append(chiefComplaint);
        sb.append(", currentMedicalHistory=").append(currentMedicalHistory);
        sb.append(", currentTreatmentSituation=").append(currentTreatmentSituation);
        sb.append(", pastHistory=").append(pastHistory);
        sb.append(", allergyHistory=").append(allergyHistory);
        sb.append(", physicalExamination=").append(physicalExamination);
        sb.append(", westernInitialDiagnosis=").append(westernInitialDiagnosis);
        sb.append(", chineseInitialDiagnosis=").append(chineseInitialDiagnosis);
        sb.append(", endDiagnosis=").append(endDiagnosis);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
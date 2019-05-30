package edu.neu.his.bean;

import java.io.Serializable;

public class Registration implements Serializable {
    private Integer medicalRecordId;

    private String address;

    private Integer age;

    private String birthday;

    private String consultationDate;

    private String idNumber;

    private String medicialCertificateNumber;

    private String medicialCategory;

    private String patientName;

    private Integer outpatientDoctorId;

    private Integer registrationDepartmentId;

    private Integer settlementCategoryId;

    private String registrationCategory;

    private String status;

    private Float cost;

    private static final long serialVersionUID = 1L;

    public Integer getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(Integer medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(String consultationDate) {
        this.consultationDate = consultationDate == null ? null : consultationDate.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getMedicialCertificateNumber() {
        return medicialCertificateNumber;
    }

    public void setMedicialCertificateNumber(String medicialCertificateNumber) {
        this.medicialCertificateNumber = medicialCertificateNumber == null ? null : medicialCertificateNumber.trim();
    }

    public String getMedicialCategory() {
        return medicialCategory;
    }

    public void setMedicialCategory(String medicialCategory) {
        this.medicialCategory = medicialCategory == null ? null : medicialCategory.trim();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public Integer getOutpatientDoctorId() {
        return outpatientDoctorId;
    }

    public void setOutpatientDoctorId(Integer outpatientDoctorId) {
        this.outpatientDoctorId = outpatientDoctorId;
    }

    public Integer getRegistrationDepartmentId() {
        return registrationDepartmentId;
    }

    public void setRegistrationDepartmentId(Integer registrationDepartmentId) {
        this.registrationDepartmentId = registrationDepartmentId;
    }

    public Integer getSettlementCategoryId() {
        return settlementCategoryId;
    }

    public void setSettlementCategoryId(Integer settlementCategoryId) {
        this.settlementCategoryId = settlementCategoryId;
    }

    public String getRegistrationCategory() {
        return registrationCategory;
    }

    public void setRegistrationCategory(String registrationCategory) {
        this.registrationCategory = registrationCategory == null ? null : registrationCategory.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", medicalRecordId=").append(medicalRecordId);
        sb.append(", address=").append(address);
        sb.append(", age=").append(age);
        sb.append(", birthday=").append(birthday);
        sb.append(", consultationDate=").append(consultationDate);
        sb.append(", idNumber=").append(idNumber);
        sb.append(", medicialCertificateNumber=").append(medicialCertificateNumber);
        sb.append(", medicialCategory=").append(medicialCategory);
        sb.append(", patientName=").append(patientName);
        sb.append(", outpatientDoctorId=").append(outpatientDoctorId);
        sb.append(", registrationDepartmentId=").append(registrationDepartmentId);
        sb.append(", settlementCategoryId=").append(settlementCategoryId);
        sb.append(", registrationCategory=").append(registrationCategory);
        sb.append(", status=").append(status);
        sb.append(", cost=").append(cost);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
package edu.neu.his.bean;

import java.io.Serializable;

public class Registration implements Serializable {
    private Integer id;

    private Integer medicalRecordId;

    private String address;

    private Integer age;

    private String birthday;

    private String registrationDate;

    private String patientId;

    private String medicalCategory;

    private String medicalCertificateNumber;

    private String name;

    private Integer outpatientDoctorId;

    private String registrationCategory;

    private String registrationDepartment;

    private String settlementCategory;

    private String status;

    private Float fee;

    private Integer hasRecordBook;

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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate == null ? null : registrationDate.trim();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId == null ? null : patientId.trim();
    }

    public String getMedicalCategory() {
        return medicalCategory;
    }

    public void setMedicalCategory(String medicalCategory) {
        this.medicalCategory = medicalCategory == null ? null : medicalCategory.trim();
    }

    public String getMedicalCertificateNumber() {
        return medicalCertificateNumber;
    }

    public void setMedicalCertificateNumber(String medicalCertificateNumber) {
        this.medicalCertificateNumber = medicalCertificateNumber == null ? null : medicalCertificateNumber.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOutpatientDoctorId() {
        return outpatientDoctorId;
    }

    public void setOutpatientDoctorId(Integer outpatientDoctorId) {
        this.outpatientDoctorId = outpatientDoctorId;
    }

    public String getRegistrationCategory() {
        return registrationCategory;
    }

    public void setRegistrationCategory(String registrationCategory) {
        this.registrationCategory = registrationCategory == null ? null : registrationCategory.trim();
    }

    public String getRegistrationDepartment() {
        return registrationDepartment;
    }

    public void setRegistrationDepartment(String registrationDepartment) {
        this.registrationDepartment = registrationDepartment == null ? null : registrationDepartment.trim();
    }

    public String getSettlementCategory() {
        return settlementCategory;
    }

    public void setSettlementCategory(String settlementCategory) {
        this.settlementCategory = settlementCategory == null ? null : settlementCategory.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Integer getHasRecordBook() {
        return hasRecordBook;
    }

    public void setHasRecordBook(Integer hasRecordBook) {
        this.hasRecordBook = hasRecordBook;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", medicalRecordId=").append(medicalRecordId);
        sb.append(", address=").append(address);
        sb.append(", age=").append(age);
        sb.append(", birthday=").append(birthday);
        sb.append(", registrationDate=").append(registrationDate);
        sb.append(", patientId=").append(patientId);
        sb.append(", medicalCategory=").append(medicalCategory);
        sb.append(", medicalCertificateNumber=").append(medicalCertificateNumber);
        sb.append(", name=").append(name);
        sb.append(", outpatientDoctorId=").append(outpatientDoctorId);
        sb.append(", registrationCategory=").append(registrationCategory);
        sb.append(", registrationDepartment=").append(registrationDepartment);
        sb.append(", settlementCategory=").append(settlementCategory);
        sb.append(", status=").append(status);
        sb.append(", fee=").append(fee);
        sb.append(", hasRecordBook=").append(hasRecordBook);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
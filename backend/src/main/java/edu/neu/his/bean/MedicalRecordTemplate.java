package edu.neu.his.bean;

import java.io.Serializable;

public class MedicalRecordTemplate implements Serializable {
    private Integer id;

    private String name;

    private Integer type;

    private Integer user_id;

    private Integer department_id;

    private String create_time;

    private String status;

    private String chief_complaint;

    private String current_medical_history;

    private String current_treatment_situation;

    private String past_history;

    private String allergy_history;

    private String physical_examination;

    private String western_initial_diagnosis;

    private String chinese_initial_diagnosis;

    private String end_diagnosis;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time == null ? null : create_time.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getChief_complaint() {
        return chief_complaint;
    }

    public void setChief_complaint(String chief_complaint) {
        this.chief_complaint = chief_complaint == null ? null : chief_complaint.trim();
    }

    public String getCurrent_medical_history() {
        return current_medical_history;
    }

    public void setCurrent_medical_history(String current_medical_history) {
        this.current_medical_history = current_medical_history == null ? null : current_medical_history.trim();
    }

    public String getCurrent_treatment_situation() {
        return current_treatment_situation;
    }

    public void setCurrent_treatment_situation(String current_treatment_situation) {
        this.current_treatment_situation = current_treatment_situation == null ? null : current_treatment_situation.trim();
    }

    public String getPast_history() {
        return past_history;
    }

    public void setPast_history(String past_history) {
        this.past_history = past_history == null ? null : past_history.trim();
    }

    public String getAllergy_history() {
        return allergy_history;
    }

    public void setAllergy_history(String allergy_history) {
        this.allergy_history = allergy_history == null ? null : allergy_history.trim();
    }

    public String getPhysical_examination() {
        return physical_examination;
    }

    public void setPhysical_examination(String physical_examination) {
        this.physical_examination = physical_examination == null ? null : physical_examination.trim();
    }

    public String getWestern_initial_diagnosis() {
        return western_initial_diagnosis;
    }

    public void setWestern_initial_diagnosis(String western_initial_diagnosis) {
        this.western_initial_diagnosis = western_initial_diagnosis == null ? null : western_initial_diagnosis.trim();
    }

    public String getChinese_initial_diagnosis() {
        return chinese_initial_diagnosis;
    }

    public void setChinese_initial_diagnosis(String chinese_initial_diagnosis) {
        this.chinese_initial_diagnosis = chinese_initial_diagnosis == null ? null : chinese_initial_diagnosis.trim();
    }

    public String getEnd_diagnosis() {
        return end_diagnosis;
    }

    public void setEnd_diagnosis(String end_diagnosis) {
        this.end_diagnosis = end_diagnosis == null ? null : end_diagnosis.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", user_id=").append(user_id);
        sb.append(", department_id=").append(department_id);
        sb.append(", create_time=").append(create_time);
        sb.append(", status=").append(status);
        sb.append(", chief_complaint=").append(chief_complaint);
        sb.append(", current_medical_history=").append(current_medical_history);
        sb.append(", current_treatment_situation=").append(current_treatment_situation);
        sb.append(", past_history=").append(past_history);
        sb.append(", allergy_history=").append(allergy_history);
        sb.append(", physical_examination=").append(physical_examination);
        sb.append(", western_initial_diagnosis=").append(western_initial_diagnosis);
        sb.append(", chinese_initial_diagnosis=").append(chinese_initial_diagnosis);
        sb.append(", end_diagnosis=").append(end_diagnosis);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
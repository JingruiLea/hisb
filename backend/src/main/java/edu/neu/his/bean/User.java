package edu.neu.his.bean;

public class User {
    private int uid;
    private String username;
    private String password;
    private String real_name;
    private int department_id;
    private String department_name;
    private String role;
    private boolean participate_in_scheduling;
    private String title;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isParticipate_in_scheduling() {
        return participate_in_scheduling;
    }

    public void setParticipate_in_scheduling(boolean participate_in_scheduling) {
        this.participate_in_scheduling = participate_in_scheduling;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

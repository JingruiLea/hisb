package edu.neu.his.bean.schedule;

import java.util.Date;

public class Schedule {
    private String name;
    private int i;
    private int j;
    private String scheduleDate;
    private String shift;
    private String registration_Level;
    private int week;
    private int residue;
    private String valid;
    private int reg_limit;

    public Schedule(){}

    public Schedule(String name,int i,int j,String scheduleDate,String shift,int week,int residue,String valid,int reg_limit,String registration_Level){
        this.name = name;
        this.i = i;
        this.j = j;
        this.scheduleDate = scheduleDate;
        this.shift = shift;
        this.week = week;
        this.residue = residue;
        this.valid = valid;
        this.reg_limit = reg_limit;
        this.registration_Level = registration_Level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getResidue() {
        return residue;
    }

    public void setResidue(int residue) {
        this.residue = residue;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public int getReg_limit() {
        return reg_limit;
    }

    public void setReg_limit(int reg_limit) {
        this.reg_limit = reg_limit;
    }

    public String getRegistration_Level() {
        return registration_Level;
    }

    public void setRegistration_Level(String registration_Level) {
        this.registration_Level = registration_Level;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "name='" + name + '\'' +
                ", i=" + i +
                ", j=" + j +
                ", scheduleDate='" + scheduleDate + '\'' +
                ", shift='" + shift + '\'' +
                ", week=" + week +
                ", residue=" + residue +
                ", valid='" + valid + '\'' +
                ", reg_limit='" + reg_limit + '\'' +
                '}';
    }
}

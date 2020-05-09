package com.example.ysuselfstudy.data;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author Ahyer
 * @version 1.0
 * @date 2020/5/9 21:48
 */
public class MakeCorrectWeek {
    private int amClasses;

    private String weekOfTerm;

    private String schoolYearTerm;

    private int code;

    private List<Object> timeTable;

    private String date;

    public int getAmClasses() {
        return amClasses;
    }

    public void setAmClasses(int amClasses) {
        this.amClasses = amClasses;
    }

    public String getWeekOfTerm() {
        return weekOfTerm;
    }

    public void setWeekOfTerm(String weekOfTerm) {
        this.weekOfTerm = weekOfTerm;
    }

    public String getSchoolYearTerm() {
        return schoolYearTerm;
    }

    public void setSchoolYearTerm(String schoolYearTerm) {
        this.schoolYearTerm = schoolYearTerm;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Object> getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(List<Object> timeTable) {
        this.timeTable = timeTable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPmClasses() {
        return pmClasses;
    }

    public void setPmClasses(int pmClasses) {
        this.pmClasses = pmClasses;
    }

    public int getEveClasses() {
        return eveClasses;
    }

    public void setEveClasses(int eveClasses) {
        this.eveClasses = eveClasses;
    }

    public int getAllTeachWeeks() {
        return allTeachWeeks;
    }

    public void setAllTeachWeeks(int allTeachWeeks) {
        this.allTeachWeeks = allTeachWeeks;
    }

    public int getAllTermWeeks() {
        return allTermWeeks;
    }

    public void setAllTermWeeks(int allTermWeeks) {
        this.allTermWeeks = allTermWeeks;
    }

    private int pmClasses;

    private int eveClasses;

    private int allTeachWeeks;

    private int allTermWeeks;


    @Override
    public String toString() {
        return "MakeCorrectWeek{" +
                "amClasses=" + amClasses +
                ", weekOfTerm='" + weekOfTerm + '\'' +
                ", schoolYearTerm='" + schoolYearTerm + '\'' +
                ", code=" + code +
                ", timeTable=" + timeTable +
                ", date='" + date + '\'' +
                ", pmClasses=" + pmClasses +
                ", eveClasses=" + eveClasses +
                ", allTeachWeeks=" + allTeachWeeks +
                ", allTermWeeks=" + allTermWeeks +
                '}';
    }
}

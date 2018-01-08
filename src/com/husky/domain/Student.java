package com.husky.domain;

import java.io.Serializable;

/**
 * Created by songshiwen on 18/1/8.
 */
public class Student implements Serializable {
    private int sid;//主键
    private String sname;

    private Clazz clazz;//学生所在班级

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Student(String sname, String apassword) {
        this.sname = sname;
        this.apassword = apassword;
    }

    public Student() {

    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", apassword='" + apassword + '\'' +
                '}';
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getApassword() {
        return apassword;
    }

    public void setApassword(String apassword) {
        this.apassword = apassword;
    }

    private String apassword;
}

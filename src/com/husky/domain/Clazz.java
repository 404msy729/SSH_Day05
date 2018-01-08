package com.husky.domain;

import java.io.Serializable;

/**
 * Created by songshiwen on 18/1/8.
 */
public class Clazz implements Serializable {
    private int cid;
    private String cname,cinfor;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Clazz(String cname, String cinfor) {
        this.cname = cname;
        this.cinfor = cinfor;
    }

    public Clazz() {

    }

    @Override
    public String toString() {
        return "Clazz{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", cinfor='" + cinfor + '\'' +
                '}';
    }

    public String getCinfor() {
        return cinfor;
    }

    public void setCinfor(String cinfor) {
        this.cinfor = cinfor;
    }
}

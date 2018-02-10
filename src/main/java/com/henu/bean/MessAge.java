package com.henu.bean;

/**
 * Created by Administrator on 2018/1/12.
 */
public class MessAge {
    String myUserName;
    String toUserName;
    String sendDate;
    int readflag;
    String context;

    @Override
    public String toString() {
        return myUserName+","+toUserName+","+context+","+sendDate+","+readflag;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getMyUserName() {
        return myUserName;
    }

    public void setMyUserName(String myUserName) {
        this.myUserName = myUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public int getReadflag() {
        return readflag;
    }

    public void setReadflag(int readflag) {
        this.readflag = readflag;
    }

    public MessAge(String myUserName, String toUserName, String sendDate, int readflag, String context) {

        this.myUserName = myUserName;
        this.toUserName = toUserName;
        this.sendDate = sendDate;
        this.readflag = readflag;
        this.context = context;
    }
}

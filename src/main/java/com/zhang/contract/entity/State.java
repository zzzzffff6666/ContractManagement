package com.zhang.contract.entity;

public class State {
    private String con_name;
    //0 起草，1 分配完成，2 会签完成，3 定稿完成，4 审批完成，5 签订完成
    private int type;
    private String time;

    public String getCon_name() {
        return con_name;
    }

    public void setCon_name(String con_name) {
        this.con_name = con_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

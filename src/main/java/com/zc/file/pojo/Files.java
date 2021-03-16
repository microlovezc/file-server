package com.zc.file.pojo;

import java.util.Date;

public class Files {
    private String uid;
    private Integer size;
    private String type;
    private String name;
    private Date createTime;
    private String addr;

    public Files() {
    }

    public Files(String uid, Integer size, String type, String name, Date createTime, String addr) {
        this.uid = uid;
        this.size = size;
        this.type = type;
        this.name = name;
        this.createTime = createTime;
        this.addr = addr;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "Files{" +
                "uid='" + uid + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", addr='" + addr + '\'' +
                '}';
    }
}

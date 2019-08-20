package com.lxw.mytool.entity;

import java.io.Serializable;

public class Connect implements Serializable {
    private Integer sid;

    private String dbtype;

    private String connectname;

    private String connectaddress;

    private String connectport;

    private String username;

    private String password;

    private static final long serialVersionUID = 1L;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype == null ? null : dbtype.trim();
    }

    public String getConnectname() {
        return connectname;
    }

    public void setConnectname(String connectname) {
        this.connectname = connectname == null ? null : connectname.trim();
    }

    public String getConnectaddress() {
        return connectaddress;
    }

    public void setConnectaddress(String connectaddress) {
        this.connectaddress = connectaddress == null ? null : connectaddress.trim();
    }

    public String getConnectport() {
        return connectport;
    }

    public void setConnectport(String connectport) {
        this.connectport = connectport == null ? null : connectport.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @Override
    public String toString() {
        return "Connect{" +
                "sid=" + sid +
                ", dbtype='" + dbtype + '\'' +
                ", connectname='" + connectname + '\'' +
                ", connectaddress='" + connectaddress + '\'' +
                ", connectport='" + connectport + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
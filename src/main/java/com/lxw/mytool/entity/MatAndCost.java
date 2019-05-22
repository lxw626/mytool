package com.lxw.mytool.entity;

public class MatAndCost {
    private String matNr;
    private String matDesc;
    private String costCode;
    private String costDesc;

    public String getMatNr() {
        return matNr;
    }

    public void setMatNr(String matNr) {
        this.matNr = matNr;
    }

    public String getMatDesc() {
        return matDesc;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCostDesc() {
        return costDesc;
    }

    public void setCostDesc(String costDesc) {
        this.costDesc = costDesc;
    }

    @Override
    public String toString() {
        return
                 matNr + '\t' + matDesc + '\t' + costCode + '\t' + costDesc + '\t';
    }
}

package com.lxw.mytool.entity;

/**
 * 
 * <p>Description: 实体类</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: sgai</p>
 * @author： lixiewen
 * @version 2019-03-19
 */
public class Stor {

    private Long sid;
    private Long version;

    // 库存地编码
    private String storCode;
    // 库存地描述
    private String storDesc;
    // 作业部编码
    private String plantCode;
    // 作业部描述
    private String plantDesc;
    // 作业区编码
    private String scopeCode;
    // 作业区描述
    private String scopeDesc;
    // 成本中心编码
    private String costCenterCode;
    // 成本中心描述
    private String costCenterDesc;

    public String getStorCode() {
        return storCode;
    }

    public void setStorCode(String storCode) {
        this.storCode = storCode;
    }

    public String getStorDesc() {
        return storDesc;
    }

    public void setStorDesc(String storDesc) {
        this.storDesc = storDesc;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getPlantDesc() {
        return plantDesc;
    }

    public void setPlantDesc(String plantDesc) {
        this.plantDesc = plantDesc;
    }

    public String getScopeCode() {
        return scopeCode;
    }

    public void setScopeCode(String scopeCode) {
        this.scopeCode = scopeCode;
    }

    public String getScopeDesc() {
        return scopeDesc;
    }

    public void setScopeDesc(String scopeDesc) {
        this.scopeDesc = scopeDesc;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getCostCenterDesc() {
        return costCenterDesc;
    }

    public void setCostCenterDesc(String costCenterDesc) {
        this.costCenterDesc = costCenterDesc;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
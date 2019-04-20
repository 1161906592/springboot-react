package com.medicinalManage.model.batch;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BatchDto {
    @NotBlank(message = "批次名称不能为空")
    private String batchName;
    @NotNull(message = "批次类型不能为空")
    private Integer batchFlag;

    private String remarks;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(Integer batchFlag) {
        this.batchFlag = batchFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

package com.medicinalManage.model.company;

import javax.validation.constraints.NotBlank;

public class CompanyDto {
    @NotBlank(message = "厂商名称不能为空！")
    private String companyName;

    private String address;

    private String phone;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

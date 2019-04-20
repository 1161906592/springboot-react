package com.medicinalManage.service.company;

import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.model.company.CompanyDto;
import com.medicinalManage.model.company.CompanyVo;

public interface CompanyService {
    PageResultEntity<CompanyVo> getCompanyListByCompanyName(String companyName, PageParams pageParams);
    void addCompany(CompanyDto companyDto) throws Exception;
    void updateCompany(String companyId, CompanyDto companyDto) throws Exception;
    void deleteCompany(String companyId);
}

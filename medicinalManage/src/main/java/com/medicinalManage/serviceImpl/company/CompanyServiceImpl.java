package com.medicinalManage.serviceImpl.company;

import com.github.pagehelper.Page;
import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.dao.Dao;
import com.medicinalManage.exception.BusinessException;
import com.medicinalManage.model.company.CompanyDo;
import com.medicinalManage.model.company.CompanyDto;
import com.medicinalManage.model.company.CompanyVo;
import com.medicinalManage.service.company.CompanyService;
import com.medicinalManage.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    Dao dao;

    @Override
    public PageResultEntity<CompanyVo> getCompanyListByCompanyName(String companyName, PageParams pageParams) {
        Page<CompanyVo> companyVos = dao.getPageList("com.medicinalManage.mapper.company.CompanyMapper.getCompanyListByCompanyName", companyName, pageParams);
        return new PageResultEntity<>(companyVos);
    }

    @Override
    public void addCompany(CompanyDto companyDto) throws Exception {
        CompanyDo companyDo = new CompanyDo();
        BeanUtils.copyProperties(companyDto, companyDo);
        String uuid = StringUtils.getUUid();
        companyDo.setCompanyId(uuid);
        int count = dao.insert("com.medicinalManage.mapper.company.CompanyMapper.addCompany", companyDo);
        if (count == 0) {
            throw new BusinessException("厂商名称已存在");
        }
    }

    @Override
    public void updateCompany(String companyId, CompanyDto companyDto) throws Exception {
        CompanyDo companyDo = new CompanyDo();
        BeanUtils.copyProperties(companyDto, companyDo);
        companyDo.setCompanyId(companyId);
        int count = dao.update("com.medicinalManage.mapper.company.CompanyMapper.updateCompany", companyDo);
        if (count == 0) {
            throw new BusinessException("厂商名称已存在");
        }
    }

    @Override
    public void deleteCompany(String companyId) {
        dao.delete("com.medicinalManage.mapper.company.CompanyMapper.deleteCompany", companyId);
    }
}

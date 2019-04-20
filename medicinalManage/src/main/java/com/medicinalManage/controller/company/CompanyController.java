package com.medicinalManage.controller.company;

import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.common.ResponseEntity;
import com.medicinalManage.model.company.CompanyDto;
import com.medicinalManage.model.company.CompanyVo;
import com.medicinalManage.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("/company/list")
    public ResponseEntity<PageResultEntity<CompanyVo>> getCompanyListByCompanyName (String companyName, PageParams pageParams) {
        return new ResponseEntity<>(companyService.getCompanyListByCompanyName(companyName, pageParams));
    }

    @PostMapping("/company")
    public ResponseEntity addCompany (@Validated @RequestBody CompanyDto companyDto) throws Exception {
        companyService.addCompany(companyDto);
        return new ResponseEntity();
    }

    @PutMapping("/company")
    public ResponseEntity updateCompany(@NotNull(message = "厂商id不能为空") @NotBlank(message = "厂商id不能为空") String companyId, @Validated @RequestBody CompanyDto companyDto) throws Exception {
        companyService.updateCompany(companyId, companyDto);
        return new ResponseEntity();
    }

    @DeleteMapping("/company")
    public ResponseEntity deleteCompany (@NotNull(message = "厂商id不能为空") @NotBlank(message = "厂商id不能为空") String companyId) {
        companyService.deleteCompany(companyId);
        return new ResponseEntity();
    }
}

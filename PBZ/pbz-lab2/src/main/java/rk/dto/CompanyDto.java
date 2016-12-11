package rk.dto;

import rk.activemodel.Company;

public class CompanyDto {
    public String companyName;

    public String waterUsage;

    public CompanyDto() {
    }

    public CompanyDto(Company company) {
        companyName = company.getName();
        waterUsage = company.getUsageType().getBdType();
    }

    public String getWaterUsage() {
        return waterUsage;
    }

    public void setWaterUsage(String waterUsage) {
        this.waterUsage = waterUsage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

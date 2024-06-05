package com.embarkx.companyms.company;

import java.util.List;

import com.embarkx.companyms.company.dto.ReviewMessage;

public interface CompanyService {

List<Company> getAllCompanies();

boolean updateCompany(Company company,Long id);

Company getCompanyById(Long id);

void createCompany(Company company);

boolean deleteCompany(Long id);

public void updateCompanyRating(ReviewMessage reviewMessage);
}

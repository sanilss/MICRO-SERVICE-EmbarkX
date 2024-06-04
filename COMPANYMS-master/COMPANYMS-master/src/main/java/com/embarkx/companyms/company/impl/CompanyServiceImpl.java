package com.embarkx.companyms.company.impl;


import com.embarkx.companyms.company.Company;
import com.embarkx.companyms.company.CompanyRepository;
import com.embarkx.companyms.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

public CompanyRepository companyRepository;

public CompanyServiceImpl(CompanyRepository companyRepository){
    this.companyRepository=companyRepository;
}

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company updateCompany, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
        Company company = companyOptional.get();
            company.setDescription(updateCompany.getDescription());
            company.setName(updateCompany.getName());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompanyById(Long id) {
       return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
    if(companyRepository.existsById(id)) {
        companyRepository.deleteById(id);
        return true;
    }
        return false;
    }




}




package com.embarkx.companyms.company.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.embarkx.companyms.company.Company;
import com.embarkx.companyms.company.CompanyRepository;
import com.embarkx.companyms.company.CompanyService;
import com.embarkx.companyms.company.clients.ReviewClient;
import com.embarkx.companyms.company.dto.ReviewMessage;

import jakarta.ws.rs.NotFoundException;

@Service
public class CompanyServiceImpl implements CompanyService {

public CompanyRepository companyRepository;
private ReviewClient reviewClient;

public CompanyServiceImpl(CompanyRepository companyRepository,ReviewClient reviewClient){
    this.companyRepository=companyRepository;
    this.reviewClient=reviewClient;
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

	@Override
	public void updateCompanyRating(ReviewMessage reviewMessage) {
System.out.println(reviewMessage.getDescription());	
//get the company from the database also we need to get the average rating 
//against that Company from the review Service and then we need to update the Company Review and then save it.
Company company=companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(() -> new NotFoundException("Company not Found"+ reviewMessage.getCompanyId()));
	Double avgRating=reviewClient.getAverageRatingForCompany(company.getId());
   company.setRating(avgRating);
	companyRepository.save(company);
	}




}




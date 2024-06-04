package com.embarkx.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
     return  new  ResponseEntity(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
   Company company= companyService.getCompanyById(id);
   if(company!=null)
    return new ResponseEntity<>(company,HttpStatus.OK);
   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

    @PutMapping ("{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id,@RequestBody Company company) {
        companyService.updateCompany(company, id);
        return new ResponseEntity<>("Company Updated Successfully", HttpStatus.OK);
    }
       @PostMapping
        public ResponseEntity<String> createCompany(@RequestBody Company company){
             companyService.createCompany(company);
        return new ResponseEntity<>("Company has been created SuccessFully",HttpStatus.CREATED);
        }

@DeleteMapping("{id}")
public ResponseEntity<String> deleteCompany(@PathVariable Long id){
      boolean isDeleted= companyService.deleteCompany(id);
        if(isDeleted){
            return new ResponseEntity<>("Company is Deleted",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Company NOT FOUND",HttpStatus.NOT_FOUND);
        }
    }

    }


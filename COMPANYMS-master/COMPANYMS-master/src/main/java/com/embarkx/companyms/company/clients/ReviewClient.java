package com.embarkx.companyms.company.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("REVIEW-SERVICE")
//this is the client which will be used to make the API calls
public interface ReviewClient {

	
	@GetMapping("/reviews/averageRating")
	double getAverageRatingForCompany(@RequestParam("companyId")Long companyId);
}

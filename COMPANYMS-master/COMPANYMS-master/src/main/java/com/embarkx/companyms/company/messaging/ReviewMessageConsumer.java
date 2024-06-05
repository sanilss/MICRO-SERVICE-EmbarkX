package com.embarkx.companyms.company.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.embarkx.companyms.company.CompanyService;
import com.embarkx.companyms.company.dto.ReviewMessage;
@Service
public class ReviewMessageConsumer {

//we are going to receive rating information and based on that we need to update it against a particular Company.
	//and for that we have created Company Instance;
	private final CompanyService companyService;
	
	public ReviewMessageConsumer(CompanyService companyService) {
		this.companyService=companyService;
	}
	@RabbitListener(queues="companyRatingQueue")
	public void consumeMessage(ReviewMessage reviewMessage) {
		companyService.updateCompanyRating(reviewMessage);
	}
}

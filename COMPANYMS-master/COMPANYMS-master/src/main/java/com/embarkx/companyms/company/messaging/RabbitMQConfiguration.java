package com.embarkx.companyms.company.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//this is a configuration class for setting up RabbitMQ in our springboot application
//this class will aloow us to define the beans and it will also help in controlling and configure rabbitmq messaging
@Configuration
public class RabbitMQConfiguration {

	@Bean
	public Queue companyRatingQueue() {
		return new Queue("companyRatingQueue");
	}
	
	//used to seralize and Deseralize messages we use Jackon library to convert messages to and from Json format 
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
		
	}
	//this is a helper class and it handles creation and release of resources when sending messages to and releases messages from RabbitMQ 
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}

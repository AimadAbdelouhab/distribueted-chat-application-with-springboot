package com.chatApplication.userService.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chatApplication.userService.model.User;

@Service
public class UserServiceRabbitMQProducer {
	
	@Value("${rabbitmq.userServiceExchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.user.userServiceProduce.key}")
	private String userProduceRoutingKey;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceRabbitMQProducer.class);
	
	private RabbitTemplate rabbitTemplate;

	public UserServiceRabbitMQProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendUser(User user) {
		logger.info(String.format("user send -> %s", user.toString() ));
		
		rabbitTemplate.convertAndSend(exchange, userProduceRoutingKey,  user);
		
	}
	
	

}

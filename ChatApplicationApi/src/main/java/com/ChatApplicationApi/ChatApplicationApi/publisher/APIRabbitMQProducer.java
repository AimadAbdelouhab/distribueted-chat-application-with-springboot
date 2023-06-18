package com.ChatApplicationApi.ChatApplicationApi.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ChatApplicationApi.ChatApplicationApi.dto.UserDto;
import com.ChatApplicationApi.ChatApplicationApi.model.Message;

@Service
public class APIRabbitMQProducer {

	
	@Value("${rabbitmq.apiExchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.message.apiProduce.key}")
	private String messageProduceRoutingKey;
	
	@Value("${rabbitmq.routing.user.apiProduce.key}")
	private String userProduceRoutingKey;
	
	private static final Logger logger = LoggerFactory.getLogger(APIRabbitMQProducer.class);
	
	private RabbitTemplate rabbitTemplate;

	
	public APIRabbitMQProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(Message messageDto) {
		logger.info(String.format("message send -> %s", messageDto.toString() ));
		
		rabbitTemplate.convertAndSend(exchange, messageProduceRoutingKey,  messageDto);
		
	}
	
	public void sendUser(UserDto userDto) {
		logger.info(String.format("user send -> %s", userDto.toString() ));
		
		rabbitTemplate.convertAndSend(exchange, userProduceRoutingKey,  userDto);
		
	}
	
	
	
}


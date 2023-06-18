package com.chatapplication.messageService.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chatapplication.messageService.model.Message;

@Service
public class MessageServiceRabbitMQProducer {
	
	@Value("${rabbitmq.messageServiceExchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.message.messageServiceProduce.key}")
	private String messageProduceRoutingKey;
	
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceRabbitMQProducer.class);
	
	private RabbitTemplate rabbitTemplate;

	public MessageServiceRabbitMQProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(Message message) {
		logger.info(String.format("message send -> %s", message.toString() ));
		
		rabbitTemplate.convertAndSend(exchange, messageProduceRoutingKey,  message);
		

		
	}
}

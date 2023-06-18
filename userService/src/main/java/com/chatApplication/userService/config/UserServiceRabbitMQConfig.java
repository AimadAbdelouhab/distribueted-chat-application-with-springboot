package com.chatApplication.userService.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceRabbitMQConfig {

//	@Value("${rabbitmq.queue.user.userServiceProduce.name}")
//	private String userUSProduceQueue;
//
//	@Value("${rabbitmq.userServiceExchange.name}")
//	private String exchangeUS;
//	
//	@Value("${rabbitmq.routing.user.userServiceProduce.key}")
//	private String userUSProduceRoutingKey;
//	
//	
//	// spring bean for rabbitMQ queue for users
//	@Bean
//	public Queue userUSProduceQueue() {
//
//		return new Queue(userUSProduceQueue, false);
//	}
//	
//	@Bean
//	public TopicExchange exchangeUS() {
//
//		return new TopicExchange(exchangeUS);
//	}
//	
//	// binding between user production queue and exchange using routing key
//	@Bean
//	public Binding userUSProduceBinding() {
//
//		return BindingBuilder.bind(userUSProduceQueue()).to(exchangeUS()).with(userUSProduceRoutingKey);
//	}
//	
//	

	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
	
	@Bean
	public MessageConverter converter() {
	    return new Jackson2JsonMessageConverter();
	}

}

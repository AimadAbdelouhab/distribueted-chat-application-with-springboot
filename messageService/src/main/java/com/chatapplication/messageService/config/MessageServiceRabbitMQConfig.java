package com.chatapplication.messageService.config;

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
public class MessageServiceRabbitMQConfig {

//	@Value("${rabbitmq.queue.message.messageServiceProduce.name}")
//	private String messageMSProduceQueue;
//
//	@Value("${rabbitmq.messageServiceExchange.name}")
//	private String exchangeMS;
//	
//	@Value("${rabbitmq.routing.message.messageServiceProduce.key}")
//	private String messageMSProduceRoutingKey;
//	
//	
//	// spring bean for rabbitMQ queue for users
//	@Bean
//	public Queue messageMSProduceQueue() {
//
//		return new Queue(messageMSProduceQueue, false);
//	}
//	
//	@Bean
//	public TopicExchange exchangeMS() {
//
//		return new TopicExchange(exchangeMS);
//	}
//	
//	// binding between user production queue and exchange using routing key
//	@Bean
//	public Binding messageMSProduceBinding() {
//
//		return BindingBuilder.bind(messageMSProduceQueue()).to(exchangeMS()).with(messageMSProduceRoutingKey);
//	}
//	
//	

	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
	

	@Bean
	public MessageConverter jsonMessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}

}

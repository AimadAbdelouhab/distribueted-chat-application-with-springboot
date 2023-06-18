package com.ChatApplicationApi.ChatApplicationApi.config;

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
public class APIRabbitMQConfig {

	// api
	@Value("${rabbitmq.queue.message.apiProduce.name}")
	private String messageApiProduceQueue;

	@Value("${rabbitmq.queue.user.apiProduce.name}")
	private String userApiProduceQueue;

	@Value("${rabbitmq.apiExchange.name}")
	private String exchangeApi;

	@Value("${rabbitmq.routing.message.apiProduce.key}")
	private String messageApiProduceRoutingKey;

	@Value("${rabbitmq.routing.user.apiProduce.key}")
	private String userApiProduceRoutingKey;

	// message service

	@Value("${rabbitmq.queue.message.messageServiceProduce.name}")
	private String messageMSProduceQueue;

	@Value("${rabbitmq.messageServiceExchange.name}")
	private String exchangeMS;

	@Value("${rabbitmq.routing.message.messageServiceProduce.key}")
	private String messageMSProduceRoutingKey;

	// user service

	@Value("${rabbitmq.queue.user.userServiceProduce.name}")
	private String userUSProduceQueue;

	@Value("${rabbitmq.userServiceExchange.name}")
	private String exchangeUS;

	@Value("${rabbitmq.routing.user.userServiceProduce.key}")
	private String userUSProduceRoutingKey;

	
	//////////api
	
	// spring bean for rabbitMQ queue for messages
	@Bean
	public Queue messageApiProduceQueue() {

		return new Queue(messageApiProduceQueue, false);
	}

	// spring bean for rabbitMQ queue for users
	@Bean
	public Queue userApiProduceQueue() {

		return new Queue(userApiProduceQueue, false);
	}

	@Bean
	public TopicExchange exchangeApi() {

		return new TopicExchange(exchangeApi);
	}

	// binding between message production queue and exchange using routing key
	@Bean
	public Binding messageApiProduceBinding() {

		return BindingBuilder.bind(messageApiProduceQueue()).to(exchangeApi()).with(messageApiProduceRoutingKey);
	}

	// binding between user production queue and exchange using routing key
	@Bean
	public Binding userProduceBinding() {

		return BindingBuilder.bind(userApiProduceQueue()).to(exchangeApi()).with(userApiProduceRoutingKey);
	}

	/////////////////// message service

	// spring bean for rabbitMQ queue for users
	@Bean
	public Queue messageMSProduceQueue() {

		return new Queue(messageMSProduceQueue, false);
	}

	@Bean
	public TopicExchange exchangeMS() {

		return new TopicExchange(exchangeMS);
	}

	// binding between user production queue and exchange using routing key
	@Bean
	public Binding messageMSProduceBinding() {

		return BindingBuilder.bind(messageMSProduceQueue()).to(exchangeMS()).with(messageMSProduceRoutingKey);
	}

	////////////////// user service

	// spring bean for rabbitMQ queue for users
	@Bean
	public Queue userUSProduceQueue() {

		return new Queue(userUSProduceQueue, false);
	}

	@Bean
	public TopicExchange exchangeUS() {

		return new TopicExchange(exchangeUS);
	}

	// binding between user production queue and exchange using routing key
	@Bean
	public Binding userUSProduceBinding() {

		return BindingBuilder.bind(userUSProduceQueue()).to(exchangeUS()).with(userUSProduceRoutingKey);
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}

}

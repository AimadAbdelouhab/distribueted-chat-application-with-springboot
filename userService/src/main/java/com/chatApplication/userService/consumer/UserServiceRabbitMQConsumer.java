package com.chatApplication.userService.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatApplication.userService.dto.UserDto;
import com.chatApplication.userService.model.User;
import com.chatApplication.userService.producer.UserServiceRabbitMQProducer;
import com.chatApplication.userService.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceRabbitMQConsumer {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceRabbitMQConsumer.class);

	@Autowired
	private UserServiceRabbitMQProducer producer;
	private final UserService userService;

	@RabbitListener(queues = { "${rabbitmq.queue.user.consume.name}" })
	public void consumeMessage(UserDto userDto) {
		logger.info(String.format("user received -> %s", userDto.toString()));
		User user = new User();

		user = userService.getUser(userDto.getUsername());

		if (user == null)
			user = userService.saveUser(userDto);

		producer.sendUser(user);

	}
}

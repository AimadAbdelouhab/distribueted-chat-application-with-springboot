package com.ChatApplicationApi.ChatApplicationApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ChatApplicationApi.ChatApplicationApi.consumer.APIConsumerUser;
import com.ChatApplicationApi.ChatApplicationApi.consumer.ConsumerCallback;
import com.ChatApplicationApi.ChatApplicationApi.dto.MessageDto;
import com.ChatApplicationApi.ChatApplicationApi.dto.UserDto;
import com.ChatApplicationApi.ChatApplicationApi.model.Message;
import com.ChatApplicationApi.ChatApplicationApi.publisher.APIRabbitMQProducer;

@RestController
public class MessageController {

	private APIRabbitMQProducer producer;

	@Value("${rabbitmq.queue.user.apiConsume.name}")
	private String userConsumeQueue;
	@Value("${spring.rabbitmq.host}")
	private String rabbitMqHost;
	@Value("${spring.rabbitmq.port}")
	private String rabbitMqPort;

	@Autowired
	private SimpMessagingTemplate template;

	public MessageController(APIRabbitMQProducer producer) {
		this.producer = producer;
	}

	@MessageMapping("/chat.register")
	public MessageDto register(@Payload MessageDto messageDto, SimpMessageHeaderAccessor headerAccessor) {
		UserDto userDto = new UserDto();
		userDto.setUsername(messageDto.getSender());

		producer.sendUser(userDto);

		APIConsumerUser consumer = new APIConsumerUser(userConsumeQueue,rabbitMqHost, rabbitMqPort);

		try {
			consumer.consume(new ConsumerCallback() {
				@Override
				public void onMessageConsumed(Message message) {
					// Access the consumed Message object

					MessageDto mdto = new MessageDto();
					mdto.setMessageId(message.getMessageId());
					mdto.setSender(message.getSender());
					mdto.setText(message.getText());
					mdto.setType(message.getType());
					System.out.println("User to register"+ mdto.getSender());

					template.convertAndSend("/topic/public", mdto);
					headerAccessor.getSessionAttributes().put("username", mdto.getSender());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return messageDto;

	}

	@MessageMapping("/chat.send")
	public Message sendMessageWithWs(@Payload Message messageDto) {
		producer.sendMessage(messageDto);
		return messageDto;

	}

	@MessageMapping("/chat.delete")
	public Message deleteMessageWithWs(@Payload Message messageDto) {

		producer.sendMessage(messageDto);

		return messageDto;

	}

	@PostMapping("/message/send")
	public ResponseEntity<String> sendMessage(@RequestBody MessageDto messageDto) {
		// producer.sendMessage(messageDto);

		return ResponseEntity.ok("Message " + messageDto.toString() + " Sent to RabbitMQ...");

	}

	@PostMapping("/user/send")
	public ResponseEntity<String> sendUser(@RequestBody UserDto userDto) {

		producer.sendUser(userDto);

		return ResponseEntity.ok("User " + userDto.toString() + " Sent to RabbitMQ...");

	}

}

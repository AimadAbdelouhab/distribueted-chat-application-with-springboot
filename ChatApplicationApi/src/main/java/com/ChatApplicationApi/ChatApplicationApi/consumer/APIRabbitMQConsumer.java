package com.ChatApplicationApi.ChatApplicationApi.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ChatApplicationApi.ChatApplicationApi.dto.MessageDto;
import com.ChatApplicationApi.ChatApplicationApi.model.Message;
import com.ChatApplicationApi.ChatApplicationApi.model.MessageType;
import com.ChatApplicationApi.ChatApplicationApi.model.User;

@Service
public class APIRabbitMQConsumer {

	
	@Autowired
	private SimpMessagingTemplate template;

	private static final Logger logger = LoggerFactory.getLogger(APIRabbitMQConsumer.class);
//
//	@RabbitListener(queues = { "${rabbitmq.queue.user.apiConsume.name}" })
//	public void consumeUserFromUserService(User user) {
//		
//		//consume user and sendTo method to the topic
//		logger.info(String.format("received user -> %s", user));
//		MessageDto messageDto = new MessageDto();
//		messageDto.setSender(user.getUsername());
//		messageDto.setType(MessageType.JOIN);
//		messageDto.setText("");
//		template.convertAndSend("/topic/public", messageDto);
//	}
	
	@RabbitListener(queues = { "${rabbitmq.queue.message.apiConsume.name}" })
	public void consumeMessageFromMessageService(Message messageParam) {
		//consume message and sentTo method to the topic
		logger.info(String.format("received user -> %s", messageParam.getType()));
		
		Message message = new Message();
		message.setMessageId(messageParam.getMessageId());
		message.setText(messageParam.getText());
		message.setSender(messageParam.getSender());
		message.setType(messageParam.getType());
		template.convertAndSend("/topic/public", message);

	}


}

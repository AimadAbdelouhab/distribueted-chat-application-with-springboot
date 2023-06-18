package com.chatapplication.messageService.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapplication.messageService.dto.MessageDto;
import com.chatapplication.messageService.model.Message;
import com.chatapplication.messageService.model.MessageType;
import com.chatapplication.messageService.producer.MessageServiceRabbitMQProducer;
import com.chatapplication.messageService.service.MessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceRabbitMQConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceRabbitMQConsumerService.class);

	@Autowired
	private MessageServiceRabbitMQProducer producer;
	private final MessageService messageService;

	@RabbitListener(queues = { "${rabbitmq.queue.message.consume.name}" })
	public void consumeMessage(MessageDto messageDto) {
		logger.info(String.format("received -> %s", messageDto.toString()));

		Message message = new Message();

		if (messageDto.getType() == MessageType.CHAT) {
			message = messageService.sendMessage(messageDto);

			// save in database
		} else if (messageDto.getType() == MessageType.TODELETE) {
			// delete in database and if success

			messageService.deleteMessage(messageDto.getMessageId());
			message.setText("");
			message.setType(MessageType.DELETED);
			message.setSender("");
			message.setMessageId(messageDto.getMessageId());
		}

		producer.sendMessage(message);

	}

}

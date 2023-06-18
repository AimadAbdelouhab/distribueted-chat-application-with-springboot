package com.chatapplication.messageService.service;

import org.springframework.stereotype.Service;

import com.chatapplication.messageService.dto.MessageDto;
import com.chatapplication.messageService.model.Message;
import com.chatapplication.messageService.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;

	public Message sendMessage(MessageDto messageDto) {
		Message message = new Message();
		message.setSender(messageDto.getSender());
		message.setText(messageDto.getText());

		return messageRepository.save(message);
	}

	public void deleteMessage(Long idMessage) {

		messageRepository.deleteById(idMessage);
	}

}

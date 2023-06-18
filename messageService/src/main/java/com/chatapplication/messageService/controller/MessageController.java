package com.chatapplication.messageService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chatapplication.messageService.dto.MessageDto;
import com.chatapplication.messageService.service.MessageService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/messageMicroservice")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String sendMessage(@RequestBody MessageDto messageDto) {

		// messageService.sendMessage(messageDto);
		return "message sent";
	}

}

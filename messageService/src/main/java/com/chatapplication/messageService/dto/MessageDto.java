package com.chatapplication.messageService.dto;

import com.chatapplication.messageService.model.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = MessageDto.class)
public class MessageDto {

	private long messageId;
	private String text;
	private String sender;
	private MessageType type;
	
	
}

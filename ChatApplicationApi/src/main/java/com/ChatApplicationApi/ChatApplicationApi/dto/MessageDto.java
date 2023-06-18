package com.ChatApplicationApi.ChatApplicationApi.dto;

import com.ChatApplicationApi.ChatApplicationApi.model.MessageType;

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
public class MessageDto {

	private long messageId;
	private String text;
	private String sender;
	private MessageType type;


}

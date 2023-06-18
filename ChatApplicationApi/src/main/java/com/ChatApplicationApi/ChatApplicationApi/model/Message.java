package com.ChatApplicationApi.ChatApplicationApi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	private long messageId;
	private String text;
	private String sender;
	private MessageType type;

	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", text=" + text + ", sender=" + sender + ", type=" + type + "]";
	}

}

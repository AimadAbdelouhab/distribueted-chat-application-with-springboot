package com.chatapplication.messageService.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name="messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="message_id")
	private Long messageId;
	
	@Column(name="text")
	private String text;
	
	@Column(name="sender")
	private String sender;
	
	@Column(name="type")
	private MessageType type;
	
}

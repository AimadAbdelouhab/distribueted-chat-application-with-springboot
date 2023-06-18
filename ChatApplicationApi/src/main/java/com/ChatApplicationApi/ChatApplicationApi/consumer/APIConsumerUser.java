package com.ChatApplicationApi.ChatApplicationApi.consumer;

import java.io.IOException;

import com.ChatApplicationApi.ChatApplicationApi.model.Message;
import com.ChatApplicationApi.ChatApplicationApi.model.MessageType;
import com.ChatApplicationApi.ChatApplicationApi.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class APIConsumerUser {

	private String queueConsumeUser;
	private String rabbitMqHost;
	private String rabbitMqPort;

	public APIConsumerUser(String queueConsumeUser, String rabbitMqHost, String rabbitMqPort) {
		this.queueConsumeUser = queueConsumeUser;
		this.rabbitMqHost = rabbitMqHost;
		this.rabbitMqPort = rabbitMqPort;
	}

	public void consume(ConsumerCallback callback) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(rabbitMqHost);
		factory.setPort(Integer.valueOf(rabbitMqPort));

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(queueConsumeUser, false, false, false, null);

		channel.basicConsume(queueConsumeUser, true, new DeliverCallback() {

			@Override
			public void handle(String consumerTag, Delivery message) throws IOException {
				String consumedString = new String(message.getBody(), "UTF-8");

				GsonBuilder builder = new GsonBuilder();
				builder.setPrettyPrinting();

				Gson gson = builder.create();
				User consumedUser = gson.fromJson(consumedString, User.class);

				Message messageConsumed = new Message();
				messageConsumed.setSender(consumedUser.getUsername());
				messageConsumed.setType(MessageType.JOIN);

				callback.onMessageConsumed(messageConsumed);

			}
		}, new CancelCallback() {

			@Override
			public void handle(String consumerTag) throws IOException {
				// TODO Auto-generated method stub

			}
		});

	}

}

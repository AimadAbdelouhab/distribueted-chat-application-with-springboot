package com.ChatApplicationApi.ChatApplicationApi.consumer;

import com.ChatApplicationApi.ChatApplicationApi.model.Message;

public interface ConsumerCallback {
	void onMessageConsumed(Message message);
}
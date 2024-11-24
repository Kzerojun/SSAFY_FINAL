package com.enjoytrip.chatbot.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageRequest {

	private String userEmail;
	private String userMsg;

}

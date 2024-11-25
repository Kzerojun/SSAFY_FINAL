package com.enjoytrip.chatbot.controller;


import com.enjoytrip.chatbot.model.dto.request.ChatMessageRequest;
import com.enjoytrip.chatbot.service.GptGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final GptGenerator gptGenerator;

	@MessageMapping("/chatbot")
	public void sendMessage(@Payload ChatMessageRequest request) {

		try {
			String destination = "/chat/" + request.getUserEmail();
			String response = gptGenerator.generateChatResponse(request.getUserMsg());

			simpMessagingTemplate.convertAndSend(destination, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

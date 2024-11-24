package com.enjoytrip.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GptGenerator {

    @Value("${gpt.secret-key}")
    private String API_KEY;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String generateChatResponse(String userMsg) throws Exception {
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        // 요청 바디 구성
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "gpt-4o-mini");
        bodyMap.put("temperature", 0.8); // Temperature 값 추가
        // 메시지 리스트 구성
        List<Map<String, String>> messages = new ArrayList<>();

        // 시스템 메시지 (어시스턴트 역할 설정)
        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "system");
        assistantMessage.put("content",
                "당신은 한국 여행 전문가입니다. " +
                        "한국의 지역, 문화, 역사, 음식, 명소 및 여행 계획에 대한 깊은 지식을 가지고 있습니다. " +
                        "사용자가 질문하면, 그 질문에 대해 전문적이고 자세한 답변을 제공합니다. " +
                        "추천할 때는 계절, 사용자 관심사, 여행 기간 등을 고려합니다. " +
                        "답변은 간결하지만 유용하고, 사용자가 실제로 계획을 세울 수 있도록 구체적인 정보를 포함해야 합니다. " +
                        "친근하고 따뜻한 말투로 사용자를 돕는 것이 목표입니다."
                +"또한, 바로 프론트에게 응답을 보낼예정입니다. 마크다운 형식을 쓰지마세요"+
                        "답변을 모르면 모른다고 안내하고, 없는 답변을 억지로 지어내려고 하지 말아주세요.\n"
                        + "답변에 순서를 사용할 경우 순서 숫자. 방식 말고, (순서 숫자)로 답변 해주세요.\n"
                        + "답변을 모를 경우, 안내번호(010-5561-7043)로 자세히 문의하도록 유도하세요."
        );

        // 사용자 메시지
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userMsg);
        messages.add(userMessage);
        messages.add(assistantMessage);

        // 메시지와 모델을 bodyMap에 추가
        bodyMap.put("messages", messages);

        // 바디를 JSON으로 직렬화
        String body = objectMapper.writeValueAsString(bodyMap);

        // HTTP 요청 설정
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        // RestTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);

        // 응답을 JSON으로 변환
        JsonNode jsonResponse = objectMapper.readTree(response.getBody());

        // 응답에서 choices[0].message.content 값을 추출
        String content = jsonResponse.path("choices").get(0).path("message").path("content").asText();

        // 응답을 JSON으로 변환하여 반환
        return content;
    }

}

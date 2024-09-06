package com.shwimping.be.place.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AIService {

    private final ChatModel chatModel;

    public void getResponse(String message) {
        String command = "사용자의 요청을 분석하여 정보를 제공해줘. \n" +
                "1. 거리 (m 단위로 입력) \n" +
                "2. 쉼터의 카테고리 (SMART, HOT, COLD, LIBRARY, TOGETHER) - 여러 개 가능 \n" +
                "3. 정렬 기준 (STAR_DESC 또는 DISTANCE_ASC) \n\n" +
                "예시: \n" +
                "거리: 3000 (사용자가 '3km 안에'라고 했을 때) \n" +
                "카테고리: HOT (사용자가 덥다고 했을 때), LIBRARY (사용자가 조용한 곳을 원할 때) \n" +
                "COLD (사용자가 춥다고 했을 때), SMART, TOGETHER (이 두개는 아무데나라고 하면 추가 + 기본)" +
                "정렬 기준: DISTANCE_ASC (가까운 순으로), STAR_DESC (평점이 좋은 순으로) \n\n" +
                "기본값: \n" +
                "거리: 2000 (입력하지 않을 경우) \n" +
                "카테고리: 모든 값 \n" +
                "정렬 기준: DISTANCE_ASC (입력하지 않을 경우) \n\n" +
                "사용자의 메시지를 위 정보에 기반하여 분석해서 JSON 형식으로 반환해줘. : " + message;

        PromptTemplate template = new PromptTemplate(command);
        template.add("message", message);

        String templateMessage = template.render();
        Message userMessage = new UserMessage(templateMessage);

        String response = chatModel.call(userMessage);
        log.info("response: {}", response);

        // JSON 파싱 (여기서는 Jackson 라이브러리를 사용할 수 있다고 가정)
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);
            // 이후 jsonResponse를 사용하여 필요한 데이터 처리
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage());
        }
    }
}

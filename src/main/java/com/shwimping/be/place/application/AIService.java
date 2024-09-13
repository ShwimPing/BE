package com.shwimping.be.place.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.GetShelterRecommendAIResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIService {

    private final ChatModel chatModel;

    public GetShelterRecommendAIResponse getResponse(String message) {
        Message userMessage = getMessage(message);

        String response = chatModel.call(userMessage);
        log.info("response: {}", response);

        return convertResponseToObject(response);
    }

    private Message getMessage(String message) {
        String command = "사용자의 요청을 분석하여 정보를 제공해줘. \n" +
                "1. distance (m 단위로) \n" +
                "2. category (SMART, LIBRARY, TOGETHER) - 여러 개 가능 \n" +
                "3. sortType (STAR_DESC 또는 DISTANCE_ASC) \n" +
                "4. keyWord (검색어) \n\n" +
                "예시: \n" +
                "distance: 3000 (사용자가 '3km 안에'라고 했을 때) \n" +
                "category: SMART, TOGETHER, LIBRARY (항상 추가)" +
                "sortType: DISTANCE_ASC (가까운 순으로), STAR_DESC (평점이 좋은 순으로) \n" +
                "keyWord: 못골 (이름에 못골이 들어간 장소를 찾아줘, 이름에 대한 명시가 없으면 설정 X, 쉴 수 있는 곳을 찾아줘는 쉴 수 있는 곳 반환 X) \n\n" +
                "기본값: \n" +
                "distance: 2000 (입력하지 않을 경우) \n" +
                "category: SMART, TOGETHER, LIBRARY (입력하지 않을 경우 모두 기본 값)\n" +
                "sortType: DISTANCE_ASC (입력하지 않을 경우) \n" +
                "keyWord:  (입력하지 않을 경우) \n\n" +
                "예외 사항: \n" +
                "category: SMART(스마트 쉼터 제외 요청시 제외), TOGETHER(기후동행쉼터 제외 요청시 제외), LIBRARY(도서관 제외 요청시 제외)\n\n" +
                "사용자의 메시지를 위 정보에 기반하여 분석해서 JSON 형식으로 반환해줘. : " + message;

        PromptTemplate template = new PromptTemplate(command);
        template.add("message", message);

        String templateMessage = template.render();
        return new UserMessage(templateMessage);
    }

    private GetShelterRecommendAIResponse convertResponseToObject(String response) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);

            int distance = jsonNode.get("distance").asInt();
            List<Category> categories = objectMapper.convertValue(
                    jsonNode.get("category"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Category.class)
            );

            int month = LocalDate.now().getMonthValue();

            if (month >= 6 && month <= 9) {
                categories.add(Category.HOT);
            } else if (month >= 11 || month <= 3) {
                categories.add(Category.COLD);
            }

            SortType sortType = SortType.valueOf(jsonNode.get("sortType").asText());
            String keyWord = jsonNode.get("keyWord").asText();

            return new GetShelterRecommendAIResponse(distance, categories, sortType, keyWord);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON 형식 변환 중 오류가 발생했습니다.");
        }
    }
}

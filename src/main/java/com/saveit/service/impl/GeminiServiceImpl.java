package com.saveit.service.impl;

import com.saveit.mapper.PromptHistoryMapper;
import com.saveit.mapper.PromptMapper;
import com.saveit.service.GeminiService;
import com.saveit.vo.Expense;
import com.saveit.vo.PromptHistory;
import com.saveit.vo.PromptVO;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {

    private final PromptMapper promptMapper;
    private final PromptHistoryMapper promptHistoryMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api-key}")  
    private String apiKey;


    private final String GEMINI_API_URL = 
    		"https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=";


//    private final String GEMINI_API_URL =
//        "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=";

    @Override
    public String analyze(int userId, int goalAmount, List<Expense> expenses) {
        // 1. 프롬프트 조회
        PromptVO prompt = promptMapper.findByType("consumption_analysis");
//        if (prompt == null || prompt.getTemplate() == null) {
//            return "분석용 프롬프트가 존재하지 않습니다.";
//        }

        // 2. 소비 데이터 계산
        int totalSpent = 0;
        StringBuilder expenseList = new StringBuilder();
        for (Expense e : expenses) {
            totalSpent += e.getAmount();
            expenseList.append("- ")
                    .append(e.getExpenseDate()).append(" | ")
                    .append(e.getCategory()).append(" | ")
                    .append(e.getAmount()).append("원\n");
        }

        int achieveRate = goalAmount > 0 ? (int)(((double) totalSpent / goalAmount) * 100) : 0;

        // 3. 템플릿 치환
        String filledPrompt = prompt.getTemplate()
                .replace("{{goalAmount}}", String.valueOf(goalAmount))
                .replace("{{total}}", String.valueOf(totalSpent))
                .replace("{{rate}}", String.valueOf(achieveRate))
                .replace("{{categorySummary}}", expenseList.toString());

        // 4. 이전에 동일 요청 이력이 있는지 확인 (비용 절약)
        PromptHistory exist = promptHistoryMapper.findRecentSuccess(
                userId, prompt.getPromptId(), filledPrompt);
        if (exist != null) {
            return exist.getGeminiResponse();
        }

        // 5. Gemini 호출
        String aiResponse = callGemini(filledPrompt);

        // 6. 이력 저장
        PromptHistory history = PromptHistory.builder()
                .userId(userId)
                .promptId(prompt.getPromptId())
                .requestInput(filledPrompt)
                .geminiResponse(aiResponse)
                .isSuccess(aiResponse != null && !aiResponse.isBlank())
                .build();
        promptHistoryMapper.insertPromptHistory(history);

        return aiResponse;
    }

    public String callGemini(String promptText) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> part = Map.of("text", promptText);
        Map<String, Object> userContent = Map.of(
                "role", "user",
                "parts", List.of(part)
        );
        Map<String, Object> body = Map.of("contents", List.of(userContent));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    GEMINI_API_URL + apiKey,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null) return "Gemini 응답이 없습니다.";

            List<Map<String, Object>> candidates =
                    (List<Map<String, Object>>) responseBody.get("candidates");
            if (candidates == null || candidates.isEmpty()) return "Gemini 응답 후보가 없습니다.";

            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

            return parts.get(0).get("text").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return 
            		

            		"Gemini 호출 오류: " +"✅ 호출 URL: " + GEMINI_API_URL + apiKey;

        }
    }
}
package com.saveit.service.impl;

import com.saveit.mapper.PromptHistoryMapper;
import com.saveit.mapper.PromptMapper;
import com.saveit.service.GeminiService;
import com.saveit.vo.Expense;
import com.saveit.vo.PromptHistory;
import com.saveit.vo.PromptVO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatClient;
//import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.ai.chat.ChatResponse;


import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {

	private final VertexAiGeminiChatClient geminiClient;
    private final PromptMapper promptMapper;
    private final PromptHistoryMapper promptHistoryMapper;
    


    @Override
    public String analyze(int userId, int goalAmount, List<Expense> expenses) {
        PromptVO prompt = promptMapper.findByType("consumption_analysis");
        if (prompt == null || prompt.getTemplate() == null) {
            return "분석용 프롬프트가 존재하지 않습니다.";
        }

        // 1. 총 지출 계산
        int totalSpent = expenses.stream().mapToInt(Expense::getAmount).sum();

        // 2. 상세 지출 리스트 작성
        StringBuilder detail = new StringBuilder();
        for (Expense e : expenses) {
            detail.append("- ").append(e.getExpenseDate()).append(" | ")
                  .append(e.getCategory()).append(" | ")
                  .append(e.getAmount()).append("원\n");
        }

        // 3. 달성률 계산
        int achieveRate = goalAmount > 0 ? (int) (((double) totalSpent / goalAmount) * 100) : 0;

        // 4. 프롬프트에 값 치환
        String filledPrompt = prompt.getTemplate()
                .replace("{{goalAmount}}", String.valueOf(goalAmount))
                .replace("{{total}}", String.valueOf(totalSpent))
                .replace("{{rate}}", String.valueOf(achieveRate))
                .replace("{{categorySummary}}", detail.toString());

        // 5. Gemini 호출
        ChatResponse response = geminiClient.call(new Prompt(new UserMessage(filledPrompt)));
        String aiResponse = response.getResult().getOutput().getContent();


        // 6. 기록 저장
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

//    @Override
//    public ResponseEntity<String> listModels() {
//        return ResponseEntity.ok("Spring AI에서는 모델 목록 API를 직접 지원하지 않습니다.");
//    }
}

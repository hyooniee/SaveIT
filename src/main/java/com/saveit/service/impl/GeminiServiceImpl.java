package com.saveit.service.impl;

import com.saveit.service.GeminiService;
import com.saveit.vo.Expense;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Override
    public String analyze(int goalAmount, List<Expense> expenses) {
        // 🔽 여기가 실제 LLM(Gemini, GPT 등) 호출을 넣을 자리입니다.

        // [1] 프롬프트 구성
        StringBuilder prompt = new StringBuilder();
        prompt.append("사용자의 목표 금액은 ").append(goalAmount).append("원입니다.\n");
        prompt.append("지출 내역은 다음과 같습니다:\n");
        for (Expense e : expenses) {
            prompt.append("- ").append(e.getExpenseDate()).append(" | ")
                  .append(e.getCategory()).append(" | ")
                  .append(e.getAmount()).append("원\n");
        }

        // [2] Gemini 또는 GPT API 호출 (예시: callGemini(prompt.toString()))
        String aiResponse = callGemini(prompt.toString());

        // [3] 분석 결과 반환
        return aiResponse;
    }

    // 🧪 예시: 실제 Gemini 호출 대신 임시로 구현
    private String callGemini(String prompt) {
        // 실제 구현 시: Google Vertex AI, OpenAI, HTTP API 요청 등
        return "AI 분석 결과 예시: 현재 지출이 목표보다 15% 초과되었습니다.";
    }
}

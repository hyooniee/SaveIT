package com.saveit.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saveit.mapper.ExpenseMapper;
import com.saveit.mapper.GoalMapper;
import com.saveit.service.GeminiService;
import com.saveit.vo.Expense;
import com.saveit.vo.Goal;
import com.saveit.vo.Login;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnalyzeController {

    private final ExpenseMapper expenseMapper;
    private final GeminiService geminiService;
    private final GoalMapper goalMapper;

    @PostMapping("/analyze")
    public ResponseEntity<?> analyze(
        @AuthenticationPrincipal Login loginUser
    ) {
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        int userId = loginUser.getUserId(); // 로그인된 유저 ID

        Goal goal = goalMapper.findGoalInfoByUserId(userId);
        
        if (goal == null) {
            return ResponseEntity.ok(Map.of("summary", "목표가 설정되지 않았습니다."));
        }
        
        int goalAmount = goal.getGoalAmount();
        LocalDate startDate = goal.getGoalDate();
        LocalDate endDate = startDate.plusMonths(1);

        List<Expense> expenses = expenseMapper.getExpenseMonth(userId, startDate, endDate);

        if (expenses.isEmpty()) {
        	return ResponseEntity.ok(Map.of("summary", "지출 내역이 없습니다."));
        }
        String result = geminiService.analyze(goalAmount, expenses);

        Map<String, Object> response = new HashMap<>();
        response.put("summary", result);

        
        return ResponseEntity.ok(response);
    }

}



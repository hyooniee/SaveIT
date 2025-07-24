package com.saveit.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saveit.mapper.ExpenseMapper;
import com.saveit.mapper.GoalMapper;
import com.saveit.vo.Expense;
import com.saveit.vo.Goal;
import com.saveit.vo.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChartController {
	
	private final ExpenseMapper expenseMapper;
	private final GoalMapper goalMapper;
	
	@PostMapping("/chart")
	public ResponseEntity<?> chart(@AuthenticationPrincipal Login loginUser) {
	    if (loginUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 해주세요");
	    }

	    int userId = loginUser.getUserId();
	    Goal goal = goalMapper.findGoalInfoByUserId(userId);

	    LocalDate startDate = goal.getGoalDate();
	    LocalDate endDate = startDate.plusMonths(1);

	    List<Map<String, Object>> data = expenseMapper.getTop5CategoryExpense(userId, startDate, endDate);

	    List<String> labels = new ArrayList<>();
	    List<Integer> series = new ArrayList<>();

	    for (Map<String, Object> item : data) {
	        labels.add((String) item.get("category"));
	        series.add(((Number) item.get("total")).intValue()); 
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("labels", labels);
	    response.put("series", series);

	    return ResponseEntity.ok(response);
	}
}

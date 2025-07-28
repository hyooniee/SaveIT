package com.saveit.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saveit.service.ExpenseService;
import com.saveit.util.JwtUtil;
import com.saveit.vo.Expense;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
	private final ExpenseService expenseService;
	private final JwtUtil jwtUtil;
	
	public ExpenseController(ExpenseService expenseService, JwtUtil jwtUtil) {
		this.expenseService = expenseService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping
	public ResponseEntity<List<Expense>> getExpense(@RequestParam(defaultValue = "0") int offset, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
	    Integer userId = jwtUtil.getUserId(token);
		
		 if (userId == null) {
		        return ResponseEntity.status(401).build(); // 유효하지 않은 토큰
	    }
		 
		 List<Expense> expense = expenseService.getExpense(userId, offset);
		
		return ResponseEntity.ok(expense);
	}
	
	@PostMapping
	public ResponseEntity<Expense> postExpense(@RequestBody Expense expense, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
	    Integer userId = jwtUtil.getUserId(token);
	    
	    if (userId == null) {
	        return ResponseEntity.status(401).build();
	    }
	    
	    expense.setUserId(userId);
	    
	    Expense input = expenseService.inputExpense(expense);
	    
//	    System.out.println(input);
	    
	    return ResponseEntity.ok(input);
	}
}
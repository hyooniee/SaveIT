package com.saveit.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saveit.mapper.GoalMapper;
import com.saveit.service.GoalService;
import com.saveit.util.JwtUtil;
import com.saveit.vo.Goal;
import com.saveit.vo.Login;

import jakarta.servlet.http.HttpServletRequest;

@RestController
	@RequestMapping("/api/goal")
	public class GoalController {
		
		private final GoalService goalService;
		private final JwtUtil jwtUtil;
		
		public GoalController(GoalService goalService, JwtUtil jwtUtil) {
			this.goalService = goalService;
			this.jwtUtil = jwtUtil;
	}
		
	@GetMapping("/expense")
	public ResponseEntity<?> getUserExpense(@AuthenticationPrincipal Login loginUser) {
		
		if (loginUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 해주세요");
	    }
	
	    int userId = loginUser.getUserId();
	    Goal goal = goalService.getTotalExpense(userId);
	    
	    return ResponseEntity.ok(goal);
	}
	
	@GetMapping("/save")
	public ResponseEntity<?> getUserSave(@AuthenticationPrincipal Login loginUser) {
		
		if (loginUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 해주세요");
	    }
	
	    int userId = loginUser.getUserId();
	    Goal goal = goalService.getTotalSave(userId);
	    
	    return ResponseEntity.ok(goal);
	}
	
	@GetMapping
	public ResponseEntity<Goal> getUserGoal(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
	    Integer userId = jwtUtil.getUserId(token);
	    
	    if (userId == null) {
	        return ResponseEntity.status(401).build(); // 유효하지 않은 토큰
	    }
		
		Goal goal = goalService.getGoal(userId);
		
		return ResponseEntity.ok(goal);
	}
	
	@PostMapping
	public ResponseEntity<Goal> postUserGoal(@RequestBody Goal goal, HttpServletRequest request ) {
		String token = request.getHeader("Authorization").substring(7);
	    Integer userId = jwtUtil.getUserId(token);
	    
		if (userId == null) {
	        return ResponseEntity.status(401).build();
	    }
		
		goal.setUserId(userId);
		
		Goal save = goalService.saveGoal(goal);
		
		return ResponseEntity.ok(save);
	}
}

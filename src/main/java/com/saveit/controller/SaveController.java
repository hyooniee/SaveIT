package com.saveit.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saveit.service.SaveService;
import com.saveit.util.JwtUtil;
import com.saveit.vo.Save;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/save")
public class SaveController {
	private final SaveService saveService;
	private final JwtUtil jwtUtil;
	
	public SaveController(SaveService saveService, JwtUtil jwtUtil) {
		this.saveService = saveService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping
	public ResponseEntity<List<Save>> getSave(@RequestParam(defaultValue = "0") int offset, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
	    Integer userId = jwtUtil.getUserId(token);
	    
	    if (userId == null) {
	        return ResponseEntity.status(401).build(); // 유효하지 않은 토큰
	    }
	    
	    List<Save> save = saveService.getSave(userId, offset); 
	 
	    return ResponseEntity.ok(save);
	}
	
	@PostMapping
	public ResponseEntity<Save> postSave(@RequestBody Save save, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
	    Integer userId = jwtUtil.getUserId(token);
	    
	    if (userId == null) {
	        return ResponseEntity.status(401).build();
	    }
	    
	    save.setUserId(userId);
	    
	    Save input = saveService.inputSave(save);
	    
//	    System.out.println(input);
	    
	    return ResponseEntity.ok(input);
	}
}
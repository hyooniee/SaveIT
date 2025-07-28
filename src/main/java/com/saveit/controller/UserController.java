package com.saveit.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.saveit.vo.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	 

	   @GetMapping("/info")
	   public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Login loginUser) {
	       if (loginUser == null) {
	           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
	       }

	       System.out.println("로그인 유저 객체: " + loginUser);

	       return ResponseEntity.ok(loginUser); // DB 재조회 안 해도 됨
	   }

//	   @GetMapping("/id")
//	   public ResponseEntity<?> getUserId(@AuthenticationPrincipal Login loginUser){
//		   if(loginUser == null) {
//			   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
//		   }
//		   return ResponseEntity.ok(Map.of("userId",loginUser.getUserId()));
//	   }

}




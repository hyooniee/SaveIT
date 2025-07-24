package com.saveit.service;

import java.util.List;

import com.saveit.vo.Expense;

public interface GeminiService {

	String analyze(int userId ,int goalAmount, List<Expense> expenses);
	
	
	
}

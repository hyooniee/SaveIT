package com.saveit.service;

import com.saveit.vo.Expense;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GeminiService {
    String analyze(int userId, int goalAmount, List<Expense> expenses);
    //ResponseEntity<String> listModels();
}

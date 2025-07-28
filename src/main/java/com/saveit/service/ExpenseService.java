package com.saveit.service;

import java.util.List;

import com.saveit.vo.Expense;


public interface ExpenseService {
	List<Expense> getExpense(int userId, int offset);
	Expense inputExpense(Expense expense);
}

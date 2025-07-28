package com.saveit.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.saveit.mapper.ExpenseMapper;
import com.saveit.service.ExpenseService;
import com.saveit.vo.Expense;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{

	private final ExpenseMapper expenseMapper;
	
	@Override
	public List<Expense> getExpense(int userId, int offset) {
		LocalDate baseDate = LocalDate.now().plusDays(offset);
	    LocalDate startDate = baseDate.minusDays(2);
	    LocalDate endDate = baseDate.plusDays(2);
        
		return expenseMapper.getExpenseWeek(userId, startDate, endDate);
	}
	
	@Override
	public Expense inputExpense(Expense	 expense) {
		
		int categoryId = expenseMapper.findCategoryIdByName(expense.getCategory());
		
		expenseMapper.inputExpense(
		        expense.getUserId(),
		        categoryId,
		        expense.getAmount(),
		        expense.getExpenseDate()
		    );
		
		return expense;
	}

	
}

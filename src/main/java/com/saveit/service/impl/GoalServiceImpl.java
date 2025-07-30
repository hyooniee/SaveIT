package com.saveit.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.saveit.mapper.ExpenseMapper;
import com.saveit.mapper.GoalMapper;
import com.saveit.mapper.SaveMapper;
import com.saveit.service.GoalService;
import com.saveit.vo.Expense;
import com.saveit.vo.Goal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService{
	
	private final GoalMapper goalMapper;
	private final ExpenseMapper expenseMapper;
	private final SaveMapper saveMapper;
	
	@Override
	public Goal getGoal(int userId) {
		
		return goalMapper.getGoalByUserId(userId);
	}

	@Override
	public Goal saveGoal(Goal goal) {
		goal.setGoalDate(LocalDate.now());
		 
		goalMapper.deleteGoalByUserId(goal.getUserId());
		
		goalMapper.saveGoalByUserId(
		        goal.getUserId(),
		        goal.getGoalAmount(),
		        goal.getGoalDate()
		    );
		
		return goal;		
	}

	@Override
	public Goal getTotalExpense(int userId) {
		
		Goal goal = goalMapper.getGoalByUserId(userId);
				
		LocalDate startDate = goal.getGoalDate();
		LocalDate endDate = startDate.plusMonths(1);
		
		int totalExpense = expenseMapper.totalExpenseMonth(userId, startDate, endDate);
		goal.setTotalExpense(totalExpense);
				
		return goal;
	}

	@Override
	public Goal getTotalSave(int userId) {
		
		Goal goal = goalMapper.getGoalByUserId(userId);
		
		LocalDate startDate = goal.getGoalDate();
		LocalDate endDate = startDate.plusMonths(1);
		
		int totalSave = saveMapper.totalSaveMonth(userId, startDate, endDate);
		goal.setTotalSave(totalSave);
		
		return goal;
	}


	
}

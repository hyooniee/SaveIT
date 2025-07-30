package com.saveit.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.saveit.mapper.GoalMapper;
import com.saveit.service.GoalService;
import com.saveit.vo.Goal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService{
	
	private final GoalMapper goalMapper;

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
	
}

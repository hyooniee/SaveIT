package com.saveit.service;


import com.saveit.vo.Goal;

public interface GoalService {
	Goal getGoal(int userId);
	Goal saveGoal(Goal goal);
}
